package mod.azure.diabolicaldelights.client;

import mod.azure.azurelib.model.DefaultedEntityGeoModel;
import mod.azure.diabolicaldelights.common.DiabolicalDelights;
import mod.azure.diabolicaldelights.common.JackOBombItem;
import net.minecraft.resources.ResourceLocation;

public class JackOBombItemModel extends DefaultedEntityGeoModel<JackOBombItem>{

	public JackOBombItemModel() {
		super(DiabolicalDelights.modResource("jack_o_bomb/jack_o_bomb"));
	}

	@Override
	public ResourceLocation getTextureResource(JackOBombItem animatable) {
		return DiabolicalDelights.modResource("textures/entity/jack_o_bomb/bomb_unlit.png");
	}
}