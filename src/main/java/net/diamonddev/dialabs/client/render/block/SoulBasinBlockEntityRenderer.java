package net.diamonddev.dialabs.client.render.block;

import net.diamonddev.dialabs.block.HorizontalRotationBlockWithEntity;
import net.diamonddev.dialabs.block.entity.SoulBasinBlockEntity;
import net.diamonddev.dialabs.util.DDVMathHelper;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

public class SoulBasinBlockEntityRenderer implements BlockEntityRenderer<SoulBasinBlockEntity> {

    float scale = 0.5F;

    private final ItemRenderer itemRenderer;

    public SoulBasinBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(SoulBasinBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        light = 15728880;
        Direction direction = entity.getCachedState().get(HorizontalRotationBlockWithEntity.FACING);

        if (!entity.hasOutput()) {
            if (entity.alphaStack != null) {
                matrices.push();

                ItemStack alpha = entity.alphaStack;
                translate(matrices, true, direction);
                if (!alpha.isEmpty()) renderItem(alpha, matrices, light, overlay, vertexConsumers, entity);

                matrices.pop();
            }

            if (entity.betaStack != null) {
                matrices.push();

                ItemStack beta = entity.betaStack;
                translate(matrices, false, direction);
                if (!beta.isEmpty()) renderItem(beta, matrices, light, overlay, vertexConsumers, entity);

                matrices.pop();
            }
        } else {
            matrices.push();
            ItemStack out = entity.outStack;

            matrices.translate(0.5, 1.15, 0.5);

            if (out != null) renderItem(out, matrices, light, overlay, vertexConsumers, entity);
            matrices.pop();
        }
    }

    private void renderItem(ItemStack itemStack, MatrixStack matrices, int light, int overlay, VertexConsumerProvider vertexConsumers, SoulBasinBlockEntity entity) {
        matrices.scale(scale, scale, scale);
        this.itemRenderer.renderItem(itemStack, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers, (int) entity.getPos().asLong());
    }

    private void translate(MatrixStack matrices, boolean alpha, Direction direction) {

        if (alpha)
            matrices.translate(.25, .75, .5);
        else
            matrices.translate(.75, .75, .5);

        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(DDVMathHelper.degToRad(180))); // Flip completely because it was backwards for some odd reason
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(DDVMathHelper.directionToRad(direction))); // todo: make it do the thing
    }
}
