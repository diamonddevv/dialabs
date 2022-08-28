package net.diamonddev.dialabs.util;

import net.diamonddev.dialabs.enchant.SyntheticEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

import java.util.Map;

import static net.minecraft.item.EnchantedBookItem.STORED_ENCHANTMENTS_KEY;

public class EnchantHelper {

    public static boolean hasEnchantmentStored(ItemStack stack, Enchantment enchantment) {
        boolean bl = false;
        if (stack.getItem() instanceof EnchantedBookItem) {
            NbtList list = EnchantedBookItem.getEnchantmentNbt(stack);
            Map<Enchantment, Integer> mappedStoredEnchants = EnchantmentHelper.fromNbt(list);

            for (Enchantment e : mappedStoredEnchants.keySet()) {
                if (enchantment == e) {
                    bl = true;
                    break;
                }
            }
        }
        return bl;
    }

    public static boolean hasAnySyntheticEnchantmentStored(ItemStack stack) {
        boolean bl = false;
        for (Enchantment e : Registry.ENCHANTMENT) {
            if (e instanceof SyntheticEnchantment) {
                if (hasEnchantmentStored(stack, e)) {
                    bl = true;
                    break;
                }
            }
        }
        return bl;
    }

    public static ItemStack storeEnchantment(ItemStack stack, EnchantmentLevelEntry enchantmentLevelEntry) {
        NbtList nbtList = getStoredEnchantments(stack);
        boolean bl = true;
        Identifier identifier = EnchantmentHelper.getEnchantmentId(enchantmentLevelEntry.enchantment);

        for(int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            Identifier identifier2 = EnchantmentHelper.getIdFromNbt(nbtCompound);
            if (identifier2 != null && identifier2.equals(identifier)) {
                if (EnchantmentHelper.getLevelFromNbt(nbtCompound) < enchantmentLevelEntry.level) {
                    EnchantmentHelper.writeLevelToNbt(nbtCompound, enchantmentLevelEntry.level);
                }

                bl = false;
                break;
            }
        }

        if (bl) {
            nbtList.add(EnchantmentHelper.createNbt(identifier, enchantmentLevelEntry.level));
        }

        stack.getOrCreateNbt().put("StoredEnchantments", nbtList);
        return stack;
    }

    public static NbtList getStoredEnchantments(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        return nbt != null ? nbt.getList(STORED_ENCHANTMENTS_KEY, 10) : new NbtList();
    }
}
