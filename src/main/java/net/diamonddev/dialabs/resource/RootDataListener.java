package net.diamonddev.dialabs.resource;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.enchant.SyntheticEnchantment;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataResource;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class RootDataListener extends CognitionDataListener {
    public RootDataListener() {
        super("Dialabs Root Data Loader", Dialabs.id("root"), "", ResourceType.SERVER_DATA);
    }

    public static ArrayList<Identifier> CACHED_SYNTHETICS = new ArrayList<>();


    @Override
    public void onReloadForEachResource(CognitionDataResource resource, Identifier path) {
        // Clear Caches
        CACHED_SYNTHETICS.clear();

        CognitionResourceType type = resource.getType();
        if (type instanceof VanillaSyntheticEnchantment) {
            VanillaSyntheticEnchantment.Serialized serialized = resource.getAsClass(VanillaSyntheticEnchantment.Serialized.class);
            CACHED_SYNTHETICS.addAll(VanillaSyntheticEnchantment.mapStringArrToIdArr(serialized.unmappedSynthetics));
        }
    }

    @Override
    public void onFinishReload() {
        CACHED_SYNTHETICS.forEach(identifier -> {
            Enchantment enchantment = Registries.ENCHANTMENT.get(identifier);
            SyntheticEnchantment.validSyntheticEnchantments.add(enchantment);
        });
    }
}
