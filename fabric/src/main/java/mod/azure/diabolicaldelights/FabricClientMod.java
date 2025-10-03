package mod.azure.diabolicaldelights;

import mod.azure.azurelib.rewrite.render.item.AzItemRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

import mod.azure.diabolicaldelights.entities.JackOBombEntityRenderer;
import mod.azure.diabolicaldelights.items.JackOBombItemRenderer;
import mod.azure.diabolicaldelights.registry.EntityRegistry;
import mod.azure.diabolicaldelights.registry.ItemRegistry;

public class FabricClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AzItemRendererRegistry.register(ItemRegistry.JACK_0_BOMB.get(), JackOBombItemRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.JACK_0_BOMB.get(), JackOBombEntityRenderer::new);
    }
}
