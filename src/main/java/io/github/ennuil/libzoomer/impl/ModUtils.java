package io.github.ennuil.libzoomer.impl;

import net.minecraft.resources.ResourceLocation;

/**
 * An internal class for holding the optimal identifier creator.
 */
public class ModUtils {
	public static final String MOD_NAMESPACE = "libzoomer";

	private static final ResourceLocation MOD_NAMESPACE_ID = ResourceLocation.fromNamespaceAndPath(MOD_NAMESPACE, "");

	public static ResourceLocation id(String path) {
		return MOD_NAMESPACE_ID.withPath(path);
	}
}
