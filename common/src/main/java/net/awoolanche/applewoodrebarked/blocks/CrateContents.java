package net.awoolanche.applewoodrebarked.blocks;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public enum CrateContents implements StringRepresentable {
    EMPTY("empty", null),
    APPLE("apple", "vinery:apple_bag"),
    CHERRY("cherry", "vinery:cherry_bag"),
    RED_GRAPE("red_grape", "vinery:red_grape_bag"),
    WHITE_GRAPE("white_grape", "vinery:white_grape_bag"),
    TOMATO("tomato", "farm_and_charm:tomato_bag"),
    POTATO("potato", "farm_and_charm:potato_bag"),
    CARROT("carrot", "farm_and_charm:carrot_bag"),
    BEETROOT("beetroot", "farm_and_charm:beetroot_bag"),
    CORN("corn", "farm_and_charm:corn_bag"),
    STRAWBERRY("strawberry", "farm_and_charm:strawberry_bag"),
    ONION("onion", "farm_and_charm:onion_bag"),
    LETTUCE("lettuce", "farm_and_charm:lettuce_bag"),
    OAT("oat", "farm_and_charm:oat_bag"),
    FISH("fish", "lilis_lucky_lures:fish_bag"),
    TEA("tea", "herbalbrews:tea_leaf_crate"),
    TRUFFLE("truffle", "wildernature:truffle_bag");

    private static final Map<ResourceLocation, CrateContents> BY_ITEM = new HashMap<>();
    static {
        for (CrateContents c : values()) {
            if (c.item != null) BY_ITEM.put(c.item, c);
        }
    }

    private final String name;
    @Nullable private final ResourceLocation item;

    CrateContents(String name, @Nullable String item) {
        this.name = name;
        this.item = item == null ? null : ResourceLocation.parse(item);
    }

    @Override
    public String getSerializedName() {
        return name;
    }

    @Nullable
    public ItemStack createBagStack() {
        if (item == null) return null;
        var bag = BuiltInRegistries.ITEM.getOptional(item);
        return bag.map(ItemStack::new).orElse(null);
    }

    @Nullable
    public static CrateContents fromStack(ItemStack stack) {
        if (stack.isEmpty()) return null;
        return BY_ITEM.get(BuiltInRegistries.ITEM.getKey(stack.getItem()));
    }
}