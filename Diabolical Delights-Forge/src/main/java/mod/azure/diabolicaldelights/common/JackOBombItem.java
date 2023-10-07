package mod.azure.diabolicaldelights.common;

import java.util.function.Consumer;
import java.util.function.Supplier;

import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.diabolicaldelights.client.JackOBombItemRender;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class JackOBombItem extends Item implements GeoItem {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public JackOBombItem() {
		super(new Item.Properties().rarity(Rarity.RARE).fireResistant().stacksTo(64));
	}

	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		var itemStack = user.getItemInHand(hand);
		if (!user.getCooldowns().isOnCooldown(this)) {
			user.getCooldowns().addCooldown(this, 25);
			if (!world.isClientSide) {
				var bomb = new JackOBombEntity(world, user);
				bomb.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.5F, 1.0F);
				bomb.setBaseDamage(0);
				bomb.setState(1);
				world.addFreshEntity(bomb);
			}
			if (!user.getAbilities().instabuild)
				itemStack.shrink(1);
			return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
		} else
			return InteractionResultHolder.fail(itemStack);
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return new JackOBombItemRender();
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return renderProvider;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "shoot_controller", event -> {
			if (event.getData(DataTickets.ITEM_RENDER_PERSPECTIVE) == ItemDisplayContext.GUI)
				return PlayState.STOP;
			return PlayState.CONTINUE;
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}
}
