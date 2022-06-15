package io.github.ennuil.libzoomer.api.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tessellator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormats;

import io.github.ennuil.libzoomer.api.ZoomOverlay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
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
     * @param textureId The texture identifier for the spyglass overlay
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
    public void renderOverlay() {
        int scaledWidth = this.client.getWindow().getScaledWidth();
        int scaledHeight = this.client.getWindow().getScaledHeight();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.textureId);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBufferBuilder();
        float f = Math.min(scaledWidth, scaledHeight);
        float h = Math.min(scaledWidth / f, scaledHeight / f) * this.scale;
        float i = f * h;
        float j = (scaledWidth - i) / 2.0F;
        float k = (scaledHeight - i) / 2.0F;
        float l = j + i;
        float m = k + i;
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(j, m, -90.0D).uv(0.0F, 1.0F).next();
        bufferBuilder.vertex(l, m, -90.0D).uv(1.0F, 1.0F).next();
        bufferBuilder.vertex(l, k, -90.0D).uv(1.0F, 0.0F).next();
        bufferBuilder.vertex(j, k, -90.0D).uv(0.0F, 0.0F).next();
        tessellator.draw();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.disableTexture();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(0.0D, scaledHeight, -90.0D).color(0, 0, 0, 255).next();
        bufferBuilder.vertex(scaledWidth, scaledHeight, -90.0D).color(0, 0, 0, 255).next();
        bufferBuilder.vertex(scaledWidth, m, -90.0D).color(0, 0, 0, 255).next();
        bufferBuilder.vertex(0.0D, m, -90.0D).color(0, 0, 0, 255).next();
        bufferBuilder.vertex(0.0D, k, -90.0D).color(0, 0, 0, 255).next();
        bufferBuilder.vertex(scaledWidth, k, -90.0D).color(0, 0, 0, 255).next();
        bufferBuilder.vertex(scaledWidth, 0.0D, -90.0D).color(0, 0, 0, 255).next();
        bufferBuilder.vertex(0.0D, 0.0D, -90.0D).color(0, 0, 0, 255).next();
        bufferBuilder.vertex(0.0D, m, -90.0D).color(0, 0, 0, 255).next();
        bufferBuilder.vertex(j, m, -90.0D).color(0, 0, 0, 255).next();
        bufferBuilder.vertex(j, k, -90.0D).color(0, 0, 0, 255).next();
        bufferBuilder.vertex(0.0D, k, -90.0D).color(0, 0, 0, 255).next();
        bufferBuilder.vertex(l, m, -90.0D).color(0, 0, 0, 255).next();
        bufferBuilder.vertex(scaledWidth, m, -90.0D).color(0, 0, 0, 255).next();
        bufferBuilder.vertex(scaledWidth, k, -90.0D).color(0, 0, 0, 255).next();
        bufferBuilder.vertex(l, k, -90.0D).color(0, 0, 0, 255).next();
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
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
