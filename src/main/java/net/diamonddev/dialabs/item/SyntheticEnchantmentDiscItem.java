package net.diamonddev.dialabs.item;

import net.diamonddev.dialabs.enchant.SyntheticEnchantment;
import net.diamonddev.dialabs.registry.InitItem;
import net.diamonddev.dialabs.util.UtilityHelper;
import net.diamonddev.dialabs.util.EnchantHelper;
import net.diamonddev.dialabs.util.ItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SyntheticEnchantmentDiscItem extends EnchantedBookItem {

    public static Collection<Enchantment> externalEntries = new ArrayList<>();
    public SyntheticEnchantmentDiscItem() {
        super(new FabricItemSettings().group(ItemGroups.SYNTHETIC_ENCHANT_GROUP).maxCount(1).rarity(Rarity.RARE));
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

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (group == ItemGroups.SYNTHETIC_ENCHANT_GROUP || group == ItemGroup.SEARCH) {
            Enchantment e;
            stacks.add(new ItemStack(InitItem.SYNTHETIC_ENCHANTMENT_DISC));

            // Predetermined Synthetic Enchantments (from registry)
            for (Enchantment enchantment : Registry.ENCHANTMENT) {
                e = enchantment;
                if (e instanceof SyntheticEnchantment) {
                    for(int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
                        stacks.add(forEnchantment(new EnchantmentLevelEntry(enchantment, i)));
                    }
                }
            }

            // External Entries
            for (Enchantment enchant : externalEntries) {
                Collection<Integer> levelData = UtilityHelper.getEachIntegerRange(enchant.getMinLevel(), enchant.getMaxLevel());
                for (Integer i : levelData) {
                    stacks.add(forEnchantment(new EnchantmentLevelEntry(enchant, i)));
                }
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
