package mod.azure.diabolicaldelights.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

import mod.azure.diabolicaldelights.CommonMod;
import mod.azure.diabolicaldelights.services.Services;

public class SoundRegistry {

    public static Supplier<SoundEvent> JACKOBOMB_SOUND = registerSound(
        "diabolicaldelights.laugh"
    );

    static Supplier<SoundEvent> registerSound(String soundName) {
        return Services.COMMON_REGISTRY.register(
            BuiltInRegistries.SOUND_EVENT,
            soundName,
            () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource(soundName))
        );
    }

    public static void initialize() {}
}
