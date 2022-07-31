package adhdmc.villagerinfo.Commands.SubCommands;

import adhdmc.villagerinfo.Commands.SubCommand;
import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class ToggleCommand extends SubCommand {

    public ToggleCommand() {
        super("toggle", "Toggles villager info on and off","/vill toggle");
    }

    @Override
    public void doThing(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageHandler.notAPlayer);
            return;
        }
        if(!(sender.hasPermission("villagerinfo.toggle")) || !(sender.hasPermission("villagerinfo.use"))) {
            sender.sendMessage(MessageHandler.noPermission);
            return;
        }
        if (toggleSetting((Player) sender)) {
            sender.sendMessage(MessageHandler.prefix + " " + MessageHandler.toggleOn);
            return;
        }
        sender.sendMessage(MessageHandler.prefix + " " + MessageHandler.toggleOff);
        }

    private boolean toggleSetting(Player p) {
        PersistentDataContainer playerPDC = p.getPersistentDataContainer();
        if (playerPDC.get(new NamespacedKey(VillagerInfo.plugin, "infoToggle"), PersistentDataType.INTEGER) == null ||
            playerPDC.get(new NamespacedKey(VillagerInfo.plugin, "infoToggle"), PersistentDataType.INTEGER).equals(0)){
                playerPDC.set(new NamespacedKey(VillagerInfo.plugin, "infoToggle"), PersistentDataType.INTEGER, 1);
                return false;
            }
                playerPDC.set(new NamespacedKey(VillagerInfo.plugin, "infoToggle"), PersistentDataType.INTEGER, 0);
                return true;
            }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return null;
    }
}
