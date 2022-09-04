package net.diamonddev.dialabs.util;

import net.diamonddev.dialabs.enchant.SyntheticEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Map;

import static net.minecraft.item.EnchantedBookItem.STORED_ENCHANTMENTS_KEY;

public class EnchantHelper {

    public static boolean hasEnchantment(Enchantment enchantment, ItemStack stack) {
        return EnchantmentHelper.getLevel(enchantment, stack) > 0;
    }

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
            if (SyntheticEnchantment.validSyntheticEnchantments.contains(e)) {
                if (hasEnchantmentStored(stack, e)) {
                    bl = true;
                    break;
                }
            }
        }
        return bl;
    }

    public static void storeEnchantment(ItemStack stack, EnchantmentLevelEntry enchantmentLevelEntry) {
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
    }

    public static void storeAllEnchantments(ItemStack stack, Map<Enchantment, Integer> mappedEnchantsToAdd) {
        mappedEnchantsToAdd.forEach((enchantment, integer) -> {
            if (hasEnchantmentStored(stack, enchantment)) {
                if (integer >= getEnchantmentLevel(stack, enchantment)) {
                    upgradeStoredEnchantment(stack, enchantment);
                }
            } else {
                storeEnchantment(stack, new EnchantmentLevelEntry(enchantment, integer));
            }
        });
    }

    public static void addAllEnchantments(ItemStack stack, Map<Enchantment, Integer> mappedEnchantsToAdd) {
        mappedEnchantsToAdd.forEach((enchantment, integer) -> {
            if (hasEnchantment(enchantment, stack)) {
                if (integer >= getEnchantmentLevel(stack, enchantment)) {
                    upgradeExistingEnchantment(stack, enchantment);
                }
            } else {
                stack.addEnchantment(enchantment, integer);
            }
        });
    }

    public static NbtList getStoredEnchantments(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        return nbt != null ? nbt.getList(STORED_ENCHANTMENTS_KEY, 10) : new NbtList();
    }

    public static Map<Enchantment, Integer> getMappedStoredEnchantments(ItemStack stack) {
        return EnchantmentHelper.fromNbt(getStoredEnchantments(stack));
    }

    public static void upgradeStoredEnchantment(ItemStack stack, Enchantment enchantment) {
        Map<Enchantment, Integer> existingMap = getMappedStoredEnchantments(stack);
        int existingLevel = existingMap.get(enchantment);
        if (existingLevel >= enchantment.getMaxLevel()) {
            existingMap.put(enchantment, existingLevel + 1);
        }
    }

    public static void upgradeExistingEnchantment(ItemStack stack, Enchantment enchantment) {
        Map<Enchantment, Integer> existingMap = EnchantmentHelper.get(stack);
        int existingLevel = existingMap.get(enchantment);
        if (existingLevel >= enchantment.getMaxLevel()) {
            existingMap.put(enchantment, existingLevel + 1);
        }
    }

    public static int getEnchantmentLevel(ItemStack stack, Enchantment enchantment) {
        return EnchantmentHelper.getLevel(enchantment, stack);
    }

    public static int getXpCostForAddingEnchants(ItemStack stack, Map<Enchantment, Integer> mappedEnchantsToAdd) {
        float cost = 0.0f;
        if (stack.getItem() instanceof ToolItem toolItem) {
            cost = cost + toolItem.getEnchantability() * 0.75f;
        }
        for (Enchantment e : mappedEnchantsToAdd.keySet()) {
            int thisLevel = mappedEnchantsToAdd.get(e);
            float f = 0;

            switch (e.getRarity()) {
                case COMMON -> f = 1.25f;
                case UNCOMMON -> f = 1.5f;
                case RARE -> f = 1.75f;
                case VERY_RARE -> f = 2.0f;
            }
            f = f * thisLevel;
            cost = cost + f;
        }
        return (int) Math.floor(cost);
    }

    public static boolean allCompatible(Map<Enchantment, Integer> map1, Map<Enchantment, Integer> map2) {
        boolean bl = true;
        for (Enchantment e : map1.keySet()) {
            for (Enchantment e2 : map2.keySet()) {
                bl = e.canCombine(e2);
                if (!bl) {
                    break;
                }
            }
            if (!bl) {
                break;
            }
        }
        return bl;
    }

    public static boolean allAcceptable(Map<Enchantment, Integer> map, ItemStack stack) {
        boolean bl = true;
        for (Enchantment e : map.keySet()) {
            bl = e.isAcceptableItem(stack);
            if (!bl) {
                break;
            }
        }
        return bl;
    }
}
