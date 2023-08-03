package net.diamonddev.dialabs.client.gui;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.gui.EnchantmentSynthesisScreenHandler;
import net.diamonddev.dialabs.item.TranslatedSynthesisTag;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.AirBlockItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.Random;

@ClientOnly
public class EnchantmentSynthesisScreen extends HandledScreen<EnchantmentSynthesisScreenHandler> {

    private static final Identifier TEXTURE = Dialabs.id("textures/gui/enchantment_synthesis_screen.png");
    private static final Identifier FONT_ID = new net.minecraft.util.Identifier("minecraft", "alt");
    private static final Style TEXT_STYLE = Style.EMPTY.withFont(FONT_ID);

    public EnchantmentSynthesisScreen(EnchantmentSynthesisScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        // The title was slightly too high
        titleY -= 1;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);
        drawMouseoverTooltip(graphics, mouseX, mouseY);
    }


    @Override
    protected void drawBackground(GuiGraphics graphics, float delta, int mouseX, int mouseY) {

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        graphics.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        drawCanTakeCross(graphics);
        drawEnchantmentText(graphics);

    }

    public static Text getText(TextRenderer renderer, ItemStack stack) {
        Text key;
        if (stack.getItem() instanceof TranslatedSynthesisTag tst) {
            key = Text.translatable(tst.getSynthesisUiTranslationKey());
        } else if (stack.getItem() instanceof BlockItem bi) {
            if (bi.getBlock() instanceof TranslatedSynthesisTag tst) {
                key = Text.translatable(tst.getSynthesisUiTranslationKey());
            } else if (TranslatedSynthesisTag.mappedTags.containsKey(bi)) {
                key = Text.of(TranslatedSynthesisTag.mappedTags.get(bi).getSynthesisUiTranslationKey());
            } else {
                key = Text.translatable(bi.getTranslationKey());
            }
        } else if (TranslatedSynthesisTag.mappedTags.containsKey(stack.getItem())) {
            key = Text.of(TranslatedSynthesisTag.mappedTags.get(stack.getItem()).getSynthesisUiTranslationKey());
        } else if (stack.getItem() instanceof AirBlockItem) {
            key = getRandomEnchantText();
        } else {
            key = Text.translatable(stack.getTranslationKey());
        }
        StringVisitable stringVisitable = renderer.getTextHandler().trimToWidth(key, 80, Style.EMPTY);
        return Text.literal(stringVisitable.getString()).setStyle(TEXT_STYLE);
    }
    public static Text getRandomEnchantText() {
        StringBuilder stringBuilder = new StringBuilder();
        Random r = new Random();
        for (int i = r.nextInt(80); i > 0; i--) {
            stringBuilder.append((char) r.nextInt(65, 90));
        }
        return Text.translatable("synthesis.dialabs.empty", stringBuilder);
    }

    //
    public void drawEnchantmentText(GuiGraphics graphics) {
        for (int i = 0; i < 3; i++) {
            ItemStack stack = this.handler.getInventory().getStack(i + 2);
            graphics.drawShadowedText(this.textRenderer, getText(this.textRenderer, stack), x + 83, y + 15 + i * 19, 0xaaaaaa);
        }
    }

    public void drawCanTakeCross(GuiGraphics graphics) {
        if (this.handler.getCanTakeFromOutSlot()) {
            int drawX = 16, drawY = 9;
            int getX = 177, getY = 1;
            int dims = 32;

            graphics.drawTexture(TEXTURE, x + drawX, y + drawY, getX, getY, dims, dims);
        }
    }
}
