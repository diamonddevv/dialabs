package net.diamonddev.dialabs.recipe.objects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.diamonddev.dialabs.recipe.SynthesisRecipe;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ChancedEnchantment {

    /**
     * Note this uses a null enchantment value, so it is not recommended to use this.
     * Enchant: null
     * Level: 0
     * Chance: 0%
     */
    public static final ChancedEnchantment EMPTY = new ChancedEnchantment(null, 0, 0.0f);

    private static final String ENCHANT_KEY = "enchantment";
    private static final String LEVEL_KEY = "level";
    private static final String CHANCE_KEY = "chance";

    private final Enchantment enchantment;
    private final int level;
    private final float chance;

    /**
     Example ChancedEnchantment Json:

     <pre>
     "ChancedEnchantmentObjecy": {
        "enchantment": "minecraft:sharpness"
        "level": 2
        "chance": 0.8
     }
     </pre>

     This is a ChancedRecipe for a 80% chance of Sharpness 2.

     WIP

     @see Enchantment
     @see SynthesisRecipe
     **/
    public ChancedEnchantment(Enchantment e, int level, float chance) {
        this.enchantment = e;
        this.level = level;
        this.chance = chance;
    }

    public ChancedEnchantment fromJson(@Nullable JsonElement json) {
        String enchantId;
        Enchantment e;
        int lvl;
        float c;

        if (json != null && !json.isJsonNull()) {
            if (json.isJsonObject()) {
                JsonObject obj = json.getAsJsonObject();
                if (obj.has(ENCHANT_KEY)) {
                    if (obj.has(CHANCE_KEY)) {
                        enchantId = obj.get(ENCHANT_KEY).getAsString();
                        lvl = obj.has(LEVEL_KEY) ? obj.get(LEVEL_KEY).getAsInt() : 1;
                        if (lvl == 0) lvl = 1;
                        c = obj.get(CHANCE_KEY).getAsFloat();
                        Optional<Enchantment> enchant = Registry.ENCHANTMENT.getOrEmpty(new Identifier(enchantId));
                        if (enchant.isPresent()) {
                            if (c < 1.0f) {
                                e = enchant.get();
                                return new ChancedEnchantment(e, lvl, c);
                            } else {
                                throw new JsonSyntaxException("The provided chance was greater than 100% (1.0); Please use a decimal chance. (Divide percentage by 100)");
                            }
                        } else {
                            throw new JsonSyntaxException("The provided Enchantment reference was invalid!");
                        }
                    } else {
                        throw new JsonSyntaxException("A Float Chance was expected, but was not found!");
                    }
                } else {
                    throw new JsonSyntaxException("An Enchantment ID was expected, but was not found!");
                }
            } else {
                throw new JsonSyntaxException("JSON was not null, but an Object was not found!");
            }
        }

        return EMPTY;
    }

    public static ChancedEnchantment fromPacket(PacketByteBuf buf) {
       Enchantment e = buf.readRegistryValue(Registry.ENCHANTMENT);
       int i = buf.readInt();
       float f = buf.readFloat();
       return new ChancedEnchantment(e, i, f);
    }

    public void toPacket(PacketByteBuf buf) {
        buf.writeRegistryValue(Registry.ENCHANTMENT, this.enchantment);
        buf.writeInt(this.level);
        buf.writeFloat(this.chance);
    }
}
