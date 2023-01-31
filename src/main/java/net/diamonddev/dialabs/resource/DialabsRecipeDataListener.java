package net.diamonddev.dialabs.resource;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.api.v0.recipe.DialabsRecipe;
import net.diamonddev.dialabs.api.v0.recipe.DialabsRecipeManager;
import net.diamonddev.dialabs.api.v0.recipe.DialabsRecipeType;
import net.diamonddev.dialabs.registry.InitResourceListener;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DialabsRecipeDataListener implements SimpleSynchronousResourceReloadListener {

    /*
    Since Striking is such a unique recipe concept, Minecraft's built-in abstraction cannot conform to its needs.
    To solve this, I am adding a custom recipe reader. It will function the same for the user, and for Datapack creators,
    more-or-less the same.

    This system is in an early form.

    An example of a Shocking Recipe can be found in 'data/dialabs/dialabs_recipes/striking/shocked_iron.json'.
     */

    private static final Gson gson = new Gson();


    @Override
    public Identifier getFabricId() {
        return Dialabs.id.build("dialabs_recipe_data");
    }

    @Override
    public void reload(ResourceManager manager) {
        // Clear Cache
        DialabsRecipeManager.CACHE.clear();


        // Read
        for (Identifier id : manager.findResources("dialabs_recipes", path -> path.getPath().endsWith(".json")).keySet()) {
            if (manager.getResource(id).isPresent()) {
                try (InputStream stream = manager.getResource(id).get().getInputStream()) {
                    // Consume stream
                    InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8); // Create Reader
                    JsonObject json = gson.fromJson(reader, JsonObject.class);
                    Identifier typeId = new Identifier(json.get(DialabsRecipeManager.IDPARAM).getAsString());

                    // Read from Type
                    DialabsRecipeType type = DialabsRecipeManager.getType(typeId);

                    // Read JSON
                    DialabsRecipe recipe = new DialabsRecipe(type);

                    // Add keys
                    ArrayList<String> jsonKeys = new ArrayList<>();
                    type.addJsonKeys(jsonKeys);
                    jsonKeys.forEach(s -> recipe.getHash().put(s, json.get(s)));

                    // Add
                    DialabsRecipeManager.CACHE.add(recipe);

                } catch (Exception e) {
                    InitResourceListener.RESOURCE_MANAGER_LOGGER.error("Error occurred while loading Dialabs Recipe resource json " + id.toString(), e);
                }
            }
        }
    }
}
