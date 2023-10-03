package mod.azure.diabolicaldelights.common;

import mod.azure.azurelib.AzureLib;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod("diabolicaldelights")
public class DiabolicalDelights {

	public static DiabolicalDelights instance;

	public DiabolicalDelights() {
		var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModItems.ITEMS.register(modEventBus);
		ModProjectiles.ENTITY_TYPES.register(modEventBus);
		ModSounds.MOD_SOUNDS.register(modEventBus);
		MinecraftForge.EVENT_BUS.register(this);
		AzureLib.initialize();
		modEventBus.addListener(this::addCreative);
	}

	private void addCreative(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.COMBAT)
			event.accept(ModItems.JACKOBOMB_ITEM.get());
	}

	public static final ResourceLocation modResource(String name) {
		return new ResourceLocation("diabolicaldelights", name);
	}

	public record ModProjectiles() {
		public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "diabolicaldelights");
		public static final RegistryObject<EntityType<JackOBombEntity>> JACKOBOMB_ENTITY = ENTITY_TYPES.register("jack_o_bomb", () -> EntityType.Builder.<JackOBombEntity>of(JackOBombEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).noSummon().canSpawnFarFromPlayer().clientTrackingRange(10).build(DiabolicalDelights.modResource("jack_o_bomb").toString()));
	}

	public record ModItems() {
		public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "diabolicaldelights");
		public static final RegistryObject<Item> JACKOBOMB_ITEM = ITEMS.register("jack_o_bomb", () -> new JackOBombItem());
	}

	public record ModSounds() {
		public static final DeferredRegister<SoundEvent> MOD_SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "diabolicaldelights");
		public static final RegistryObject<SoundEvent> JACKOBOMB_SOUND = MOD_SOUNDS.register("diabolicaldelights.laugh", () -> SoundEvent.createVariableRangeEvent(DiabolicalDelights.modResource("diabolicaldelights.laugh")));
	}
}