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
        if (!entity.hasOutput()) {
            if (entity.stacks.size() >= 1) {
                ItemStack alpha = entity.stacks.get(SoulBasinBlockEntity.ALPHA_INDEX);
                if (alpha != null) renderItem(alpha, matrices, light, overlay, vertexConsumers, entity);
            }

            if (entity.stacks.size() >= 2) {
                ItemStack beta = entity.stacks.get(SoulBasinBlockEntity.BETA_INDEX);
                if (beta != null) renderItem(beta, matrices, light, overlay, vertexConsumers, entity);
            }
        } else {
            ItemStack out = entity.outStack;
            if (out != null) renderItem(out, matrices, light, overlay, vertexConsumers, entity);
        }
    }

    private void renderItem(ItemStack itemStack, MatrixStack matrices, int light, int overlay, VertexConsumerProvider vertexConsumers, SoulBasinBlockEntity entity) {
        matrices.push();
        matrices.scale(scale, scale, scale);
        this.itemRenderer.renderItem(itemStack, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers, (int) entity.getPos().asLong());
        matrices.pop();
    }
}
