package io.github.joaoh1.libzoomer.api;

import java.util.HashSet;
import java.util.Set;

public class ZoomHelper {
    public static final Set<ZoomInstance> zoomInstances = new HashSet<>();

    public static ZoomInstance registerInstance(ZoomInstance instance) {
        for (ZoomInstance zoomInstance : zoomInstances) {
            if (zoomInstance.getInstanceId().equals(instance.getInstanceId())) {
                throw new RuntimeException("Multiple zoom instances with the ID " + zoomInstance.getInstanceId() + " were found!");
            }
        }
        return zoomInstances.add(instance) ? instance : null;
    }
}
