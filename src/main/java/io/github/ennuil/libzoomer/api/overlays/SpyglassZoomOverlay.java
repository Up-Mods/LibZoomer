package io.github.ennuil.libzoomer.api.overlays;

import io.github.ennuil.libzoomer.api.ZoomOverlay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

/**
 * An implementation of the spyglass overlay as a zoom overlay
 */
public class SpyglassZoomOverlay implements ZoomOverlay {
    private static final Identifier OVERLAY_ID = new Identifier("libzoomer:spyglass_zoom");
    private final Identifier textureId;
    private MinecraftClient client;
    private float scale;
    private boolean active;

    /**
     * Initializes an instance of the spyglass mouse modifier with the specified texture identifier
     *
	 * @param textureId the texture identifier for the spyglass overlay
    */
    public SpyglassZoomOverlay(Identifier textureId) {
        this.textureId = textureId;
        this.scale = 0.5F;
        this.active = false;
		this.ensureClient();
    }

    @Override
    public Identifier getIdentifier() {
        return OVERLAY_ID;
    }

    @Override
    public boolean getActive() {
        return this.active;
    }

    @Override
    public boolean cancelOverlayRendering() {
        return true;
    }

    @Override
    public void renderOverlay(GuiGraphics graphics) {
        int scaledWidth = graphics.getScaledWindowWidth();
        int scaledHeight = graphics.getScaledWindowHeight();
		float smallerLength = (float) Math.min(scaledWidth, scaledHeight);
		float scaledSmallerLength = Math.min((float) scaledWidth / smallerLength, (float) scaledHeight / smallerLength) * scale;
		int width = MathHelper.floor(smallerLength * scaledSmallerLength);
		int height = MathHelper.floor(smallerLength * scaledSmallerLength);
		int x = (scaledWidth - width) / 2;
		int y = (scaledHeight - height) / 2;
		int borderX = x + width;
		int borderY = y + height;
		graphics.drawTexture(textureId, x, y, -90, 0.0F, 0.0F, width, height, width, height);
		graphics.fill(RenderLayer.getGuiOverlay(), 0, borderY, scaledWidth, scaledHeight, -90, CommonColors.BLACK);
		graphics.fill(RenderLayer.getGuiOverlay(), 0, 0, scaledWidth, y, -90, CommonColors.BLACK);
		graphics.fill(RenderLayer.getGuiOverlay(), 0, y, x, borderY, -90, CommonColors.BLACK);
		graphics.fill(RenderLayer.getGuiOverlay(), borderX, y, scaledWidth, borderY, -90, CommonColors.BLACK);
    }

    @Override
    public void tick(boolean active, double divisor, double transitionMultiplier) {
        this.active = active;
    }

    @Override
    public void tickBeforeRender() {
		this.ensureClient();
        if (this.client.options.getPerspective().isFirstPerson()) {
            if (!this.active) {
                this.scale = 0.5F;
            } else {
                float lastFrameDuration = this.client.getLastFrameDuration();
                this.scale = MathHelper.lerp(0.5F * lastFrameDuration, this.scale, 1.125F);
            }
        }
    }

	private void ensureClient() {
		if (this.client == null) {
			this.client = MinecraftClient.getInstance();
		}
	}
}
