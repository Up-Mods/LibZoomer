package io.github.joaoh1.libzoomer.api;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class SpyglassHelper {
    public static final Tag<Item> SPYGLASSES = TagRegistry.item(new Identifier("libzoomer", "spyglasses"));
}
