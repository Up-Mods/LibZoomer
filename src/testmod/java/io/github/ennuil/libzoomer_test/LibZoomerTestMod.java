package io.github.ennuil.libzoomer_test;

import io.github.ennuil.libzoomer.api.ZoomInstance;
import io.github.ennuil.libzoomer.api.ZoomRegistry;
import io.github.ennuil.libzoomer.api.modifiers.CinematicCameraMouseModifier;
import io.github.ennuil.libzoomer.api.modifiers.SpyglassMouseModifier;
import io.github.ennuil.libzoomer.api.overlays.SpyglassZoomOverlay;
import io.github.ennuil.libzoomer.api.transitions.InstantTransitionMode;
import io.github.ennuil.libzoomer.api.transitions.SmoothTransitionMode;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpyglassItem;
import org.lwjgl.glfw.GLFW;

public class LibZoomerTestMod implements ModInitializer, ClientModInitializer {
	// Michael's Zoom Instance
	private static final ZoomInstance MICHAEL_ZOOM = new ZoomInstance(
		new ResourceLocation("libzoomer_test:zoom"),
		10.0F, new SmoothTransitionMode(),
		new SpyglassMouseModifier(),
		new SpyglassZoomOverlay(new ResourceLocation("libzoomer_test:textures/misc/michael.png"))
	);

	// Michelle's Zoom Instance
	private static final ZoomInstance MICHELLE_ZOOM = new ZoomInstance(
		new ResourceLocation("libzoomer_test:zoom_2"),
		3.0F, new InstantTransitionMode(),
		new CinematicCameraMouseModifier(),
		null
	);

	// Michael. He's a reimplementation of the spyglass. Tests if the spyglass can be replicated.
	private static final Item MICHAEL_ITEM = new SpyglassItem(new Item.Properties().stacksTo(1));

	// Michelle. She's an implementation of a very simple zoom key. Tests if there are zoom instance conflicts and spyglass-unrelated things.
	private static final KeyMapping MICHELLE_KEY = new KeyMapping(
		"key.libzoomer_test.michelle",
		GLFW.GLFW_KEY_V,
		"key.libzoomer_test.category"
	);

	@Override
	public void onInitialize() {
		// Register the Michael item
		Registry.register(BuiltInRegistries.ITEM, new ResourceLocation("libzoomer_test:michael"), MICHAEL_ITEM);
	}

	@Override
	public void onInitializeClient() {
		// This prints out all zoom instances registered so far and some extra info
		for (ZoomInstance instance : ZoomRegistry.getZoomInstances()) {
			System.out.println("Id: " + instance.getInstanceId() + " | Zooming: " + instance.getZoom() + " | Divisor: " + instance.getZoomDivisor());
		}

		KeyBindingHelper.registerKeyBinding(MICHELLE_KEY);

		// ClientTickEvents.Start is desirable in order to reduce latency issues, since the render tick, which happens later,
		// handles the effects of zooming. Setting the zoom after it means a one tick delay for zooming to apply
		ClientTickEvents.START_CLIENT_TICK.register(minecraft -> {
			// This is how you get a spyglass-like zoom working
			if (minecraft.player == null) return;

			MICHAEL_ZOOM.setZoom(
				minecraft.options.getCameraType().isFirstPerson()
					&& (
					minecraft.player.isUsingItem()
						&& minecraft.player.getUseItem().is(MICHAEL_ITEM)
				)
			);

			// And this is how you get a simple zoom button working
			MICHELLE_ZOOM.setZoom(MICHELLE_KEY.isDown());
		});
	}
}
