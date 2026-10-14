package net.awoolanche.applewoodrebarked.compat;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientGuiEvent;
import net.awoolanche.applewoodrebarked.compat.FurnitureCompat;
import net.awoolanche.applewoodrebarked.compat.HearthAndTimberCompat;
import net.awoolanche.applewoodrebarked.util.ModCompat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.satisfy.foundation.client.creative.CreativeSideTabs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public final class ModCompatTabs {
    private static final String HEARTH_AND_TIMBER_COMPAT_TITLE = "creativetab.hearth_and_timber.side.compat";
    private static final String FURNITURE_COMPAT_TITLE = "creativetab.furniture.side.compat";

    private static final Set<ResourceKey<CreativeModeTab>> PATCHED = new HashSet<>();

    private ModCompatTabs() {
    }

    public static void init() {
        ClientGuiEvent.INIT_PRE.register((screen, access) -> {
            if (screen instanceof CreativeModeInventoryScreen) {
                apply();
            }
            return EventResult.pass();
        });
    }

    private static void apply() {
        if (ModCompat.HEARTH_AND_TIMBER) {
            extend(HearthAndTimberCompat.TAB_KEY, HEARTH_AND_TIMBER_COMPAT_TITLE, HearthAndTimberHooks.CONTENTS);
        }
        if (ModCompat.FURNITURE) {
            extend(FurnitureCompat.COMPAT_TAB_KEY, FURNITURE_COMPAT_TITLE, FurnitureHooks.CONTENTS);
        }
    }

    private static void extend(ResourceKey<CreativeModeTab> tabKey, String compatTitleKey, Consumer<CreativeModeTab.Output> contents) {
        if (PATCHED.contains(tabKey)) {
            return;
        }

        CreativeModeTab tab = BuiltInRegistries.CREATIVE_MODE_TAB.getOptional(tabKey).orElse(null);
        if (tab == null) {
            return;
        }

        List<CreativeSideTabs.SideTab> existing = CreativeSideTabs.get(tab);
        if (existing.isEmpty()) {
            return;
        }

        CreativeSideTabs.SideTab[] replaced = new CreativeSideTabs.SideTab[existing.size()];
        boolean found = false;
        for (int i = 0; i < replaced.length; i++) {
            CreativeSideTabs.SideTab sideTab = existing.get(i);
            if (isTitled(sideTab, compatTitleKey)) {
                replaced[i] = new CreativeSideTabs.SideTab(sideTab.title(), sideTab::icon, contents);
                found = true;
            } else {
                replaced[i] = sideTab;
            }
        }

        if (found) {
            CreativeSideTabs.register(tabKey, replaced);
            PATCHED.add(tabKey);
        }
    }

    private static boolean isTitled(CreativeSideTabs.SideTab sideTab, String translationKey) {
        return sideTab.title().getContents() instanceof TranslatableContents translatable
                && translatable.getKey().equals(translationKey);
    }

    private static final class HearthAndTimberHooks {
        static final Consumer<CreativeModeTab.Output> CONTENTS = output -> {
            net.satisfy.hearth_and_timber.core.registry.TabRegistry.acceptCompat(output);
            HearthAndTimberCompat.acceptCompatItems(output);
        };
    }

    private static final class FurnitureHooks {
        static final Consumer<CreativeModeTab.Output> CONTENTS = output -> {
            net.satisfy.furniture.core.registry.TabRegistry.acceptCompat(output);
            FurnitureCompat.acceptCompatItems(output);
        };
    }
}