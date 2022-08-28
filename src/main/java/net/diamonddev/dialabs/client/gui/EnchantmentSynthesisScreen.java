package net.diamonddev.dialabs.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.diamonddev.dialabs.api.Identifier;
import net.diamonddev.dialabs.gui.EnchantmentSynthesisScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class EnchantmentSynthesisScreen extends HandledScreen<EnchantmentSynthesisScreenHandler> {

    private static final Identifier TEXTURE = new Identifier("textures/gui/enchantment_synthesis_screen.png");
    private static final net.minecraft.util.Identifier FONT_ID = new net.minecraft.util.Identifier("minecraft", "alt");

    private static Style TEXT_STYLE = Style.EMPTY.withFont(FONT_ID);

    public EnchantmentSynthesisScreen(EnchantmentSynthesisScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        for (int i = 0; i < 3; i++) {
            ItemStack stack = this.handler.getInventory().getStack(i + 2);
            drawTextWithShadow(matrices, this.textRenderer, generateText(this.textRenderer, 86, stack), x + 80, y + 15 + i * 19,
                    ColorHelper.Argb.getArgb(0,170, 170, 170));
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
    }

    public Text generateText(TextRenderer textRenderer, int width, ItemStack stackInSlot) {
        return (Text) textRenderer.getTextHandler().trimToWidth(
                Text.translatable(
                        stackInSlot.getTranslationKey()
                ).fillStyle(TEXT_STYLE), width, Style.EMPTY);
    }
}
