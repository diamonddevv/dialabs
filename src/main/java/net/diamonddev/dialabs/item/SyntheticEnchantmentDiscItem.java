package net.diamonddev.dialabs.item;

import net.diamonddev.dialabs.enchant.SyntheticEnchantment;
import net.diamonddev.dialabs.registry.InitItem;
import net.diamonddev.dialabs.util.EnchantHelper;
import net.diamonddev.dialabs.util.Helpers;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SyntheticEnchantmentDiscItem extends EnchantedBookItem {

    public static Collection<Enchantment> externalEntries = new ArrayList<>();
    public SyntheticEnchantmentDiscItem() {
        super(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return EnchantHelper.hasAnySyntheticEnchantmentStored(stack);
    }

    public static ItemStack forEnchantment(EnchantmentLevelEntry info) {
        ItemStack itemStack = new ItemStack(InitItem.SYNTHETIC_ENCHANTMENT_DISC);
        addEnchantment(itemStack, info);
        return itemStack;
    }


    public void putSyntheticDiscStacks(FabricItemGroupEntries content) {
        Enchantment e;
        content.add(new ItemStack(InitItem.SYNTHETIC_ENCHANTMENT_DISC));

        // Predetermined Synthetic Enchantments (from registry)
        for (Enchantment enchantment : Registries.ENCHANTMENT) {
            e = enchantment;
            if (e instanceof SyntheticEnchantment) {
                for(int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
                    content.add(forEnchantment(new EnchantmentLevelEntry(enchantment, i)));
                }
            }
        }

        // External Entries
        for (Enchantment enchant : externalEntries) {
            Collection<Integer> levelData = Helpers.getEachIntegerRange(enchant.getMinLevel(), enchant.getMaxLevel());
            for (Integer i : levelData) {
                content.add(forEnchantment(new EnchantmentLevelEntry(enchant, i)));
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (!EnchantHelper.hasAnySyntheticEnchantmentStored(stack)) {
            tooltip.add(Text.translatable("text.dialabs.empty_synthetic_enchantment_disc"));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
