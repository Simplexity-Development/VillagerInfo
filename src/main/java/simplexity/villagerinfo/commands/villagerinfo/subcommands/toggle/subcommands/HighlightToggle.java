package simplexity.villagerinfo.commands.villagerinfo.subcommands.toggle.subcommands;

import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import simplexity.villagerinfo.commands.util.SubCommand;
import simplexity.villagerinfo.configurations.locale.MessageInsert;
import simplexity.villagerinfo.configurations.locale.ServerMessage;
import simplexity.villagerinfo.interaction.logic.PlayerToggle;
import simplexity.villagerinfo.util.Perm;
import simplexity.villagerinfo.util.Resolvers;

import java.util.List;

public class HighlightToggle extends SubCommand {
    public HighlightToggle() {
        super(Perm.VILL_COMMAND_TOGGLE_HIGHLIGHT.getPerm(), ServerMessage.HELP_TOGGLE_HIGHLIGHT.getMessage());
    }
    public static final NamespacedKey highlightEnabledKey = new NamespacedKey("vill-info", "vill-highlight-enabled");

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof org.bukkit.entity.Player player)) {
            sender.sendMessage(Resolvers.getInstance().prefixResolver(ServerMessage.NOT_A_PLAYER.getMessage()));
            return;
        }
        String toggleType = MessageInsert.TOGGLE_TYPE_HIGHLIGHT.getMessage();
        boolean isToggleEnabled = PlayerToggle.isPdcToggleEnabled(player, highlightEnabledKey);
        if (isToggleEnabled) {
            PlayerToggle.setPdcToggleDisabled(player, highlightEnabledKey);
            PlayerToggle.sendPlayerFeedback(false, player, toggleType);
        } else {
            PlayerToggle.setPdcToggleEnabled(player, highlightEnabledKey);
            PlayerToggle.sendPlayerFeedback(true, player, toggleType);
        }
    }


    @Override
    public List<String> subCommandTabCompletions(CommandSender sender, String[] args) {
        return List.of();
    }
}
