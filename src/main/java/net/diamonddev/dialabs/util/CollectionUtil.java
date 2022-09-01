package net.diamonddev.dialabs.util;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionUtil {

    public static Collection<Integer> getEachIntegerRange(int origin, int bound) {
        Collection<Integer> intCol = new ArrayList<>();
        for (int i = origin; i < bound; i++) {
            intCol.add(i);
        }
        return intCol;
    }

    public static <T> T getIfPresent(ArrayList<T> array, T object) {
        if (array.contains(object)) {
            return object;
        }
        else return null;
    }
}
