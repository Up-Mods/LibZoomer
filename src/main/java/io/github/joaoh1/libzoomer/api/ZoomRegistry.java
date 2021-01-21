package io.github.joaoh1.libzoomer.api;

import java.util.HashSet;
import java.util.Set;

public class ZoomRegistry {
    private static final Set<ZoomInstance> zoomInstances = new HashSet<>();

    /**
     * Registers a zoom instance into the internal set of zoom instances.
     * Mandatory in order to make a zoom instance functional. 
     * @param instance An unregistered zoom instance.
     * @return The zoom instance if registered, else, null.
     */
    public static ZoomInstance registerInstance(ZoomInstance instance) {
        for (ZoomInstance zoomInstance : zoomInstances) {
            if (zoomInstance.getInstanceId().equals(instance.getInstanceId())) {
                throw new RuntimeException("Multiple zoom instances with the ID " + zoomInstance.getInstanceId() + " were found!");
            }
        }
        return zoomInstances.add(instance) ? instance : null;
    }

    public static Set<ZoomInstance> getZoomInstances() {
        return zoomInstances;
    }
}
