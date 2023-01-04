package net.diamonddev.dialabs.api.emi.recipe;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.diamonddev.dialabs.api.emi.DialabsEmiPlugin;
import net.diamonddev.dialabs.recipe.SynthesisRecipe;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmiSynthesisRecipe implements EmiRecipe {

    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public EmiSynthesisRecipe(SynthesisRecipe recipe) {
        this.id = recipe.getId();


        this.input = List.of(
                EmiIngredient.of(recipe.getIngredients().get(SynthesisRecipe.getSlotIndex(SynthesisRecipe.SlotIndices.DISC))),

                EmiIngredient.of(recipe.getIngredients().get(SynthesisRecipe.getSlotIndex(SynthesisRecipe.SlotIndices.PAY))),

                EmiIngredient.of(recipe.getIngredients().get(SynthesisRecipe.getSlotIndex(SynthesisRecipe.SlotIndices.A))),
                EmiIngredient.of(recipe.getIngredients().get(SynthesisRecipe.getSlotIndex(SynthesisRecipe.SlotIndices.B))),
                EmiIngredient.of(recipe.getIngredients().get(SynthesisRecipe.getSlotIndex(SynthesisRecipe.SlotIndices.C)))
        );



        this.output = List.of(
                EmiStack.of(recipe.getOutput())
        );
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return DialabsEmiPlugin.SYNTHESIS_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return input;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return output;
    }

    @Override
    public int getDisplayWidth() {
        return 76;
    }

    @Override
    public int getDisplayHeight() {
        return 18;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        // Disc Slot
        widgets.addSlot(input.get(0), 0, 44);

        // Pay Slot
        widgets.addSlot(input.get(1), 20, 44);

        // Inputs
        widgets.addSlot(input.get(2), 67, 12);
        widgets.addSlot(input.get(3), 67, 32);
        widgets.addSlot(input.get(4), 67, 52);

        // Output
        widgets.addSlot(output.get(0), 30, 14).recipeContext(this);

    }
}
