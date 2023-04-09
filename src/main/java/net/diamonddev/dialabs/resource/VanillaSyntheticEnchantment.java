package net.diamonddev.dialabs.resource;

import com.google.gson.annotations.SerializedName;
import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class VanillaSyntheticEnchantment implements CognitionResourceType {

    public static final String
            ENCHANT_KEY = "enchantments";

    @Override
    public Identifier getId() {
        return Dialabs.id("vanilla_synthetic_enchants");
    }

    @Override
    public void addJsonKeys(ArrayList<String> keys) {
        keys.add(
                ENCHANT_KEY
        );
    }

    public static class Serialized {
        @SerializedName(ENCHANT_KEY)
        public ArrayList<String> unmappedSynthetics;

    }

    public static List<Identifier> mapStringArrToIdArr(ArrayList<String> unmapped) {
        return unmapped.stream().map(Identifier::new).toList();
    }
}
