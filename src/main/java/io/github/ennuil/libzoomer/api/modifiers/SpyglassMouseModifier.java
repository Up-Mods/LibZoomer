package io.github.ennuil.libzoomer.api.modifiers;

import io.github.ennuil.libzoomer.api.MouseModifier;
import net.minecraft.util.Identifier;

/**
 * An implementation of the spyglass' reduction of the mouse sensitivity as a mouse modifier
*/
public class SpyglassMouseModifier implements MouseModifier {
    private static final Identifier MODIFIER_ID = new Identifier("libzoomer:spyglass");
    private boolean active;

    /**
     * Initializes an instance of the spyglass mouse modifier
    */
    public SpyglassMouseModifier() {
        this.active = false;
    }

    @Override
    public Identifier getIdentifier() {
        return MODIFIER_ID;
    }

    @Override
    public boolean getActive() {
        return this.active;
    }

    @Override
    public double applyXModifier(double cursorDeltaX, double cursorSensitivity, double mouseUpdateTimeDelta, double targetDivisor, double transitionMultiplier) {
        return cursorDeltaX / (this.active ? 8.0 : 1.0);
    }

    @Override
    public double applyYModifier(double cursorDeltaY, double cursorSensitivity, double mouseUpdateTimeDelta, double targetDivisor, double transitionMultiplier) {
        return cursorDeltaY / (this.active ? 8.0 : 1.0);
    }

    @Override
    public void tick(boolean active) {
        this.active = active;
    }
}
