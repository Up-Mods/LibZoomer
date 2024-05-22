package io.github.ennuil.libzoomer.impl;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * An utility class whose sole purpose is to hold the spyglass tag.
 */
public class SpyglassHelper {
    /**
     * The spyglass tag, which is used internally in order to unhardcode behavior specific to vanilla spyglasses.
     */
    public static final TagKey<Item> SPYGLASSES = TagKey.create(
		Registries.ITEM, new ResourceLocation("libzoomer", "spyglasses")
	);
}
