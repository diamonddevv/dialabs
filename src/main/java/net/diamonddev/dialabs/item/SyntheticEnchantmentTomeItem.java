package net.diamonddev.dialabs.item;

import net.minecraft.item.Item;
import net.minecraft.item.Wearable;

public class SyntheticEnchantmentTomeItem extends Item implements TranslatedTagSynthesisItem, Wearable {
    private final String key;

    public SyntheticEnchantmentTomeItem(String typeKey, Settings settings) {
        super(settings);
        this.key = typeKey;
    }

    @Override
    public String getSynthesisUiTranslationKey() {
        return "synthesis.dialabs." + key + "_tome";
    }
}
