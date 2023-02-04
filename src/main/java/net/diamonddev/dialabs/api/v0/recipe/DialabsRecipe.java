package net.diamonddev.dialabs.api.v0.recipe;

import com.google.gson.JsonElement;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class DialabsRecipe {

    private final HashMap<String, JsonElement> objHash;
    private final DialabsRecipeType type;
    private final Identifier id;

    public DialabsRecipe(DialabsRecipeType type, Identifier identifier) {
        this.type = type;
        this.id = identifier;
        this.objHash = new HashMap<>();
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public HashMap<String, JsonElement> getHash() {
        return objHash;
    }

    public JsonElement getObject(String jsonKey) {
        return objHash.get(jsonKey);
    }

    public String getString(String jsonKey) {
        return getObject(jsonKey).getAsString();
    }
    public int getInt(String jsonKey) {
        return getObject(jsonKey).getAsInt();
    }
    public boolean getBool(String jsonKey) {
        return getObject(jsonKey).getAsBoolean();
    }
    public Identifier getIdentifier(String jsonKey) {
        return new Identifier(getString(jsonKey));
    }
}
