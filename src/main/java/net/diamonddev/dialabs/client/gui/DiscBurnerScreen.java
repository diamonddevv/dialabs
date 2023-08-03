package net.diamonddev.dialabs.client.gui;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.gui.DiscBurnerScreenHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class DiscBurnerScreen extends HandledScreen<DiscBurnerScreenHandler> {

    private static final Identifier TEXTURE = Dialabs.id("textures/gui/disc_burner_screen.png");

    public DiscBurnerScreen(DiscBurnerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
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


        drawXpRequirement(graphics);
        drawCrossedOutputArrow(graphics);
    }

    public void drawXpRequirement(GuiGraphics graphics) {
        int requirement = this.handler.getXpRequirementProperty().get();
        Text text = null;
        if (this.handler.isAllSlotsRequiredFilled()) {
            text = Text.translatable("block.dialabs.disc_burner.ui.xp", requirement);
        }


        boolean affordable = this.handler.canTake();
        int fillColor = affordable ? 0x00ff00 : 0xff0000; // rgb format - green (0,255,0) : red (255,0,0)

        if (text != null && !this.handler.forceFail) {
            drawXpOrb(graphics, affordable);
            graphics.drawShadowedText(this.textRenderer, text, x, y, fillColor);
        }
    }

    public void drawCrossedOutputArrow(GuiGraphics graphics) {
        if ((!this.handler.isPossibleCombination() && this.handler.isAllSlotsRequiredFilled()) || this.handler.forceFail) {
            int i = (this.width - this.backgroundWidth) / 2;
            int j = (this.height - this.backgroundHeight) / 2;
            graphics.drawTexture(TEXTURE, i + 99, j + 45, this.backgroundWidth, 0, 28, 21);
        }
    }

    public void drawXpOrb(GuiGraphics graphics, boolean canAfford) {
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        int uvY = canAfford ? 21 : 37;
        graphics.drawTexture(TEXTURE, x + 105, y + 65, this.backgroundWidth, uvY, 16, 16);
    }
}
