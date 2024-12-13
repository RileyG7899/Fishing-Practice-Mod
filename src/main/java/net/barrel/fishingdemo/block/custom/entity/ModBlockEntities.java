package net.barrel.fishingdemo.block.custom.entity;

import net.barrel.fishingdemo.FishingDemo;
import net.barrel.fishingdemo.block.ModBlocks;
import net.barrel.fishingdemo.block.custom.entity.custom.FishingTrapBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, FishingDemo.MOD_ID);

    public static final Supplier<BlockEntityType<FishingTrapBlockEntity>> FISHING_TRAP_BE =
            BLOCK_ENTITIES.register("fishing_trap_be", () -> BlockEntityType.Builder.of(
                    FishingTrapBlockEntity::new, ModBlocks.FISHING_TRAP_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
