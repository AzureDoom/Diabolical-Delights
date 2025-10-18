package mod.azure.diabolicaldelights.entities;

import mod.azure.azurelib.common.animation.AzAnimatorConfig;
import mod.azure.azurelib.common.animation.cache.AzBoneCache;
import mod.azure.azurelib.common.animation.controller.AzAnimationController;
import mod.azure.azurelib.common.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.common.animation.impl.AzEntityAnimator;
import mod.azure.azurelib.common.model.AzBone;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import mod.azure.diabolicaldelights.CommonMod;

public class JackOBombAnimator extends AzEntityAnimator<JackOBombEntity> {

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
        "animations/entity/jack_o_bomb.animation.json"
    );

    public JackOBombAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<JackOBombEntity> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, "base").build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(JackOBombEntity animatable) {
        return ANIMATIONS;
    }

    @Override
    public void setCustomAnimations(JackOBombEntity animatable, float partialTicks) {
        super.setCustomAnimations(animatable, partialTicks);
        AzBoneCache boneCache = this.context().boneCache();
        Optional<AzBone> block = boneCache.getBakedModel().getBone("block");

        if (block.isPresent()) {
            var baseSpeed = 5.0F;
            var rotationX = (float) Math.sin(partialTicks * 0.1F) * baseSpeed * partialTicks;
            var rotationY = partialTicks * baseSpeed * 2F;

            block.get().setRotX(rotationX);
            block.get().setRotY(rotationY);
            block.get().setRotZ(partialTicks * 0.5F);
        }
    }
}
