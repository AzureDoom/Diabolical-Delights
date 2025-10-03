package mod.azure.diabolicaldelights.services;

import net.minecraft.core.Registry;

import java.util.function.Supplier;

/**
 * The CommonRegistry interface defines a method to register objects into a Minecraft game registry. It serves as a
 * utility for handling registry-related tasks, allowing for simplified and reusable operations when working with modded
 * content.
 */
public interface CommonRegistry {

    /**
     * Registers an object into the specified registry with the given name and supplier. This method ensures the
     * registration is handled properly and returns a supplier to access the registered instance.
     *
     * @param <T>          The type of the object to register.
     * @param registry     The registry where the object will be registered.
     * @param registryName The unique name to associate with the registered object.
     * @param supplier     A supplier providing the instance of the object to register.
     * @return A supplier for the registered object, allowing access to the instance.
     */
    <T> Supplier<T> register(
        Registry<? super T> registry,
        String registryName,
        Supplier<? extends T> supplier
    );
}
