package io.github.ennuil.libzoomer.api.modifiers;

import io.github.ennuil.libzoomer.api.MouseModifier;
import net.minecraft.util.Identifier;

public class NoMouseModifier implements MouseModifier {
    private static final Identifier TRANSITION_ID = new Identifier("libzoomer:no_modifier");
    
    @Override
    public Identifier getIdentifier() {
        return TRANSITION_ID;
    }

    @Override
    public boolean getActive() {
        return false;
    }

    @Override
    public double applyXModifier(double cursorDeltaX, double cursorSensitivity, double mouseUpdateTimeDelta, double targetDivisor, double transitionMultiplier) {
        return cursorDeltaX;
    }

    @Override
    public double applyYModifier(double cursorDeltaY, double cursorSensitivity, double mouseUpdateTimeDelta, double targetDivisor, double transitionMultiplier) {
        return cursorDeltaY;
    }

    @Override
    public void tick(boolean active) {}
}
