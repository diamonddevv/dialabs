package net.diamonddev.dialabs.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class LightningBottleItem extends Item {
    public LightningBottleItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity.isAlive()) {
            BlockPos pos = entity.getBlockPos();
            World world = entity.getWorld();

            LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
            assert lightning != null;
            lightning.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(pos.up()));

            world.spawnEntity(lightning);

            stack.decrement(1);

            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        ItemStack stack = context.getStack();

        if (!world.isAir(pos)) {
            LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
            assert lightning != null;
            lightning.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(pos.up()));

            world.spawnEntity(lightning);

            stack.decrement(1);

            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }


}
