package mod.azure.diabolicaldelights;

import net.minecraft.resources.ResourceLocation;

import mod.azure.diabolicaldelights.registry.EntityRegistry;
import mod.azure.diabolicaldelights.registry.ItemRegistry;
import mod.azure.diabolicaldelights.registry.SoundRegistry;

public class CommonMod {

    public static final String MOD_ID = "diabolicaldelights";

    private CommonMod() {}

    public static ResourceLocation modResource(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    public static void initRegistries() {
        EntityRegistry.initialize();
        ItemRegistry.initialize();
        SoundRegistry.initialize();
    }
}
