package adhdmc.villagerinfo.commands.subcommands;

import adhdmc.villagerinfo.MessageHandler;
import adhdmc.villagerinfo.VillagerHandler;
import adhdmc.villagerinfo.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

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
        UUID uuid = p.getUniqueId();
        if (VillagerHandler.villagerCheck.containsKey(uuid)) {
            if (VillagerHandler.villagerCheck.get(uuid)) {
                VillagerHandler.villagerCheck.put(uuid, false);
                return false;
            } else {
                VillagerHandler.villagerCheck.put(uuid, true);
                return true;
            }
        }
        VillagerHandler.villagerCheck.put(p.getUniqueId(), false);
        return false;
    }
    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return null;
    }
}
