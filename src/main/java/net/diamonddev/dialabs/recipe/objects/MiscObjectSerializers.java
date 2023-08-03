package net.diamonddev.dialabs.recipe.objects;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;

import java.util.ArrayList;

public class MiscObjectSerializers {
    public static void writeStringArray(PacketByteBuf buf, ArrayList<String> strings) {
        buf.writeInt(strings.size());
        strings.forEach(buf::writeString);
    }

    public static ArrayList<String> readStringArray(PacketByteBuf buf) {
        ArrayList<String> strings = new ArrayList<>();
        int length = buf.readInt();
        for (int i = 0; i < length; i++) {
            strings.add(buf.readString());
        }
        return strings;
    }


    public static void writeEnchantment(PacketByteBuf buf, Enchantment entry) {
        buf.writeIdentifier(Registries.ENCHANTMENT.getId(entry));
    }

    public static Enchantment readEnchantment(PacketByteBuf buf) {
        return Registries.ENCHANTMENT.get(buf.readIdentifier());
    }
}
