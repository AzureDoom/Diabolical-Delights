package mod.azure.diabolicaldelights.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

import mod.azure.diabolicaldelights.items.JackOBombItem;
import mod.azure.diabolicaldelights.services.Services;

public class ItemRegistry {

    public static final Supplier<Item> JACK_0_BOMB = registerItem(
        "jack_o_bomb",
        JackOBombItem::new
    );

    static <T extends Item> Supplier<T> registerItem(String itemName, Supplier<T> item) {
        return Services.COMMON_REGISTRY.register(BuiltInRegistries.ITEM, itemName, item);
    }

    private ItemRegistry() {}

    public static void initialize() {}
}
