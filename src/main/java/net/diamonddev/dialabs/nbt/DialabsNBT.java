package net.diamonddev.dialabs.nbt;


import net.minecraft.item.ItemStack;

public class DialabsNBT {

    public static final NBTIntComponent MULTICLIP_ARROWS = new NBTIntComponent("multiclipArrowCount");

    public static class MulticlipProjectileManager {
        public static int getProjectiles(ItemStack stack) {
            return MULTICLIP_ARROWS.read(stack.getOrCreateNbt());
        }

        public static void setProjectiles(ItemStack stack, int count) {
            MULTICLIP_ARROWS.write(stack.getOrCreateNbt(), count);
        }

        public static void decrementProjectileCount(ItemStack stack) {
            setProjectiles(stack, getProjectiles(stack) - 1);
        }
    }
}
