package net.diamonddev.dialabs.manager;

import com.google.common.collect.Maps;
import net.diamonddev.dialabs.mixin.ItemCooldownManagerAccessor;
import net.diamonddev.dialabs.mixin.ItemCooldownManagerEntryInvoker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import java.util.Map;

public class ItemContinuityManager {
    private final Map<Item, ItemContinuityEntryManager> entries = Maps.newHashMap();
    private final PlayerEntity p;

    public ItemContinuityManager(PlayerEntity player) {
        this.p = player;
    }

    public ItemContinuityEntryManager createEntry(Item item, int maxContinuity) {
        return new ItemContinuityEntryManager(item, new Entry(maxContinuity), p);
    }
    public boolean hasEntry(Item item) {
        return entries.containsKey(item);
    }
    public ItemContinuityEntryManager getEntry(Item item) {
        return entries.get(item);
    }

    public static class ItemContinuityEntryManager {
        private final Item item;
        private final ItemContinuityManager.Entry entry;
        private final PlayerEntity p;

        protected ItemContinuityEntryManager(Item item, ItemContinuityManager.Entry entry, PlayerEntity player) {
            this.item = item;
            this.entry = entry;
            this.p = player;
        }

        public void setContinuity(int i) {
            entry.continuity = i;
        }
        public int getContinuity() {
            return entry.continuity;
        }

        public void startCooldown() {
            ItemCooldownManagerAccessor accessedManager = ((ItemCooldownManagerAccessor)p.getItemCooldownManager());
            accessedManager.accessEntries().put(
                    item,
                    ItemCooldownManagerEntryInvoker.invokeInstantiate(accessedManager.accessTick() + entry.maxContinuity, accessedManager.accessTick() - entry.continuity)
            );
        }
    }

    private static class Entry {
        final int maxContinuity;
        private int continuity;

        Entry(int maxContinuity) {
            this.maxContinuity = maxContinuity;
        }
    }
}
