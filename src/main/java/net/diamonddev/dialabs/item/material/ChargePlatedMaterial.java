package net.diamonddev.dialabs.item.material;

import net.diamonddev.dialabs.init.InitItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class ChargePlatedMaterial implements ToolMaterial {

    public static final ChargePlatedMaterial INSTANCE = new ChargePlatedMaterial();

    @Override
    public int getDurability() {
        return 640;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 12.0F;
    }

    @Override
    public float getAttackDamage() {
        return 2.0F;
    }

    @Override
    public int getMiningLevel() {
        return 3;
    }

    @Override
    public int getEnchantability() {
        return 17;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(InitItem.STATICITE_INGOT);
    }
}
