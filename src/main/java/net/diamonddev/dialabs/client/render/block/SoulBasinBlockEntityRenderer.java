package net.diamonddev.dialabs.client.render.block;

import net.diamonddev.dialabs.block.entity.SoulBasinBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class SoulBasinBlockEntityRenderer implements BlockEntityRenderer<SoulBasinBlockEntity> {

    float scale = 0.375F;

    private final ItemRenderer itemRenderer;

    public SoulBasinBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(SoulBasinBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        light = 15728880;

        if (!entity.hasOutput()) {
            if (entity.alphaStack != null) {
                ItemStack alpha = entity.alphaStack;

                matrices.translate(0.25, 1.25, 0.5);

                if (!alpha.isEmpty()) renderItem(alpha, matrices, light, overlay, vertexConsumers, entity);
            }

            if (entity.betaStack != null) {
                ItemStack beta = entity.betaStack;

                matrices.translate(0.5, 0, 0);

                if (!beta.isEmpty()) renderItem(beta, matrices, light, overlay, vertexConsumers, entity);
            }
        } else {
            ItemStack out = entity.outStack;

            matrices.translate(0.5, 1.35, 0.5);

            if (out != null) renderItem(out, matrices, light, overlay, vertexConsumers, entity);
        }
        matrices.pop();
    }

    private void renderItem(ItemStack itemStack, MatrixStack matrices, int light, int overlay, VertexConsumerProvider vertexConsumers, SoulBasinBlockEntity entity) {
        matrices.push();
        matrices.scale(scale, scale, scale);
        this.itemRenderer.renderItem(itemStack, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers, (int) entity.getPos().asLong());
        matrices.pop();
    }
}
