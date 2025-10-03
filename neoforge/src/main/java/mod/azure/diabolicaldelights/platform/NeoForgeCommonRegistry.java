package mod.azure.diabolicaldelights.platform;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

import mod.azure.diabolicaldelights.NeoForgeMod;
import mod.azure.diabolicaldelights.services.CommonRegistry;

public class NeoForgeCommonRegistry implements CommonRegistry {

    @Override
    public <T> Supplier<T> register(Registry<? super T> registry, String registryName, Supplier<? extends T> supplier) {
        if (registry == BuiltInRegistries.ITEM) {
            return (Supplier<T>) NeoForgeMod.itemDeferredRegister.register(registryName, (Supplier<Item>) supplier);
        } else if (registry == BuiltInRegistries.SOUND_EVENT) {
            return (Supplier<T>) NeoForgeMod.soundDeferredRegister.register(
                registryName,
                (Supplier<SoundEvent>) supplier
            );
        } else if (registry == BuiltInRegistries.ENTITY_TYPE) {
            return (Supplier<T>) NeoForgeMod.entityDeferredRegister.register(
                registryName,
                (Supplier<EntityType<?>>) supplier
            );
        }

        throw new IllegalArgumentException(
            "Received registration attempt for an unhandled registry. Registry: " + registry
        );
    }
}
