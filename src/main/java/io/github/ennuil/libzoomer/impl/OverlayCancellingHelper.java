package io.github.ennuil.libzoomer.impl;

public class OverlayCancellingHelper {
    private static boolean cancelOverlayRender;

    public static boolean getCancelOverlayRender() {
        return cancelOverlayRender;
    }

    public static boolean setCancelOverlayRender(boolean cancel) {
        return cancelOverlayRender = cancel;
    }
}
