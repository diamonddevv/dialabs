package net.diamonddev.dialabs.recipe.objects;

import net.minecraft.network.PacketByteBuf;

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
}
