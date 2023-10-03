package mod.azure.diabolicaldelights.common;

import mod.azure.azurelib.AzureLib;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

public class DiabolicalDelights implements ModInitializer {

	@Override
	public void onInitialize() {
		AzureLib.initialize();
		ModProjectiles.initialize();
		ModItems.initialize();
		ModSounds.initialize();
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> entries.accept(ModItems.JACKOBOMB_ITEM));
	}

	public static final ResourceLocation modResource(String name) {
		return new ResourceLocation("diabolicaldelights", name);
	}

	public record ModProjectiles() {

		public static EntityType<JackOBombEntity> JACKOBOMB_ENTITY = projectile(JackOBombEntity::new, "jack_o_bomb");

		private static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id) {
			EntityType<T> type = FabricEntityTypeBuilder.<T>create(MobCategory.MISC, factory).dimensions(new EntityDimensions(0.5F, 0.5F, true)).disableSummon().spawnableFarFromPlayer().trackRangeBlocks(90).trackedUpdateRate(1).build();
			Registry.register(BuiltInRegistries.ENTITY_TYPE, DiabolicalDelights.modResource(id), type);
			return type;
		}

		public static void initialize() {

		}
	}

	public record ModItems() {

		public static JackOBombItem JACKOBOMB_ITEM = item(new JackOBombItem(), "jack_o_bomb");

		static <T extends Item> T item(T c, String id) {
			Registry.register(BuiltInRegistries.ITEM, DiabolicalDelights.modResource(id), c);
			return c;
		}

		public static void initialize() {
		}

	}

	public record ModSounds() {

		public static SoundEvent JACKOBOMB_SOUND = of("diabolicaldelights.laugh");

		static SoundEvent of(String id) {
			var sound = SoundEvent.createVariableRangeEvent(DiabolicalDelights.modResource(id));
			Registry.register(BuiltInRegistries.SOUND_EVENT, DiabolicalDelights.modResource(id), sound);
			return sound;
		}

		public static void initialize() {
		}
	}
}