package mod.azure.diabolicaldelights.client;

import mod.azure.diabolicaldelights.common.DiabolicalDelights.ModProjectiles;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "diabolicaldelights", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DiabolicalDelightsClient {

	@SubscribeEvent
	public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModProjectiles.JACKOBOMB_ENTITY.get(), JackOBombEntityRender::new);
	}

}
