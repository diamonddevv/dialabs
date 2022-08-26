package net.diamonddev.dialabs.item;

import net.diamonddev.dialabs.enchant.SyntheticEnchantment;
import net.diamonddev.dialabs.registry.InitItem;
import net.diamonddev.dialabs.util.ItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

public class SyntheticEnchantmentDisc extends EnchantedBookItem {

    public SyntheticEnchantmentDisc() {
        super(new FabricItemSettings().group(ItemGroups.SYNTHETIC_ENCHANT_GROUP).maxCount(1).rarity(Rarity.RARE));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    public static ItemStack forEnchantment(EnchantmentLevelEntry info) {
        ItemStack itemStack = new ItemStack(InitItem.SYNTHETIC_ENCHANTMENT_DISC);
        addEnchantment(itemStack, info);
        return itemStack;
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (group == ItemGroups.SYNTHETIC_ENCHANT_GROUP || group == ItemGroup.SEARCH) {
            Enchantment e;
            for (Enchantment enchantment : Registry.ENCHANTMENT) {
                e = enchantment;
                if (e instanceof SyntheticEnchantment) {
                    for(int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
                        stacks.add(forEnchantment(new EnchantmentLevelEntry(enchantment, i)));
                    }
                }
            }
        }
    }
}
