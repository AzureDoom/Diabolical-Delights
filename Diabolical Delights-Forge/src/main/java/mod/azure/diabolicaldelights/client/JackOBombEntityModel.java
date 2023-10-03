package mod.azure.diabolicaldelights.client;

import mod.azure.azurelib.model.DefaultedEntityGeoModel;
import mod.azure.diabolicaldelights.common.DiabolicalDelights;
import mod.azure.diabolicaldelights.common.JackOBombEntity;
import net.minecraft.resources.ResourceLocation;

public class JackOBombEntityModel extends DefaultedEntityGeoModel<JackOBombEntity>{

	public JackOBombEntityModel() {
		super(DiabolicalDelights.modResource("jack_o_bomb/jack_o_bomb"));
	}

	@Override
	public ResourceLocation getTextureResource(JackOBombEntity animatable) {
		return DiabolicalDelights.modResource("textures/entity/jack_o_bomb/bomb_lit.png");
	}
}