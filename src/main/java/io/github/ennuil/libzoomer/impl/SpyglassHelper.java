package io.github.ennuil.libzoomer.impl;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.quiltmc.qsl.tag.api.QuiltTagKey;
import org.quiltmc.qsl.tag.api.TagType;

/**
 * An utility class whose sole purpose is to hold the spyglass tag.
 */
public class SpyglassHelper {
    /**
     * The spyglass tag, which is used internally in order to unhardcode behavior specific to vanilla spyglasses.
     */
    public static final TagKey<Item> SPYGLASSES = QuiltTagKey.of(
		Registries.ITEM, new ResourceLocation("libzoomer", "spyglasses"), TagType.CLIENT_FALLBACK
	);
}
