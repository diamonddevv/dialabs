package net.diamonddev.dialabs.item.synthesis;

import net.minecraft.item.Item;

public class AspectionTomeItem extends Item implements SyntheticEnchantmentIngredientItem {
    public AspectionTomeItem(Settings settings) {
        super(settings);
    }

    @Override
    public String getSynthesisUiTranslationKey() {
        return "synthesis.dialabs.aspection_tome";
    }
}
