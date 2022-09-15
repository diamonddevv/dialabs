package net.diamonddev.dialabs.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.diamonddev.dialabs.api.Identifier;
import net.diamonddev.dialabs.gui.EnchantmentSynthesisScreenHandler;
import net.diamonddev.dialabs.item.synthesis.SyntheticEnchantmentIngredientItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.AirBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class EnchantmentSynthesisScreen extends HandledScreen<EnchantmentSynthesisScreenHandler> {

    private static final Identifier TEXTURE = new Identifier("textures/gui/enchantment_synthesis_screen.png");
    private static final net.minecraft.util.Identifier FONT_ID = new net.minecraft.util.Identifier("minecraft", "alt");

    private static final Style TEXT_STYLE = Style.EMPTY.withFont(FONT_ID);
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

        drawEnchantmentText(matrices);
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

        // The title was slightly too high
        titleY = titleY - 2;
    }
    public static Text getText(TextRenderer renderer, ItemStack stack) {
        Text key;
        if (stack.getItem() instanceof SyntheticEnchantmentIngredientItem seii) {
            key = Text.translatable(seii.getSynthesisUiTranslationKey());
        } else if (stack.getItem() instanceof AirBlockItem) {
            StringBuilder stringBuilder = new StringBuilder();
            Random r = new Random();
            for (int i = r.nextInt(80); i > 0; i--) {
                stringBuilder.append((char) r.nextInt(65, 90));
            }
            key = Text.translatable("synthesis.dialabs.empty", stringBuilder);
        } else {
            key = Text.translatable(stack.getTranslationKey());
        }
        StringVisitable stringVisitable = renderer.getTextHandler().trimToWidth(key, 80, Style.EMPTY);
        return Text.literal(stringVisitable.getString()).setStyle(TEXT_STYLE);
    }


    public void drawEnchantmentText(MatrixStack matrices) {
        for (int i = 0; i < 3; i++) {
            ItemStack stack = this.handler.getInventory().getStack(i + 2);
            drawTextWithShadow(matrices, this.textRenderer, getText(textRenderer, stack), x + 83, y + 15 + i * 19,
                    ColorHelper.Argb.getArgb(0, 170, 170, 170));
        }
    }

}
