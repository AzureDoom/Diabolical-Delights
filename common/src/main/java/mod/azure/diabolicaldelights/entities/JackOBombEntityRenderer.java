package mod.azure.diabolicaldelights.entities;

import mod.azure.azurelib.rewrite.render.entity.AzEntityRenderer;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.rewrite.render.layer.AzAutoGlowingLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import mod.azure.diabolicaldelights.CommonMod;

public class JackOBombEntityRenderer extends AzEntityRenderer<JackOBombEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/jack_o_bomb.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/bomb_lit.png");

    public JackOBombEntityRenderer(EntityRendererProvider.Context context) {
        super(
            AzEntityRendererConfig.<JackOBombEntity>builder(MODEL, TEXTURE)
                .setAnimatorProvider(JackOBombAnimator::new)
                .addRenderLayer(new AzAutoGlowingLayer<>())
                .build(),
            context
        );
    }
}
