package net.barrel.fishingdemo.datagen;

import net.barrel.fishingdemo.FishingDemo;
import net.barrel.fishingdemo.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FishingDemo.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.MASTER_FISHING_ROD_BODY.get());
        basicItem(ModItems.MASTER_FISHING_ROD_REEL.get());
        basicItem(ModItems.MASTER_FISHING_ROD_HOOK.get());
        basicItem(ModItems.BARRELITE_SHARD.get());
    }
}
