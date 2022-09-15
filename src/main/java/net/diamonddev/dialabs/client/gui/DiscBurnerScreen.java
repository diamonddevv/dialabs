package net.diamonddev.dialabs.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.diamonddev.dialabs.api.Identifier;
import net.diamonddev.dialabs.gui.DiscBurnerScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;

public class DiscBurnerScreen extends HandledScreen<DiscBurnerScreenHandler> {

    private static final Identifier TEXTURE = new Identifier("textures/gui/disc_burner_screen.png");

    public DiscBurnerScreen(DiscBurnerScreenHandler handler, PlayerInventory inventory, Text title) {
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


        drawXpRequirement(matrices);
        drawCrossedOutputArrow(matrices);
    }


    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    public void drawXpRequirement(MatrixStack matrices) {
        int requirement = this.handler.getXpRequirementProperty().get();
        Text text = null;
        if (this.handler.isAllSlotsRequiredFilled()) {
            text = Text.translatable("block.dialabs.disc_burner.ui.xp", requirement);
        }

        int fillColor = this.handler.canTake() ?
                ColorHelper.Argb.getArgb(0, 0, 255, 0) : ColorHelper.Argb.getArgb(0, 255, 0, 0);

        if (text != null) {
            // fill(matrices, x + 26, y + 24, x + 26 + textRenderer.getWidth(text), y + 32, fillColor); hmm
            drawTextWithShadow(matrices, this.textRenderer, text, x + 18, y + 28,
                    fillColor);
        }
    }

    public void drawCrossedOutputArrow(MatrixStack matrices) {
        if (!this.handler.isPossibleCombination() && this.handler.isAllSlotsRequiredFilled()) {
            int i = (this.width - this.backgroundWidth) / 2;
            int j = (this.height - this.backgroundHeight) / 2;
            this.drawTexture(matrices, i + 99, j + 45, this.backgroundWidth, 0, 28, 21);
        }
    }
}
