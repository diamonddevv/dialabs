package net.diamonddev.dialabs.item;

import net.minecraft.item.Item;

public class SynthesisTranslatedTagItem extends Item implements TranslatedTagSynthesisItem {
    private final String key;

    public SynthesisTranslatedTagItem(String translationKey, Settings settings) {
        super(settings);
        this.key = translationKey;
    }

    @Override
    public String getSynthesisUiTranslationKey() {
        return key;
    }
}
