package io.github.ennuil.libzoomer.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.ennuil.libzoomer.impl.SpyglassHelper;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.util.Identifier;

@Mixin(ModelPredicateProviderRegistry.class)
public abstract class ModelPredicateProviderRegistryMixin {
    @Shadow
    private static UnclampedModelPredicateProvider register(Identifier id, UnclampedModelPredicateProvider provider) {
        return null;
    }

    @Inject(at = @At("TAIL"), method = "<clinit>")
    private static void addScopingPredicateToModdedSpyglasses(CallbackInfo ci) {
        register(new Identifier("scoping"), (stack, clientWorld, entity, i) -> {
            return entity != null && entity.isUsingItem() && entity.getActiveItem() == stack && entity.getActiveItem().isIn(SpyglassHelper.SPYGLASSES) ? 1.0F : 0.0F;
        });
    }
}
