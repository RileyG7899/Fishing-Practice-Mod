package net.barrel.fishingdemo.item;

import net.barrel.fishingdemo.FishingDemo;
import net.barrel.fishingdemo.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
            FishingDemo.MOD_ID);

    public static final Supplier<CreativeModeTab> FISHING_ITEMS_TAB =
            CREATIVE_MODE_TABS.register("fishing_items_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.fishingdemo.fishing_items_tab"))
                    .icon(() -> new ItemStack(Items.COD))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(Items.COD);
                        pOutput.accept(Items.TROPICAL_FISH);
                        pOutput.accept(ModBlocks.FISHING_STATION);
                        pOutput.accept(ModItems.MASTER_FISHING_ROD_BODY);
                        pOutput.accept(ModItems.MASTER_FISHING_ROD_REEL);
                        pOutput.accept(ModItems.MASTER_FISHING_ROD_HOOK);
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
