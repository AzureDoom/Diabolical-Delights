package mod.azure.diabolicaldelights.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import mod.azure.diabolicaldelights.entities.JackOBombEntity;

public class JackOBombItem extends Item {

    public JackOBombItem() {
        super(new Properties().rarity(Rarity.RARE).fireResistant().stacksTo(64));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(
        @NotNull Level level,
        Player user,
        @NotNull InteractionHand hand
    ) {
        var itemStack = user.getItemInHand(hand);
        if (!user.getCooldowns().isOnCooldown(this)) {
            user.getCooldowns().addCooldown(this, 25);
            level.playSound(
                null,
                user.getX(),
                user.getY(),
                user.getZ(),
                SoundEvents.WITCH_THROW,
                SoundSource.NEUTRAL,
                0.5F,
                0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
            );
            if (!level.isClientSide) {
                var bomb = new JackOBombEntity(
                    level,
                    user,
                    user.getX(),
                    user.getEyeY(),
                    user.getZ()
                );
                bomb.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.5F, 0.0F);
                bomb.setBaseDamage(0);
                level.addFreshEntity(bomb);
            }
            if (!user.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
            return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
        } else
            return InteractionResultHolder.fail(itemStack);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        return 72000;
    }
}
