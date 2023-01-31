package net.diamonddev.dialabs.api.v0.recipe;

import net.minecraft.util.Identifier;

import java.util.ArrayList;

public abstract class DialabsRecipeType {
    public abstract Identifier getId();
    public abstract void addJsonKeys(ArrayList<String> keys);
}
