package mod.azure.diabolicaldelights;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;

import mod.azure.diabolicaldelights.registry.ItemRegistry;

public final class FabricMod implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonMod.initRegistries();
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT)
            .register(entries -> entries.addAfter(Items.WIND_CHARGE, ItemRegistry.JACK_0_BOMB.get()));
    }
}
