package net.barrel.fishingdemo.item;

import net.barrel.fishingdemo.FishingDemo;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FishingDemo.MOD_ID);

    /*
    public static final DeferredItem<Item> BLACK_OPAL = ITEMS.registerSimpleItem("black_opal");
    public static final DeferredItem<Item> RAW_BLACK_OPAL = ITEMS.registerItem("raw_black_opal",
            Item::new, new Item.Properties());
    */
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
