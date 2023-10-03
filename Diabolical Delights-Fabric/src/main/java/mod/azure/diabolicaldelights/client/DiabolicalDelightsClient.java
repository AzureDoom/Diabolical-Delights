package mod.azure.diabolicaldelights.client;

import mod.azure.diabolicaldelights.common.DiabolicalDelights.ModProjectiles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class DiabolicalDelightsClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModProjectiles.JACKOBOMB_ENTITY, JackOBombEntityRender::new);
	}

}
