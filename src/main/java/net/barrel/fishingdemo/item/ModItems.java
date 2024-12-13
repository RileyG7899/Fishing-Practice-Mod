package net.barrel.fishingdemo.item;

import net.barrel.fishingdemo.FishingDemo;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FishingDemo.MOD_ID);

    public static final DeferredItem<Item> MASTER_FISHING_ROD_BODY = ITEMS.registerSimpleItem("master_fishing_rod_body");
    public static final DeferredItem<Item> MASTER_FISHING_ROD_REEL = ITEMS.registerSimpleItem("master_fishing_rod_reel");
    public static final DeferredItem<Item> MASTER_FISHING_ROD_HOOK = ITEMS.registerSimpleItem("master_fishing_rod_hook");

    public static final DeferredItem<Item> WORM = ITEMS.registerSimpleItem("worm");



    public static final DeferredItem<Item> BARRELITE_SHARD = ITEMS.registerSimpleItem("barrelite_shard");

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
