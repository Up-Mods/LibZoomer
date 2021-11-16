package io.github.ennuil.libzoomer.impl;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

/**
 * An utility class whose sole purpose is to hold the spyglass tag
 */
public class SpyglassHelper {
    /**
     * The spyglass tag, which is used internally in order to unhardcode behavior specific to vanilla spyglasses
     */
    public static final Tag<Item> SPYGLASSES = TagFactory.ITEM.create(new Identifier("libzoomer", "spyglasses"));
}
