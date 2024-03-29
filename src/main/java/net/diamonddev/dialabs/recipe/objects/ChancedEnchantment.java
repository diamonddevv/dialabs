package net.diamonddev.dialabs.recipe.objects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.diamonddev.dialabs.recipe.SynthesisRecipe;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
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
     "ChancedEnchantmentObject": {
        "enchantment": "minecraft:sharpness",
        "level": 2,
        "chance": 0.8
     }
     </pre>

     This is a ChancedRecipe for a 80% chance of Sharpness 2.

     @see Enchantment
     @see SynthesisRecipe
     **/
    public ChancedEnchantment(Enchantment e, int level, float chance) {
        this.enchantment = e;
        this.level = level;
        this.chance = chance;
    }

    Random random = new Random();

    public static ChancedEnchantment fromJson(@Nullable JsonElement json) {
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
                        Optional<Enchantment> enchant = Registries.ENCHANTMENT.getOrEmpty(new Identifier(enchantId));
                        if (enchant.isPresent()) {
                            if (c < 1.0f) {
                                c = 1.0f;
                            }
                            e = enchant.get();
                            return new ChancedEnchantment(e, lvl, c);
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
       Enchantment e = MiscObjectSerializers.readEnchantment(buf);
       int i = buf.readInt();
       float f = buf.readFloat();
       return new ChancedEnchantment(e, i, f);
    }

    public void toPacket(PacketByteBuf buf) {
        MiscObjectSerializers.writeEnchantment(buf, this.enchantment);
        buf.writeInt(this.level);
        buf.writeFloat(this.chance);
    }


    public static void writeArrayListPacket(ArrayList<ChancedEnchantment> array, PacketByteBuf buf) {
        buf.writeInt(array.size());
        for (ChancedEnchantment ce : array) {
            ce.toPacket(buf);
        }
    }

    public static ArrayList<ChancedEnchantment> readArrayListPacket(PacketByteBuf buf) {
        ArrayList<ChancedEnchantment> array = new ArrayList<>();
        int size = buf.readInt();
        for (int i = size; i > 0; i--) {
            array.add(fromPacket(buf));
        }
        return array;
    }

    /**
     * Rolls the Chanced Enchantment.
     * @return Enchantment Entry if rolled, Null if not.
     */
    public EnchantmentLevelEntry rollAndGet() {
        if (this.roll()) {
            return get();
        } else {
            return null;
        }
    }

    /**
     * Gets an EnchantmentLevelEntry based on this Chanced Enchantment.
     * @return Enchantment & Level
     */
    public EnchantmentLevelEntry get() {
        return new EnchantmentLevelEntry(this.enchantment, this.level);
    }

    /**
     * Rolls this Chanced Enchantment.
     * @return true if the roll was successful.
     */
    public boolean roll() {
        float roll = random.nextFloat(1.0f);
        return this.chance >= roll;
    }

}
