package net.barrel.fishingdemo.block;

import net.barrel.fishingdemo.FishingDemo;
import net.barrel.fishingdemo.block.custom.FishingTrapBlock;
import net.barrel.fishingdemo.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(FishingDemo.MOD_ID);

    public static final DeferredBlock<Block> FISHING_STATION = registerBlock("fishing_station",
            () -> new Block(BlockBehaviour.Properties.of().strength(4f)));
    public static final DeferredBlock<Block> FISHING_TRAP_BLOCK = registerBlock("fishing_trap_block",
            () -> new FishingTrapBlock(BlockBehaviour.Properties.of().strength(1f).noOcclusion()));

    public static final DeferredBlock<Block> BARRELITE_ORE = registerBlock("barrelite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 6),
                    BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));



    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
