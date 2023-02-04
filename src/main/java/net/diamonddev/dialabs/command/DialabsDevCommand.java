package net.diamonddev.dialabs.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.diamonddev.dialabs.api.v0.recipe.DialabsRecipeManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class DialabsDevCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("dialabsdev").requires(scs -> scs.hasPermissionLevel(2))
                        .then(literal("getinfo")
                                .then(literal("recipecache")
                                        .executes(DialabsDevCommand::exeGetDDVRecipeCache)
                                )
                        )
        );
    }

    private static int exeGetDDVRecipeCache(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(Text.literal(DialabsRecipeManager.CACHE.toString()), true);
        return 1;
    }
}
