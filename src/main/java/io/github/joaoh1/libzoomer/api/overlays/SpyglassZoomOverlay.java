package io.github.joaoh1.libzoomer.api.overlays;

import com.mojang.blaze3d.systems.RenderSystem;

import io.github.joaoh1.libzoomer.api.ZoomOverlay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class SpyglassZoomOverlay implements ZoomOverlay {
    private Identifier textureId;
    private MinecraftClient client;
    private float scale;
    private boolean active;

    public SpyglassZoomOverlay(Identifier textureId) {
        this.textureId = textureId;
        this.client = MinecraftClient.getInstance();
        this.scale = 0.5F;
        this.active = false;
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier("libzoomer:spyglass_zoom");
    }

    @Override
    public boolean getActive() {
        return this.active;
    }

    @Override
    public MinecraftClient setClient(MinecraftClient newClient) {
        return this.client = newClient;
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
        RenderSystem.disableAlphaTest();
        this.client.getTextureManager().bindTexture(this.textureId);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        float f = Math.min(scaledWidth, scaledHeight);
        float h = Math.min(scaledWidth / f, scaledHeight / f) * this.scale;
        float i = f * h;
        float j = (scaledWidth - i) / 2.0F;
        float k = (scaledHeight - i) / 2.0F;
        float l = j + i;
        float m = k + i;
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(j, m, -90.0D).texture(0.0F, 1.0F).next();
        bufferBuilder.vertex(l, m, -90.0D).texture(1.0F, 1.0F).next();
        bufferBuilder.vertex(l, k, -90.0D).texture(1.0F, 0.0F).next();
        bufferBuilder.vertex(j, k, -90.0D).texture(0.0F, 0.0F).next();
        tessellator.draw();
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
        RenderSystem.enableAlphaTest();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void tick(boolean active, double divisor, double transitionMultiplier) {
        float f = this.client.getLastFrameDuration();
        this.active = active;
        this.scale = MathHelper.lerp(0.5F * f, this.scale, 1.125F);
        if (this.client.options.getPerspective().isFirstPerson()) {
           if (!this.active) {
              this.scale = 0.5F;
           }
        }
    }
}
