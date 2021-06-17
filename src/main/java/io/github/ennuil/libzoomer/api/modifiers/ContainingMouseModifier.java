package io.github.ennuil.libzoomer.api.modifiers;

import io.github.ennuil.libzoomer.api.MouseModifier;
import net.minecraft.util.Identifier;

//A sin was probably committed by using a lot of for each loops
/**
 * A mouse modifier that contains multiple mouse modifiers.
 */
public class ContainingMouseModifier implements MouseModifier {
    private Identifier transitionId = new Identifier("libzoomer:modifier_container");
    private boolean active;
    private MouseModifier[] modifiers;

    public ContainingMouseModifier(MouseModifier... modifiers) {
        this.active = false;
        this.modifiers = modifiers;
    }

    @Override
    public Identifier getIdentifier() {
        return this.transitionId;
    }

    @Override
    public boolean getActive() {
        return this.active;
    }

    @Override
    public double applyXModifier(double cursorDeltaX, double cursorSensitivity, double mouseUpdateTimeDelta, double targetDivisor, double transitionMultiplier) {
        double returnedValue = cursorDeltaX;
        for (MouseModifier modifier : modifiers) {
            returnedValue = modifier.applyXModifier(returnedValue, cursorSensitivity, mouseUpdateTimeDelta, targetDivisor, transitionMultiplier);
        }
        return returnedValue;
    }

    @Override
    public double applyYModifier(double cursorDeltaY, double cursorSensitivity, double mouseUpdateTimeDelta, double targetDivisor, double transitionMultiplier) {
        double returnedValue = cursorDeltaY;
        for (MouseModifier modifier : modifiers) {
            returnedValue = modifier.applyYModifier(returnedValue, cursorSensitivity, mouseUpdateTimeDelta, targetDivisor, transitionMultiplier);
        }
        return returnedValue;
    }

    @Override
    public void tick(boolean active) {
        boolean generalActive = false;
        for (MouseModifier modifier : modifiers) {
            modifier.tick(active);
            if (!generalActive) {
                generalActive = modifier.getActive();
            }
        }
        this.active = generalActive;
    }
}
