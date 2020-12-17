package io.github.joaoh1.libzoomer;

import org.lwjgl.glfw.GLFW;

import io.github.joaoh1.libzoomer.api.transitions.InstantTransitionMode;
import io.github.joaoh1.libzoomer.api.transitions.SmoothTransitionMode;
import io.github.joaoh1.libzoomer.api.ZoomHelper;
import io.github.joaoh1.libzoomer.api.ZoomInstance;
import io.github.joaoh1.libzoomer.api.modifiers.CinematicCameraMouseModifier;
import io.github.joaoh1.libzoomer.api.modifiers.ZoomDivisorMouseModifier;
import io.github.joaoh1.libzoomer.api.overlays.SpyglassZoomOverlay;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpyglassItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

//TODO - Eliminate Michael and the keybind
public class ExampleMod implements ModInitializer {
    public static Item MICHAEL_ITEM = new SpyglassItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1));

    @Override
    public void onInitialize() {
        KeyBinding zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.libzoomer.zoom", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "key.libzoomer.category"));
        ZoomInstance zoomInstance = ZoomHelper.registerInstance(new ZoomInstance(new Identifier("libzoomer:zoom"), 10.0F, new SmoothTransitionMode(0.5f), new ZoomDivisorMouseModifier(), new SpyglassZoomOverlay(new Identifier("libzoomer:textures/misc/michael.png"))));
        ZoomInstance electricBoogaloo = ZoomHelper.registerInstance(new ZoomInstance(new Identifier("libzoomer:zoom2"), 3.0F, new InstantTransitionMode(), new CinematicCameraMouseModifier()));
        for (ZoomInstance instance : ZoomHelper.zoomInstances) {
            System.out.println("Id: " + instance.getInstanceId() + " | Zooming:" + instance.getZoom() + " | Divisor: " + instance.getZoomDivisor());
        }
        Registry.register(Registry.ITEM, new Identifier("libzoomer:michael"), MICHAEL_ITEM);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            if (client.options.getPerspective().isFirstPerson() && (client.player.isUsingItem() && client.player.getActiveItem().isOf(MICHAEL_ITEM))) {
                zoomInstance.setZoom(true);
            } else {
                zoomInstance.setZoom(false);
            }

            electricBoogaloo.setZoom(zoomKey.isPressed());
        });
    }
}
