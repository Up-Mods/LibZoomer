package io.github.ennuil.libzoomer.impl;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class SpyglassHelper {
    public static final Tag<Item> SPYGLASSES = TagFactory.ITEM.create(new Identifier("libzoomer", "spyglasses"));
}
