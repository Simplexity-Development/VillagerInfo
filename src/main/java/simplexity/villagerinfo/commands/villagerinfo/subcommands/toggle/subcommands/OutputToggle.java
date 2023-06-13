package simplexity.villagerinfo.commands.villagerinfo.subcommands.toggle.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import simplexity.villagerinfo.commands.util.SubCommand;
import simplexity.villagerinfo.configurations.locale.MessageInsert;
import simplexity.villagerinfo.configurations.locale.ServerMessage;
import simplexity.villagerinfo.events.PlayerToggleEvent;
import simplexity.villagerinfo.util.PDCTag;
import simplexity.villagerinfo.util.Perm;
import simplexity.villagerinfo.util.Resolvers;

import java.util.List;

public class OutputToggle extends SubCommand {
    public OutputToggle() {
        super(Perm.VILL_COMMAND_TOGGLE_OUTPUT.getPerm(), ServerMessage.HELP_TOGGLE_OUTPUT.getMessage());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof org.bukkit.entity.Player player)) {
            sender.sendMessage(Resolvers.getInstance().prefixResolver(ServerMessage.NOT_A_PLAYER.getMessage()));
            return;
        }
        PlayerToggleEvent toggleOutputEvent = callOutputToggleEvent(player);
        if (toggleOutputEvent == null) return;
        byte toggleState = toggleOutputEvent.getCurrentToggleState();
        if (toggleState == (byte) 0) {
            toggleOutputEvent.setDisabled();
            return;
        }
        if (toggleState == (byte) 1) {
            toggleOutputEvent.setEnabled();
        }
    }

    public PlayerToggleEvent callOutputToggleEvent(org.bukkit.entity.Player player) {
        PlayerToggleEvent toggleOutputEvent = new PlayerToggleEvent(player, PDCTag.PLAYER_TOGGLE_OUTPUT_ENABLED.getPdcTag(), MessageInsert.TOGGLE_TYPE_OUTPUT.getMessage());
        Bukkit.getServer().getPluginManager().callEvent(toggleOutputEvent);
        if (toggleOutputEvent.isCancelled()) return null;
        return toggleOutputEvent;
    }

    @Override
    public List<String> subCommandTabCompletions(CommandSender sender) {
        return List.of();
    }
}
