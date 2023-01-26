package net.diamonddev.dialabs.manager;

import com.google.common.collect.Maps;
import net.minecraft.item.Item;

import java.util.Map;

public class ItemContinuityManager {
    private final Map<Item, ItemContinuityManager.Entry> entries = Maps.newHashMap();

    public ItemContinuityManager() {
    }


    public void tick() {
        entries.forEach((item, entry) -> {
            //
        });
    }

    public ItemContinuityEntryManager createEntry(Item item, int maxContinuity) {
        return new ItemContinuityEntryManager(item, new Entry(maxContinuity));
    }


    public static class ItemContinuityEntryManager {
        private final Item item;
        private final ItemContinuityManager.Entry entry;

        protected ItemContinuityEntryManager(Item item, ItemContinuityManager.Entry entry) {
            this.item = item;
            this.entry = entry;
        }
    }

    private static class Entry {
        final int maxContinuity;
        private int continuity;

        Entry(int maxContinuity) {
            this.maxContinuity = maxContinuity;
        }

        public int getContinuity() {
            return continuity;
        }
        public void setContinuity(int continuity) {
            this.continuity = continuity;
        }
    }
}
