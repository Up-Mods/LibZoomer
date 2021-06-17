package io.github.ennuil.libzoomertest;

import org.lwjgl.glfw.GLFW;

import io.github.ennuil.libzoomer.api.ZoomInstance;
import io.github.ennuil.libzoomer.api.ZoomRegistry;
import io.github.ennuil.libzoomer.api.modifiers.CinematicCameraMouseModifier;
import io.github.ennuil.libzoomer.api.modifiers.ContainingMouseModifier;
import io.github.ennuil.libzoomer.api.modifiers.SpyglassMouseModifier;
import io.github.ennuil.libzoomer.api.overlays.NoZoomOverlay;
import io.github.ennuil.libzoomer.api.overlays.SpyglassZoomOverlay;
import io.github.ennuil.libzoomer.api.transitions.InstantTransitionMode;
import io.github.ennuil.libzoomer.api.transitions.SmoothTransitionMode;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpyglassItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class LibZoomerTestMod implements ModInitializer {
    // Michael. He's a reimplementation of the spyglass. Tests if the spyglass can be replicated.
    public static Item MICHAEL_ITEM = new SpyglassItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1));

    @Override
    public void onInitialize() {
        // Michelle. She's an implementation of a very simple zoom key. Tests if there are zoom instance conflicts and spyglass-unrelated things.
        KeyBinding michelle = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.libzoomertest.michelle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "key.libzoomertest.category"));
        
        // Michael's Zoom Instance
        ZoomInstance zoomInstance = ZoomRegistry.registerInstance(new ZoomInstance(
            new Identifier("libzoomertest:zoom"),
            10.0F, new SmoothTransitionMode(),
            new SpyglassMouseModifier(),
            new SpyglassZoomOverlay(new Identifier("libzoomertest:textures/misc/michael.png"))
        ));
        // Michelle's Zoom Instance
        ZoomInstance electricBoogaloo = ZoomRegistry.registerInstance(new ZoomInstance(
            new Identifier("libzoomertest:zoom2"),
            3.0F, new InstantTransitionMode(),
            new CinematicCameraMouseModifier(),
            new NoZoomOverlay()
        ));

        // This prints out all zoom instances registered so far and some extra info
        for (ZoomInstance instance : ZoomRegistry.getZoomInstances()) {
            System.out.println("Id: " + instance.getInstanceId() + " | Zooming: " + instance.getZoom() + " | Divisor: " + instance.getZoomDivisor());
        }
        
        // Register the Michael item
        Registry.register(Registry.ITEM, new Identifier("libzoomertest:michael"), MICHAEL_ITEM);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // This is how you get a spyglass-like zoom working
            if (client.player == null) return;
            if (client.options.getPerspective().isFirstPerson() && (client.player.isUsingItem() && client.player.getActiveItem().isOf(MICHAEL_ITEM))) {
                zoomInstance.setZoom(true);
            } else {
                zoomInstance.setZoom(false);
            }

            // And this is how you get a simple zoom button working
            electricBoogaloo.setZoom(michelle.isPressed());
        });
    }
}
