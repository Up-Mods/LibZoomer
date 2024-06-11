package io.github.ennuil.libzoomer.impl;

import net.minecraft.resources.ResourceLocation;

/**
 * An internal class for holding the optimal identifier creator.
 */
public class ModUtils {
	public static final String MOD_NAMESPACE = "libzoomer";

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_NAMESPACE, ResourceLocation.assertValidPath(MOD_NAMESPACE, path));
	}
}
