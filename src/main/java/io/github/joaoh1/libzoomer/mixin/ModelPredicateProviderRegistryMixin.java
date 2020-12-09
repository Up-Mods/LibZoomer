package io.github.joaoh1.libzoomer.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.joaoh1.libzoomer.api.SpyglassHelper;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

@Mixin(ModelPredicateProviderRegistry.class)
public class ModelPredicateProviderRegistryMixin {
    @Shadow
    private static ModelPredicateProvider register(Identifier id, ModelPredicateProvider provider) {
        return null;
    }

    @Inject(at = @At("TAIL"), method = "<clinit>")
    private static void addScopingPredicateToModdedSpyglasses(CallbackInfo ci) {
        register(new Identifier("scoping"), (itemStack, clientWorld, livingEntity, i) -> {
            return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack && livingEntity.getActiveItem().isIn(SpyglassHelper.SPYGLASSES) ? 1.0F : 0.0F;
        });
    }
}
