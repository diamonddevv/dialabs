package net.diamonddev.dialabs.item;

import net.diamonddev.dialabs.enchant.SyntheticEnchantment;
import net.diamonddev.dialabs.registry.InitItem;
import net.diamonddev.dialabs.util.EnchantHelper;
import net.diamonddev.dialabs.util.Helpers;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SyntheticEnchantmentDiscItem extends EnchantedBookItem {

    public static Collection<Enchantment> externalEntries = new ArrayList<>();
    public SyntheticEnchantmentDiscItem() {
        super(new QuiltItemSettings().maxCount(1).rarity(Rarity.RARE));
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


    public void putSyntheticDiscStacks(FabricItemGroupEntries content, ItemGroup.Visibility vis) {
        content.addStack(new ItemStack(InitItem.SYNTHETIC_ENCHANTMENT_DISC));

        for (Enchantment enchant : Registries.ENCHANTMENT) {
            if (SyntheticEnchantment.validSyntheticEnchantments.contains(enchant)) {
                content.addStack(forEnchantment(new EnchantmentLevelEntry(enchant, SyntheticEnchantment.hashSyntheticEnchantMaxLevel.get(enchant))), vis);
            }
        }
    }

    public void putAllSyntheticDiscStacks(FabricItemGroupEntries content, ItemGroup.Visibility vis) {
        content.addStack(new ItemStack(InitItem.SYNTHETIC_ENCHANTMENT_DISC));

        for (Enchantment enchant : Registries.ENCHANTMENT) {
            if (SyntheticEnchantment.validSyntheticEnchantments.contains(enchant)) {
                Helpers.getEachIntegerRange(enchant.getMinLevel(), SyntheticEnchantment.hashSyntheticEnchantMaxLevel.get(enchant)).forEach((i) -> {
                    content.addStack(forEnchantment(new EnchantmentLevelEntry(enchant, i)), vis);
                });
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

    public static ItemStack getStackFromEnchantmentLevelEntry(EnchantmentLevelEntry enchantment) {
        return EnchantHelper.storeEnchantment(new ItemStack(InitItem.SYNTHETIC_ENCHANTMENT_DISC), enchantment);
    }
}
