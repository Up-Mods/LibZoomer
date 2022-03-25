package io.github.ennuil.libzoomer.api;

import java.util.Set;

import it.unimi.dsi.fastutil.objects.ReferenceArraySet;

/**
 * The class responsible for the handling the zoom instance registry.
 */
public class ZoomRegistry {
    private static final Set<ZoomInstance> zoomInstances = new ReferenceArraySet<>();
    private static boolean iterateZoom;
    private static boolean iterateTransitions;
    private static boolean iterateModifiers;
    private static boolean iterateOverlays;

    /**
     * Registers a zoom instance into the internal set of zoom instances.
     * Mandatory in order to make a zoom instance functional. 
     * @param instance An unregistered zoom instance.
     * @return The zoom instance if registered, else, null.
     */
    protected static ZoomInstance registerInstance(ZoomInstance instance) {
        for (ZoomInstance zoomInstance : zoomInstances) {
            if (zoomInstance.getInstanceId().equals(instance.getInstanceId())) {
                throw new RuntimeException("Multiple zoom instances with the ID " + zoomInstance.getInstanceId() + " were found!");
            }
        }
        return zoomInstances.add(instance) ? instance : null;
    }

    /**
     * Gets a set of all the registered zoom instances.
     * @return A set of registered zoom instances.
     */
    public static Set<ZoomInstance> getZoomInstances() {
        return zoomInstances;
    }

    // TODO - Document!
    public static boolean shouldIterateZoom() {
        return iterateZoom;
    }

    public static void setIterateZoom(boolean iterateZoom) {
        ZoomRegistry.iterateZoom = iterateZoom;
    }

    public static boolean shouldIterateTransitions() {
        return iterateTransitions;
    }

    public static void setIterateTransitions(boolean iterateTransitions) {
        ZoomRegistry.iterateTransitions = iterateTransitions;
    }
    
    public static boolean shouldIterateModifiers() {
        return iterateModifiers;
    }

    public static void setIterateModifiers(boolean iterateModifiers) {
        ZoomRegistry.iterateModifiers = iterateModifiers;
    }

    public static boolean shouldIterateOverlays() {
        return iterateOverlays;
    }

    public static void setIterateOverlays(boolean iterateOverlays) {
        ZoomRegistry.iterateOverlays = iterateOverlays;
    }
}
