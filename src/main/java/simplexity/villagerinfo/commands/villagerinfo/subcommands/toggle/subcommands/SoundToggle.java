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

public class SoundToggle extends SubCommand {
    public SoundToggle() {
        super(Perm.VILL_COMMAND_TOGGLE_SOUND.getPerm(), ServerMessage.HELP_TOGGLE_SOUND.getMessage());
    }

    public static NamespacedKey soundEnabledKey = new NamespacedKey("vill-info", "vill-output-sound-enabled");

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Resolvers.getInstance().prefixResolver(ServerMessage.NOT_A_PLAYER.getMessage()));
            return;
        }
        String toggleType = MessageInsert.TOGGLE_TYPE_SOUND.getMessage();
        boolean isToggleEnabled = PlayerToggle.isPdcToggleEnabled(player, soundEnabledKey);
        if (isToggleEnabled) {
            PlayerToggle.setPdcToggleDisabled(player, soundEnabledKey);
            PlayerToggle.sendPlayerFeedback(false, player, toggleType);
        } else {
            PlayerToggle.setPdcToggleEnabled(player, soundEnabledKey);
            PlayerToggle.sendPlayerFeedback(true, player, toggleType);
        }
    }


    @Override
    public List<String> subCommandTabCompletions(CommandSender sender) {
        return List.of();
    }
}
