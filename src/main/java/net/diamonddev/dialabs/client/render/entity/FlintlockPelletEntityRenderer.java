package net.diamonddev.dialabs.client.render.entity;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.client.registry.InitEntityLayer;
import net.diamonddev.dialabs.client.render.model.FlintlockPelletEntityModel;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class FlintlockPelletEntityRenderer<T extends Entity> extends EntityRenderer<T> {

    private static final Identifier FLINTLOCK_PELLET_TEXTURE = Dialabs.id.build("textures/entity/flintlock_pellet.png");

    private final FlintlockPelletEntityModel model;

    public FlintlockPelletEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new FlintlockPelletEntityModel(ctx.getPart(InitEntityLayer.FLINTLOCK_PELLET));
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        this.model.render(matrices, vertexConsumers.getBuffer(this.model.getLayer(this.getTexture(entity))), light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
    }

    @Override
    public Identifier getTexture(Entity entity) {
        return FLINTLOCK_PELLET_TEXTURE;
    }
}
