package adhdmc.villagerinfo.Commands.SubCommands;

import adhdmc.villagerinfo.Commands.SubCommand;
import adhdmc.villagerinfo.Config.Message;
import adhdmc.villagerinfo.Config.Perms;
import adhdmc.villagerinfo.VillagerInfo;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class ToggleCommand extends SubCommand {

    MiniMessage miniMessage = VillagerInfo.getMiniMessage();
    public ToggleCommand() {
        super("toggle", "Toggles villager info on and off", "/vill toggle");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(miniMessage.deserialize(Message.NOT_A_PLAYER.getMessage()));
            return;
        }
        if (!(sender.hasPermission(Perms.TOGGLE.getPerm()))) {
            sender.sendMessage(miniMessage.deserialize(Message.NO_PERMISSION.getMessage()));
            return;
        }
        if (toggleSetting((Player) sender)) {
            sender.sendMessage(miniMessage.deserialize(Message.PREFIX.getMessage())
                    .append(miniMessage.deserialize(Message.TOGGLE_ON.getMessage())));
            return;
        }
        sender.sendMessage(miniMessage.deserialize(Message.PREFIX.getMessage())
                .append(miniMessage.deserialize(Message.TOGGLE_OFF.getMessage())));
    }

    private boolean toggleSetting(Player player) {
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        byte togglePDC = playerPDC.getOrDefault(VillagerInfo.INFO_ENABLED_KEY, PersistentDataType.BYTE, (byte)0);
        if (togglePDC == 1) {
            playerPDC.set(VillagerInfo.INFO_ENABLED_KEY, PersistentDataType.BYTE, (byte)0);
            return false;
        }
        playerPDC.set(VillagerInfo.INFO_ENABLED_KEY, PersistentDataType.BYTE, (byte)1);
        return true;
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return null;
    }
}
