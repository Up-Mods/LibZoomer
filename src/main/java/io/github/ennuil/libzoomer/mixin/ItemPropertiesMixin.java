package io.github.ennuil.libzoomer.mixin;

import io.github.ennuil.libzoomer.impl.SpyglassHelper;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemProperties.class)
public abstract class ItemPropertiesMixin {
	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void addScopingPredicateToModdedSpyglasses(CallbackInfo ci) {
		ItemProperties.registerGeneric(new ResourceLocation("libzoomer", "scoping"), (stack, clientLevel, entity, i) ->
			entity != null
				&& entity.isUsingItem()
				&& entity.getUseItem() == stack
				&& entity.getUseItem().is(SpyglassHelper.SPYGLASSES)
				? 1.0F
				: 0.0F
		);
	}
}
