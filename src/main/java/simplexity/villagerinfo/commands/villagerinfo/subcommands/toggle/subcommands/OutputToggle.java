package simplexity.villagerinfo.commands.villagerinfo.subcommands.toggle.subcommands;

import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import simplexity.villagerinfo.commands.util.SubCommand;
import simplexity.villagerinfo.configurations.locale.MessageInsert;
import simplexity.villagerinfo.configurations.locale.ServerMessage;
import simplexity.villagerinfo.interaction.logic.PlayerToggle;
import simplexity.villagerinfo.util.Perm;
import simplexity.villagerinfo.util.Resolvers;

import java.util.List;

public class OutputToggle extends SubCommand {
    public OutputToggle() {
        super(Perm.VILL_COMMAND_TOGGLE_OUTPUT.getPerm(), ServerMessage.HELP_TOGGLE_OUTPUT.getMessage());
    }


    public static NamespacedKey outputEnabledKey = new NamespacedKey("vill-info", "vill-info-enabled");

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Resolvers.getInstance().prefixResolver(ServerMessage.NOT_A_PLAYER.getMessage()));
            return;
        }
        String toggleType = MessageInsert.TOGGLE_TYPE_OUTPUT.getMessage();
        boolean isToggleEnabled = PlayerToggle.isPdcToggleEnabled(player, outputEnabledKey);
        if (isToggleEnabled) {
            PlayerToggle.setPdcToggleDisabled(player, outputEnabledKey);
            PlayerToggle.sendPlayerFeedback(false, player, toggleType);
        } else {
            PlayerToggle.setPdcToggleEnabled(player, outputEnabledKey);
            PlayerToggle.sendPlayerFeedback(true, player, toggleType);
        }
    }


    @Override
    public List<String> subCommandTabCompletions(CommandSender sender) {
        return List.of();
    }

}