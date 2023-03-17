package io.github.ennuil.libzoomer.impl;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import org.quiltmc.qsl.tag.api.QuiltTagKey;
import org.quiltmc.qsl.tag.api.TagType;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

/**
 * An utility class whose sole purpose is to hold the spyglass tag
 */
public class SpyglassHelper {
    /**
     * The spyglass tag, which is used internally in order to unhardcode behavior specific to vanilla spyglasses
     */
    public static final TagKey<Item> SPYGLASSES = QuiltTagKey.of(
			RegistryKeys.ITEM, new Identifier("libzoomer", "spyglasses"), TagType.CLIENT_FALLBACK
	);
}
