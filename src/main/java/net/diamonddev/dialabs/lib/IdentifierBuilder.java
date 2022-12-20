package net.diamonddev.dialabs.lib;

import net.minecraft.util.Identifier;

public class IdentifierBuilder {
    private final String source;

    public IdentifierBuilder(String source) {
        this.source = source;
    }

    public Identifier build(String path) {
        return new Identifier(source, path);
    }
}
