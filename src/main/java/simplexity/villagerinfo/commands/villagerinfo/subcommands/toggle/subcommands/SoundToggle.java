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

public class SoundToggle extends SubCommand {
    public SoundToggle() {
        super(Perm.VILL_COMMAND_TOGGLE_SOUND.getPerm(), ServerMessage.HELP_TOGGLE_SOUND.getMessage());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof org.bukkit.entity.Player player)) {
            sender.sendMessage(Resolvers.getInstance().prefixResolver(ServerMessage.NOT_A_PLAYER.getMessage()));
            return;
        }
        PlayerToggleEvent toggleSoundEvent = callToggleSoundEvent(player);
        if (toggleSoundEvent == null) return;
        byte toggleState = toggleSoundEvent.getCurrentToggleState();
        if (toggleState == (byte) 0) {
            toggleSoundEvent.setDisabled();
            return;
        }
        if (toggleState == (byte) 1) {
            toggleSoundEvent.setEnabled();
        }
    }

    public PlayerToggleEvent callToggleSoundEvent(org.bukkit.entity.Player player) {
        PlayerToggleEvent toggleSoundEvent = new PlayerToggleEvent(player, PDCTag.PLAYER_TOGGLE_SOUND_ENABLED.getPdcTag(), MessageInsert.TOGGLE_TYPE_SOUND.getMessage());
        Bukkit.getServer().getPluginManager().callEvent(toggleSoundEvent);
        if (toggleSoundEvent.isCancelled()) return null;
        return toggleSoundEvent;
    }

    @Override
    public List<String> subCommandTabCompletions(CommandSender sender) {
        return List.of();
    }
}
