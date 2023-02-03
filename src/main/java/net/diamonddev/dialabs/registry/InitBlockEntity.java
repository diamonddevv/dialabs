package net.diamonddev.dialabs.registry;

import com.mojang.datafixers.types.Type;
import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.block.entity.SoulBasinBlockEntity;
import net.diamonddev.dialabs.lib.RegistryInit;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InitBlockEntity implements RegistryInit {

    public static final RegisteredBlockEntityType<SoulBasinBlockEntity> SOUL_BASIN =
            create(SoulBasinBlockEntity::new, Dialabs.id.build("soul_basin_block_entity"), InitBlocks.SOUL_BASIN);

    @Override
    public void init() {
        Registry.register(Registries.BLOCK_ENTITY_TYPE, SOUL_BASIN.id, SOUL_BASIN.blockEntityType);
    }


    private static <T extends BlockEntity> RegisteredBlockEntityType<T> create(
            BlockEntityType.BlockEntityFactory<? extends T> factory,
            Identifier id,
            Block... blocks
    ) {
        Type<?> type = Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id.getPath());
        Set<Block> blockSet = new HashSet<>(List.of(blocks));

        BlockEntityType<T> blockEntity = new BlockEntityType<>(factory, blockSet, type);
        return new RegisteredBlockEntityType<>(id, blockEntity);
    }


    public record RegisteredBlockEntityType<T extends BlockEntity>(Identifier id, BlockEntityType<T> blockEntityType) {}

}
