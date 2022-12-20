package net.diamonddev.dialabs.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.diamonddev.dialabs.DiaLabs;
import net.diamonddev.dialabs.gui.DiscBurnerScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

public class DiscBurnerScreen extends HandledScreen<DiscBurnerScreenHandler> {

    private static final Identifier TEXTURE = DiaLabs.id.build("textures/gui/disc_burner_screen.png");

    public DiscBurnerScreen(DiscBurnerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
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


        boolean affordable = this.handler.canTake();
        int fillColor = affordable ?
                ColorHelper.Argb.getArgb(0, 0, 255, 0) : ColorHelper.Argb.getArgb(0, 255, 0, 0);

        if (text != null && !this.handler.forceFail) {
            drawXpOrb(matrices, affordable);
            this.textRenderer.drawWithShadow(matrices, text, x + 121, y + 71, fillColor);
        }
    }

    public void drawCrossedOutputArrow(MatrixStack matrices) {
        if ((!this.handler.isPossibleCombination() && this.handler.isAllSlotsRequiredFilled()) || this.handler.forceFail) {
            int i = (this.width - this.backgroundWidth) / 2;
            int j = (this.height - this.backgroundHeight) / 2;
            this.drawTexture(matrices, i + 99, j + 45, this.backgroundWidth, 0, 28, 21);
        }
    }

    public void drawXpOrb(MatrixStack matrices, boolean canAfford) {
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        int uvY = canAfford ? 21 : 37;
        this.drawTexture(matrices, x + 105, y + 65, this.backgroundWidth, uvY, 16, 16);
    }
}
