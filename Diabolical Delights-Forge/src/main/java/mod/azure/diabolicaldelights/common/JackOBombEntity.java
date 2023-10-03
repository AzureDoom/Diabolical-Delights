package mod.azure.diabolicaldelights.common;

import java.util.Arrays;

import org.jetbrains.annotations.Nullable;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.diabolicaldelights.common.DiabolicalDelights.ModProjectiles;
import mod.azure.diabolicaldelights.common.DiabolicalDelights.ModSounds;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
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
import net.minecraftforge.network.NetworkHooks;

public class JackOBombEntity extends AbstractArrow implements GeoEntity {

	protected int timeInAir;
	protected boolean inAir;
	protected String type;
	private int ticksInAir;
	private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(JackOBombEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public static final EntityDataAccessor<Float> FORCED_YAW = SynchedEntityData.defineId(JackOBombEntity.class, EntityDataSerializers.FLOAT);

	public JackOBombEntity(EntityType<? extends JackOBombEntity> entityType, Level world) {
		super(entityType, world);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public JackOBombEntity(Level world, LivingEntity owner) {
		super(ModProjectiles.JACKOBOMB_ENTITY.get(), owner, world);
	}

	protected JackOBombEntity(EntityType<? extends JackOBombEntity> type, double x, double y, double z, Level world) {
		this(type, world);
	}

	protected JackOBombEntity(EntityType<? extends JackOBombEntity> type, LivingEntity owner, Level world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public JackOBombEntity(Level world, ItemStack stack, Entity entity, double x, double y, double z, boolean shotAtAngle) {
		this(world, stack, x, y, z, shotAtAngle);
		this.setOwner(entity);
	}

	public JackOBombEntity(Level world, ItemStack stack, double x, double y, double z, boolean shotAtAngle) {
		this(world, x, y, z, stack);
	}

	public JackOBombEntity(Level world, double x, double y, double z, ItemStack stack) {
		super(ModProjectiles.JACKOBOMB_ENTITY.get(), world);
		this.absMoveTo(x, y, z);
	}

	public JackOBombEntity(Level world, @Nullable Entity entity, double x, double y, double z, ItemStack stack) {
		this(world, x, y, z, stack);
		this.setOwner(entity);
	}

	public JackOBombEntity(Level world, double x, double y, double z) {
		super(ModProjectiles.JACKOBOMB_ENTITY.get(), x, y, z, world);
		this.setBaseDamage(0);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(FORCED_YAW, 0f);
		this.entityData.define(STATE, 0);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("State", this.getState());
		tag.putShort("life", (short) this.ticksInAir);
		tag.putFloat("ForcedYaw", entityData.get(FORCED_YAW));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setState(tag.getInt("State"));
		this.ticksInAir = tag.getShort("life");
		entityData.set(FORCED_YAW, tag.getFloat("ForcedYaw"));
	}

	@Override
	public void tick() {
		super.tick();
		if (getOwner() instanceof Player owner)
			setYRot(entityData.get(FORCED_YAW));
	}

	public int getState() {
		return this.entityData.get(STATE);
	}

	public void setState(int color) {
		this.entityData.set(STATE, color);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			return event.setAndContinue(RawAnimation.begin().thenLoop("spin"));
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void remove(RemovalReason reason) {
		this.summonAoE(this, ParticleTypes.SCULK_SOUL, 0, 50, 3, 100);
		super.remove(reason);
	}

	@Override
	public void tickDespawn() {
		++this.ticksInAir;
		if (this.ticksInAir >= 80)
			this.remove(Entity.RemovalReason.DISCARDED);
	}

	@Override
	public void shoot(double x, double y, double z, float speed, float divergence) {
		super.shoot(x, y, z, speed, divergence);
		this.ticksInAir = 0;
	}

	public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

	@Override
	public void setSoundEvent(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.EMPTY;
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		if (!this.level().isClientSide) 
			this.remove(Entity.RemovalReason.DISCARDED);
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		if (!this.level().isClientSide) 
			this.remove(Entity.RemovalReason.DISCARDED);
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(Items.AIR);
	}

	public void summonAoE(Entity entity, ParticleOptions particle, int yOffset, int duration, float radius, int effectTime) {
		var areaEffectCloudEntity = new AreaEffectCloud(entity.level(), entity.getX(), entity.getY() + yOffset, entity.getZ());
		areaEffectCloudEntity.setRadius(radius);
		areaEffectCloudEntity.setDuration(duration);
		areaEffectCloudEntity.setParticle(particle);
		areaEffectCloudEntity.setRadiusPerTick(-areaEffectCloudEntity.getRadius() / (float) areaEffectCloudEntity.getDuration());
		var effects = Arrays.asList(MobEffects.MOVEMENT_SPEED, MobEffects.MOVEMENT_SLOWDOWN, MobEffects.DIG_SPEED, MobEffects.DIG_SLOWDOWN, 
				MobEffects.DAMAGE_BOOST, MobEffects.HEAL, MobEffects.HARM, MobEffects.JUMP, MobEffects.CONFUSION, MobEffects.REGENERATION, 
				MobEffects.DAMAGE_RESISTANCE, MobEffects.FIRE_RESISTANCE, MobEffects.WATER_BREATHING, MobEffects.INVISIBILITY, MobEffects.BLINDNESS, 
				MobEffects.NIGHT_VISION, MobEffects.HUNGER, MobEffects.WEAKNESS, MobEffects.POISON, MobEffects.WITHER,
				MobEffects.HEALTH_BOOST, MobEffects.ABSORPTION, MobEffects.SATURATION, MobEffects.GLOWING, MobEffects.LEVITATION, MobEffects.LUCK, 
				MobEffects.UNLUCK, MobEffects.SLOW_FALLING, MobEffects.CONDUIT_POWER, MobEffects.DOLPHINS_GRACE, MobEffects.BAD_OMEN, 
				MobEffects.HERO_OF_THE_VILLAGE, MobEffects.DARKNESS);
		areaEffectCloudEntity.addEffect(new MobEffectInstance(effects.get(this.random.nextInt(effects.size())), effectTime));
		this.playSound(ModSounds.JACKOBOMB_SOUND.get(), 0.5f, 1.0f);
		entity.level().addFreshEntity(areaEffectCloudEntity);
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double distance) {
		return true;
	}

	public void setProperties(float pitch, float yaw, float roll, float modifierZ) {
		var f = 0.017453292F;
		var x = -Mth.sin(yaw * f) * Mth.cos(pitch * f);
		var y = -Mth.sin((pitch + roll) * f);
		var z = Mth.cos(yaw * f) * Mth.cos(pitch * f);
		this.shoot(x, y, z, modifierZ, 0);
	}
}
