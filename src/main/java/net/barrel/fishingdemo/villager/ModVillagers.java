package net.barrel.fishingdemo.villager;

import com.google.common.collect.ImmutableSet;
import net.barrel.fishingdemo.FishingDemo;
import net.barrel.fishingdemo.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, FishingDemo.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, FishingDemo.MOD_ID);

    public static final Holder<PoiType> FISHING_POI = POI_TYPES.register("fishing_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.FISHING_STATION.get().getStateDefinition().getPossibleStates()),
                    1, 1));


    public static final Holder<VillagerProfession> MASTER_FISHERMAN = VILLAGER_PROFESSIONS.register("master_fisherman",
            () -> new VillagerProfession("master_fisherman", holder -> holder.value() == FISHING_POI.value(),
                    holder -> holder.value() == FISHING_POI.value(), ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_FISHERMAN));

    public static void register(IEventBus bus) {
        POI_TYPES.register(bus);
        VILLAGER_PROFESSIONS.register(bus);
    }
}
