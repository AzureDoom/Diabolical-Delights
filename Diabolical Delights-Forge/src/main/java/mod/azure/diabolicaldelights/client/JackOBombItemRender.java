package mod.azure.diabolicaldelights.client;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.diabolicaldelights.common.JackOBombItem;

public class JackOBombItemRender extends GeoItemRenderer<JackOBombItem> {
	public JackOBombItemRender() {
		super(new JackOBombItemModel());
	}
}