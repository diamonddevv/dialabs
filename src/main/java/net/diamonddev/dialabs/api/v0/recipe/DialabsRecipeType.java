package net.diamonddev.dialabs.api.v0.recipe;

import net.minecraft.util.Identifier;

import java.util.ArrayList;

public interface DialabsRecipeType {
    Identifier getId();
    void addJsonKeys(ArrayList<String> keys);
}
