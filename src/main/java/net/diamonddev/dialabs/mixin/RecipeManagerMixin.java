package net.diamonddev.dialabs.mixin;

import com.google.gson.JsonElement;
import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.recipe.SynthesisRecipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At("HEAD"))
    private void dialabs$preventLoadSynthesisRecipesWithoutModids(
            Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci
    ) {
        ArrayList<Identifier> toRemove = new ArrayList<>();
        for (Map.Entry<Identifier, JsonElement> entry : map.entrySet()) {
            if (entry.getKey().toString().matches(Dialabs.MOD_ID + SynthesisRecipe.Type.ID)) {
                toRemove.add(entry.getKey());
            }
        }
        toRemove.forEach(map::remove);
    }

}
