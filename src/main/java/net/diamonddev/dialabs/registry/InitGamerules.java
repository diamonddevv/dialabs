package net.diamonddev.dialabs.registry;

import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class InitGamerules implements RegistryInitializer {

    public static GameRules.Key<GameRules.IntRule> CRYSTAL_SHARD_STRENGTH;
    public static GameRules.Key<GameRules.IntRule> CRYSTAL_SHARD_LENGTH;

    public static GameRules.Key<GameRules.IntRule> STATIC_CORE_STRENGTH;
    public static GameRules.Key<GameRules.IntRule> STATIC_CORE_LENGTH;

    public static GameRules.Key<GameRules.IntRule> WITHERED_ASPECT_SPL;
    public static GameRules.Key<GameRules.IntRule> WITHERED_ASPECT_APL;

    public static GameRules.Key<GameRules.IntRule> RETRIBUTION_STRENGTH;
    public static GameRules.Key<GameRules.IntRule> RETRIBUTION_LENGTH;


    @Override
    public void register() {
       CRYSTAL_SHARD_STRENGTH = GameRuleRegistry.register("crystalShardStrength", GameRules.Category.MOBS,
                GameRuleFactory.createIntRule(1));

       CRYSTAL_SHARD_LENGTH = GameRuleRegistry.register("crystalShardLengthInSeconds", GameRules.Category.MOBS,
                GameRuleFactory.createIntRule(60));



       STATIC_CORE_LENGTH = GameRuleRegistry.register("staticCoreLengthInSeconds", GameRules.Category.MOBS,
                GameRuleFactory.createIntRule(60));

        STATIC_CORE_STRENGTH = GameRuleRegistry.register("staticCoreStrength", GameRules.Category.MOBS,
                GameRuleFactory.createIntRule(1));



       WITHERED_ASPECT_SPL = GameRuleRegistry.register("witheredAspectSecondsPerLevel", GameRules.Category.MOBS,
                GameRuleFactory.createIntRule(5));

       WITHERED_ASPECT_APL = GameRuleRegistry.register("witheredAspectAmplifierPerLevel", GameRules.Category.MOBS,
                GameRuleFactory.createIntRule(1));


       RETRIBUTION_STRENGTH = GameRuleRegistry.register("retributionStrength", GameRules.Category.MOBS,
               GameRuleFactory.createIntRule(0));

        RETRIBUTION_LENGTH = GameRuleRegistry.register("retributionLrength", GameRules.Category.MOBS,
                GameRuleFactory.createIntRule(400));

    }
}
