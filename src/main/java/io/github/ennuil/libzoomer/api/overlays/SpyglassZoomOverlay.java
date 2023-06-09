package io.github.ennuil.libzoomer.api.overlays;

import io.github.ennuil.libzoomer.api.ZoomOverlay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.render.RenderLayer;
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
		float f = (float) Math.min(scaledWidth, scaledHeight);
		float h = Math.min((float) scaledWidth / f, (float) scaledHeight / f) * scale;
		int i = MathHelper.floor(f * h);
		int j = MathHelper.floor(f * h);
		int k = (scaledWidth - i) / 2;
		int l = (scaledHeight - j) / 2;
		int m = k + i;
		int n = l + j;
		graphics.drawTexture(textureId, k, l, -90, 0.0F, 0.0F, i, j, i, j);
		graphics.fill(RenderLayer.getGuiOverlay(), 0, n, scaledWidth, scaledHeight, -90, 0xFF000000);
		graphics.fill(RenderLayer.getGuiOverlay(), 0, 0, scaledWidth, l, -90, 0xFF000000);
		graphics.fill(RenderLayer.getGuiOverlay(), 0, l, k, n, -90, 0xFF000000);
		graphics.fill(RenderLayer.getGuiOverlay(), m, l, scaledWidth, n, -90, 0xFF000000);
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
