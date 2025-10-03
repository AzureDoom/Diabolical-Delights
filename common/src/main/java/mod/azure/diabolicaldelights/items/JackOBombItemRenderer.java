package mod.azure.diabolicaldelights.items;

import mod.azure.azurelib.rewrite.render.item.AzItemRenderer;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererConfig;
import net.minecraft.resources.ResourceLocation;

import mod.azure.diabolicaldelights.CommonMod;

public class JackOBombItemRenderer extends AzItemRenderer {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/item/jack_o_bomb.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/bomb_unlit.png");

    public JackOBombItemRenderer() {
        super(AzItemRendererConfig.builder(MODEL, TEXTURE).build());
    }
}
