package net.diamonddev.dialabs.api;


import net.diamonddev.dialabs.DiaLabs;

public class Identifier extends net.minecraft.util.Identifier {
    public Identifier(String path) {
        super(DiaLabs.MOD_ID, path);
    }
}
