package mod.azure.diabolicaldelights.entities;

import mod.azure.azurelib.common.internal.common.blocks.TickingLightBlock;
import mod.azure.azurelib.common.internal.common.registry.AzureBlocksRegistry;
import mod.azure.azurelib.common.internal.common.util.AzureLibUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import mod.azure.diabolicaldelights.registry.EntityRegistry;
import mod.azure.diabolicaldelights.registry.SoundRegistry;

public class JackOBombEntity extends AbstractArrow {

    private int ticksInAir;

    public static final EntityDataAccessor<Float> FORCED_YAW = SynchedEntityData.defineId(
        JackOBombEntity.class,
        EntityDataSerializers.FLOAT
    );

    protected static final List<Holder<MobEffect>> effects = Arrays.asList(
        MobEffects.MOVEMENT_SPEED,
        MobEffects.MOVEMENT_SLOWDOWN,
        MobEffects.DIG_SPEED,
        MobEffects.DIG_SLOWDOWN,
        MobEffects.DAMAGE_BOOST,
        MobEffects.HEAL,
        MobEffects.HARM,
        MobEffects.JUMP,
        MobEffects.CONFUSION,
        MobEffects.REGENERATION,
        MobEffects.DAMAGE_RESISTANCE,
        MobEffects.FIRE_RESISTANCE,
        MobEffects.WATER_BREATHING,
        MobEffects.INVISIBILITY,
        MobEffects.BLINDNESS,
        MobEffects.NIGHT_VISION,
        MobEffects.HUNGER,
        MobEffects.WEAKNESS,
        MobEffects.POISON,
        MobEffects.WITHER,
        MobEffects.HEALTH_BOOST,
        MobEffects.ABSORPTION,
        MobEffects.SATURATION,
        MobEffects.GLOWING,
        MobEffects.LEVITATION,
        MobEffects.LUCK,
        MobEffects.UNLUCK,
        MobEffects.SLOW_FALLING,
        MobEffects.CONDUIT_POWER,
        MobEffects.DOLPHINS_GRACE,
        MobEffects.BAD_OMEN,
        MobEffects.HERO_OF_THE_VILLAGE,
        MobEffects.DARKNESS
    );

    public JackOBombEntity(EntityType<? extends JackOBombEntity> entityType, Level world) {
        super(entityType, world);
        this.pickup = Pickup.DISALLOWED;
    }

    public JackOBombEntity(
        Level world,
        Entity owner,
        double x,
        double y,
        double z
    ) {
        super(EntityRegistry.JACK_0_BOMB.get(), world);
        this.setOwner(owner);
        this.absMoveTo(x, y, z);
        this.pickup = Pickup.DISALLOWED;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FORCED_YAW, 0f);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putShort("life", (short) this.ticksInAir);
        tag.putFloat("ForcedYaw", entityData.get(FORCED_YAW));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.ticksInAir = tag.getShort("life");
        entityData.set(FORCED_YAW, tag.getFloat("ForcedYaw"));
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (getOwner() instanceof Player) {
            setYRot(entityData.get(FORCED_YAW));
        }
        if (!this.level().isClientSide) {
            var lightBlockPos = AzureLibUtil.findFreeSpace(this.level(), this.blockPosition(), 1);

            if (lightBlockPos == null) {
                return;
            }

            if (this.level().isNight() || !this.level().canSeeSky(lightBlockPos)) {
                this.level()
                    .setBlockAndUpdate(
                        lightBlockPos,
                        AzureBlocksRegistry.TICKING_LIGHT_BLOCK.get()
                            .defaultBlockState()
                            .setValue(
                                TickingLightBlock.LIGHT_LEVEL,
                                7
                            )
                    );
            }
        }
        if (this.tickCount >= 190) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        this.summonAoE(this, ParticleTypes.SCULK_SOUL, 0, 50, 3, 100);
        super.remove(reason);
    }

    @Override
    public void tickDespawn() {
        ++this.ticksInAir;
        if (this.ticksInAir >= 80) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public void shoot(double x, double y, double z, float speed, float divergence) {
        super.shoot(x, y, z, speed, divergence);
        this.ticksInAir = 0;
    }

    @Override
    public void setSoundEvent(@NotNull SoundEvent soundIn) {
        this.getDefaultHitGroundSoundEvent();
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.EMPTY;
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() instanceof LivingEntity) {
            this.remove(RemovalReason.DISCARDED);
        }
        if (!this.level().isClientSide) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public @NotNull ItemStack getPickupItem() {
        return new ItemStack(Items.AIR);
    }

    @Override
    protected @NotNull ItemStack getDefaultPickupItem() {
        return Items.AIR.getDefaultInstance();
    }

    public void summonAoE(
        Entity entity,
        ParticleOptions particle,
        int yOffset,
        int duration,
        float radius,
        int effectTime
    ) {
        var areaEffectCloudEntity = new AreaEffectCloud(
            entity.level(),
            entity.getX(),
            entity.getY() + yOffset,
            entity.getZ()
        );
        areaEffectCloudEntity.setRadius(radius);
        areaEffectCloudEntity.setDuration(duration);
        areaEffectCloudEntity.setParticle(particle);
        areaEffectCloudEntity.setRadiusPerTick(-16);
        areaEffectCloudEntity.addEffect(
            new MobEffectInstance(effects.get(this.random.nextInt(effects.size())), effectTime, 0)
        );
        level().playSound(
            null,
            this.getX(),
            this.getY(),
            this.getZ(),
            SoundRegistry.JACKOBOMB_SOUND.get(),
            SoundSource.NEUTRAL,
            0.5F,
            0.4F / (level().getRandom().nextFloat() * 0.4F + 0.8F)
        );
        entity.level().addFreshEntity(areaEffectCloudEntity);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }
}
