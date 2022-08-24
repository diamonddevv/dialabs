package net.diamonddev.dialabs.util;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class DiaLabsGamerules {

    public static GameRules.Key<GameRules.IntRule> CRYSTAL_SHARD_STRENGTH;
    public static GameRules.Key<GameRules.IntRule> STATIC_CORE_STRENGTH;
    public static GameRules.Key<GameRules.IntRule> CRYSTAL_SHARD_LENGTH;
    public static GameRules.Key<GameRules.IntRule> STATIC_CORE_LENGTH;
    public static GameRules.Key<GameRules.IntRule> WITHERED_ASPECT_SPL;

    public static GameRules.Key<GameRules.IntRule> WITHERED_ASPECT_APL;


    public static void registerGamerules() {
       CRYSTAL_SHARD_STRENGTH = GameRuleRegistry.register("crystalShardStrength", GameRules.Category.MOBS,
                GameRuleFactory.createIntRule(1));

        STATIC_CORE_STRENGTH = GameRuleRegistry.register("staticCoreStrength", GameRules.Category.MOBS,
                GameRuleFactory.createIntRule(1));

        CRYSTAL_SHARD_LENGTH = GameRuleRegistry.register("crystalShardLengthInSeconds", GameRules.Category.MOBS,
                GameRuleFactory.createIntRule(60));

        STATIC_CORE_LENGTH = GameRuleRegistry.register("staticCoreLengthInSeconds", GameRules.Category.MOBS,
                GameRuleFactory.createIntRule(60));

        WITHERED_ASPECT_SPL = GameRuleRegistry.register("witheredAspectSecondsPerLevel", GameRules.Category.MOBS,
                GameRuleFactory.createIntRule(5));

        WITHERED_ASPECT_APL = GameRuleRegistry.register("witheredAspectAmplifierPerLevel", GameRules.Category.MOBS,
                GameRuleFactory.createIntRule(1));
    }
}
