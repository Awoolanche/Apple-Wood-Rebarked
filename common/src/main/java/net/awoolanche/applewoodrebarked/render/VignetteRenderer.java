package net.awoolanche.applewoodrebarked.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.awoolanche.applewoodrebarked.AppleWoodRebarked;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class VignetteRenderer {

    private static final ResourceLocation FEROCITY_VIGNETTE =
            ResourceLocation.fromNamespaceAndPath(AppleWoodRebarked.MOD_ID, "textures/misc/ferocity_vignette.png");

    public static void renderRadialVignette(GuiGraphics guiGraphics, int screenWidth, int screenHeight,
                                            float r, float g, float b, float intensity) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(r, g, b, intensity);

        guiGraphics.blit(FEROCITY_VIGNETTE, 0, 0, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }
}