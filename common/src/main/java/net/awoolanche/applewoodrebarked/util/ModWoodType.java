package net.awoolanche.applewoodrebarked.util;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.resources.ResourceLocation;

public class ModWoodType {
    public static final WoodType APPLE = WoodType.register(new WoodType(ResourceLocation.fromNamespaceAndPath("applewoodrebarked", "apple").toString(), BlockSetType.OAK));
}