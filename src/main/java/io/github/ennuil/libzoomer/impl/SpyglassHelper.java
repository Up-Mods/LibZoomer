package io.github.ennuil.libzoomer.impl;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * An utility class whose sole purpose is to hold the spyglass tag
 */
public class SpyglassHelper {
    /**
     * The spyglass tag, which is used internally in order to unhardcode behavior specific to vanilla spyglasses
     */
    public static final TagKey<Item> SPYGLASSES = TagKey.of(Registry.ITEM_KEY, new Identifier("libzoomer", "spyglasses"));
}
