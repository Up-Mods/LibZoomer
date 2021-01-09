package io.github.joaoh1.libzoomer.api.modifiers;

import io.github.joaoh1.libzoomer.api.MouseModifier;
import net.minecraft.util.Identifier;

//A sin was probably committed by using a lot of for each loops
public class ContainingMouseModifier implements MouseModifier {
    private Identifier transitionId = new Identifier("libzoomer:modifier_container");
    private boolean active;
    private MouseModifier[] modifiers;

    public ContainingMouseModifier(boolean active, MouseModifier[] modifiers) {
        this.active = active;
        this.modifiers = modifiers;
    }

    public ContainingMouseModifier(boolean active) {
        this.active = active;
        this.modifiers = new MouseModifier[]{};
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
    public double applyXModifier(double cursorXDelta, double o, double mouseUpdateDelta, double targetDivisor, double transitionMultiplier) {
        double returnedValue = o;
        for (MouseModifier modifier : modifiers) {
            returnedValue = modifier.applyXModifier(cursorXDelta, o, mouseUpdateDelta, targetDivisor, transitionMultiplier);
        }
        return returnedValue;
    }

    @Override
    public double applyYModifier(double cursorYDelta, double p, double mouseUpdateDelta, double targetDivisor, double transitionMultiplier) {
        double returnedValue = p;
        for (MouseModifier modifier : modifiers) {
            returnedValue = modifier.applyYModifier(cursorYDelta, p, mouseUpdateDelta, targetDivisor, transitionMultiplier);
        }
        return returnedValue;
    }

    @Override
    public void tick(boolean active, boolean cinematicCameraEnabled) {
        for (MouseModifier modifier : modifiers) {
            modifier.tick(active, cinematicCameraEnabled);
        }
    }
}
