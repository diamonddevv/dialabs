package net.diamonddev.dialabs.util;

import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.RegistryEntry;

import java.util.ArrayList;
import java.util.Collection;

public class Helpers {
    public static Collection<Integer> getEachIntegerRange(int origin, int bound) {
        Collection<Integer> intCol = new ArrayList<>();
        for (int i = origin; i <= bound; i++) {
            intCol.add(i);
        }
        return intCol;
    }

    public static <T> boolean isInTag(TagKey<T> tag, T object) {
        RegistryEntry<T> entry = RegistryEntry.of(object);
        return entry.isIn(tag);
    }
}