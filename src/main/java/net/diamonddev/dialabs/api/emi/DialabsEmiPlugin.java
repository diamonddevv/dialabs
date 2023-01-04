package net.diamonddev.dialabs.api.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.api.emi.recipe.EmiSynthesisRecipe;
import net.diamonddev.dialabs.recipe.SynthesisRecipe;
import net.diamonddev.dialabs.registry.InitBlocks;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;

public class DialabsEmiPlugin implements EmiPlugin {

    public static final Identifier SYNTHESIS_RECIPE_SPRITESHEET = Dialabs.id.build("textures/emi/gui/synthesis_recipe_sprites.png");


    public static final EmiStack SYNTHESIS_WORKSTATION = EmiStack.of(InitBlocks.ENCHANTMENT_SYNTHESIZER);


    public static final EmiRecipeCategory SYNTHESIS_CATEGORY
            = new EmiRecipeCategory(Dialabs.id.build("synthesis_category"), new EmiTexture(SYNTHESIS_RECIPE_SPRITESHEET, 0, 0, 16, 16));



    @Override
    public void register(EmiRegistry registry) {
        // Tell EMI to add a tab for categories
        registry.addCategory(SYNTHESIS_CATEGORY);

        // Add all the workstations categories use
        registry.addWorkstation(SYNTHESIS_CATEGORY, SYNTHESIS_WORKSTATION);

        // Use vanilla's concept of recipes and pass them to EmiRecipe representation
        RecipeManager manager = registry.getRecipeManager();

        for (SynthesisRecipe recipe : manager.listAllOfType(SynthesisRecipe.Type.INSTANCE)) {
            registry.addRecipe(new EmiSynthesisRecipe(recipe));
        }
    }
}
