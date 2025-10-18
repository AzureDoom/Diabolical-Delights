package mod.azure.diabolicaldelights;

import mod.azure.azurelib.common.render.item.AzItemRendererRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import mod.azure.diabolicaldelights.entities.JackOBombEntityRenderer;
import mod.azure.diabolicaldelights.items.JackOBombItemRenderer;
import mod.azure.diabolicaldelights.registry.EntityRegistry;
import mod.azure.diabolicaldelights.registry.ItemRegistry;

@EventBusSubscriber(modid = CommonMod.MOD_ID, value = Dist.CLIENT)
public class NeoForgeClientMod {

    private NeoForgeClientMod() {}

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        AzItemRendererRegistry.register(ItemRegistry.JACK_0_BOMB.get(), JackOBombItemRenderer::new);
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.JACK_0_BOMB.get(), JackOBombEntityRenderer::new);
    }
}
