package net.barrel.fishingdemo.block.custom.entity.custom;

import net.barrel.fishingdemo.block.custom.entity.ModBlockEntities;
import net.barrel.fishingdemo.screen.custom.FishingTrapMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class FishingTrapBlockEntity extends BlockEntity implements MenuProvider {
    private static final int TICK_INTERNAL = 100;
    private int tickCount = 0;
    private final Random random = new Random();

    private static final ResourceKey<LootTable> FISHING_LOOT_TABLE = BuiltInLootTables.FISHING;


    private void generateFishingLoot(ServerLevel level) {
        if (level.isClientSide()) return;

        // Retrieve the fishing loot table
        LootTable fishingLootTable = level.getServer().reloadableRegistries().getLootTable(BuiltInLootTables.FISHING);

        // Build our LootParams
        LootParams.Builder builder = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.worldPosition))
                // The FISHING param set requires TOOL to be present.
                // Since we don't have an actual tool, let's just provide an empty stack.
                .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                .withLuck(0); // optional if you want luck

        LootParams params = builder.create(LootContextParamSets.FISHING);

        // Roll the loot table
        List<ItemStack> loot = fishingLootTable.getRandomItems(params);

        // Insert items into inventory
        for (ItemStack item : loot) {
            for (int i = 0; i < inventory.getSlots(); i++) {
                if (inventory.getStackInSlot(i).isEmpty()) {
                    inventory.setStackInSlot(i, item);
                    break;
                }
                else if (inventory.getStackInSlot(i).getItem() == item.getItem()) {
                    inventory.insertItem(i, item, false);
                    break;
                }
            }
        }
    }

    public final ItemStackHandler inventory = new ItemStackHandler(3) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return stack.getMaxStackSize();
        }


        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public static void serverTick(Level level, BlockPos pos, BlockState state, FishingTrapBlockEntity entity) {
        if (level.isClientSide()) return; // Server-side only

        entity.tickCount++;

        if (entity.tickCount >= TICK_INTERNAL) {
            entity.tickCount = 0;

            if (entity.isInWaterSource(level, pos)) {
                entity.generateFishingLoot((ServerLevel) level);
            }
        }
    }

    public void clearContents() {
        inventory.setStackInSlot(0, ItemStack.EMPTY);
        inventory.setStackInSlot(1, ItemStack.EMPTY);
        inventory.setStackInSlot(2, ItemStack.EMPTY);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for(int i = 0; i < inventory.getSlots(); i++) {
            inv.setItem(i, inventory.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    public FishingTrapBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.FISHING_TRAP_BE.get(), pos, blockState);
    }



    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("blockentity.fishingdemo.fishing_trap");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new FishingTrapMenu(containerId, inventory, this);
    }

    private boolean isInWaterSource(Level level, BlockPos pos) {
        return level.getBlockState(pos.north()).getBlock() == Blocks.WATER
                && level.getBlockState(pos.south()).getBlock() == Blocks.WATER
                && level.getBlockState(pos.east()).getBlock() == Blocks.WATER
                && level.getBlockState(pos.west()).getBlock() == Blocks.WATER;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }


}
