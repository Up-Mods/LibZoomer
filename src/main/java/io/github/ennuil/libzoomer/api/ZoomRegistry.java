package io.github.ennuil.libzoomer.api;

import java.util.Set;

import org.jetbrains.annotations.ApiStatus;

import it.unimi.dsi.fastutil.objects.ReferenceArraySet;

/**
 * The class responsible for the handling the zoom instance registry.
 */
public class ZoomRegistry {
	private static final Set<ZoomInstance> ZOOM_INSTANCES = new ReferenceArraySet<>();
	private static boolean iterateZoom;
	private static boolean iterateTransitions;
	private static boolean iterateModifiers;
	private static boolean iterateOverlays;

	/**
	 * Registers a zoom instance into the internal set of zoom instances.
	 * Mandatory in order to make a zoom instance functional.
	 *
	 * @param instance An unregistered zoom instance.
	 * @return The zoom instance if registered, else, null.
	 */
	@ApiStatus.Internal
	protected static ZoomInstance registerInstance(ZoomInstance instance) {
		for (ZoomInstance zoomInstance : ZOOM_INSTANCES) {
			if (zoomInstance.getInstanceId().equals(instance.getInstanceId())) {
				throw new RuntimeException("Multiple zoom instances with the ID " + zoomInstance.getInstanceId() + " were found!");
			}
		}

		return ZOOM_INSTANCES.add(instance) ? instance : null;
	}

	/**
	 * Gets a set of all the registered zoom instances.
	 *
	 * @return A set of registered zoom instances.
	 */
	public static Set<ZoomInstance> getZoomInstances() {
		return ZOOM_INSTANCES;
	}

	/**
	 * Determines whenever an iteration through all active zoom instances is necessary.
	 *
	 * @return {@code true} if an iteration is needed, else {@code false} otherwise.
	 */
	public static boolean shouldIterateZoom() {
		return iterateZoom;
	}

	/**
	 * Sets the state that determines the need for an iteration through all active zoom instances.
	 *
	 * @apiNote This is an internal method that shouldn't be used by other mods.
	 * @param iterateZoom The new iteration state.
	 */
	@ApiStatus.Internal
	public static void setIterateZoom(boolean iterateZoom) {
		ZoomRegistry.iterateZoom = iterateZoom;
	}

	/**
	 * Determines whenever an iteration through all zoom instances with active transitions is necessary.
	 *
	 * @return {@code true} if an iteration is needed, else {@code false} otherwise.
	 */
	public static boolean shouldIterateTransitions() {
		return iterateTransitions;
	}

	/**
	 * Sets the state that determines the need for an iteration through all zoom instances with active transitions.
	 * This is an internal method and shouldn't be used by other mods.
	 *
	 * @param iterateTransitions The new iteration state.
	 */
	@ApiStatus.Internal
	public static void setIterateTransitions(boolean iterateTransitions) {
		ZoomRegistry.iterateTransitions = iterateTransitions;
	}

	/**
	 * Determines whenever an iteration through all zoom instances with active modifiers is necessary.
	 *
	 * @return {@code true} if an iteration is needed, else {@code false} otherwise.
	 */
	public static boolean shouldIterateModifiers() {
		return iterateModifiers;
	}

	/**
	 * Sets the state that determines the need for an iteration through all zoom instances with active modifiers.
	 *
	 * @apiNote This is an internal method that shouldn't be used by other mods.
	 * @param iterateModifiers The new iteration state.
	 */
	@ApiStatus.Internal
	public static void setIterateModifiers(boolean iterateModifiers) {
		ZoomRegistry.iterateModifiers = iterateModifiers;
	}

	/**
	 * Determines whenever an iteration through all zoom instances with active overlays is necessary.
	 *
	 * @return {@code true} if an iteration is needed, else {@code false} otherwise.
	 */
	public static boolean shouldIterateOverlays() {
		return iterateOverlays;
	}

	/**
	 * Sets the state that determines the need for an iteration through all zoom instances with active overlays.
	 *
	 * @apiNote This is an internal method that shouldn't be used by other mods.
	 * @param iterateOverlays The new iteration state.
	 */
	@ApiStatus.Internal
	public static void setIterateOverlays(boolean iterateOverlays) {
		ZoomRegistry.iterateOverlays = iterateOverlays;
	}
}
