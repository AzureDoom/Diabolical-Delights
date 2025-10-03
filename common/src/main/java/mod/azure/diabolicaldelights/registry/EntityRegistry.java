package mod.azure.diabolicaldelights.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

import mod.azure.diabolicaldelights.entities.JackOBombEntity;
import mod.azure.diabolicaldelights.entities.SilencedEntityTypeBuilder;
import mod.azure.diabolicaldelights.services.Services;

public class EntityRegistry {

    public static final Supplier<EntityType<JackOBombEntity>> JACK_0_BOMB = registerEntity(
        "jack_o_bomb",
        JackOBombEntity::new,
        MobCategory.MISC,
        0.5F,
        0.5F
    );

    static <T extends Entity> Supplier<EntityType<T>> registerEntity(
        String entityName,
        EntityType.EntityFactory<T> entity,
        MobCategory mobCategory,
        float width,
        float height
    ) {
        return Services.COMMON_REGISTRY.register(
            BuiltInRegistries.ENTITY_TYPE,
            entityName,
            () -> create(entity, mobCategory, width, height).buildWithoutDataFixerCheck()
        );
    }

    static <T extends Entity> SilencedEntityTypeBuilder create(
        EntityType.EntityFactory<T> entity,
        MobCategory mobCategory,
        float width,
        float height
    ) {
        return (SilencedEntityTypeBuilder) EntityType.Builder.of(entity, mobCategory)
            .sized(width, height)
            .clientTrackingRange(256)
            .canSpawnFarFromPlayer();
    }

    public static void initialize() {}
}
