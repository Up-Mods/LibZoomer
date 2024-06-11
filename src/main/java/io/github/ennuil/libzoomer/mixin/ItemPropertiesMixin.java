package io.github.ennuil.libzoomer.mixin;

import io.github.ennuil.libzoomer.impl.ModUtils;
import io.github.ennuil.libzoomer.impl.SpyglassHelper;
import net.fabricmc.fabric.api.tag.client.v1.ClientTags;
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
		ItemProperties.registerGeneric(ModUtils.id("scoping"), (stack, clientLevel, entity, i) ->
			entity != null
				&& entity.isUsingItem()
				&& entity.getUseItem() == stack
				&& ClientTags.isInWithLocalFallback(SpyglassHelper.SPYGLASSES, entity.getUseItem().getItem())
				? 1.0F
				: 0.0F
		);
	}
}
