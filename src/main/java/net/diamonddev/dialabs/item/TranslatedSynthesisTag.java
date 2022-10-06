package net.diamonddev.dialabs.item;

import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public interface TranslatedSynthesisTag {

    Map<Item, TranslatedSynthesisTag> mappedTags = new HashMap<>();
    String getSynthesisUiTranslationKey();

    static void giveItemTag(Item item, TranslatedSynthesisTag tag) {
        mappedTags.put(item, tag);
    }
}
