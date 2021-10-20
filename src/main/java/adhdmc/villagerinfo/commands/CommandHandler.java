package adhdmc.villagerinfo.commands;

import adhdmc.villagerinfo.MessageHandler;
import adhdmc.villagerinfo.VillagerHandler;
import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CommandHandler implements CommandExecutor, TabExecutor {

    //TY Peashooter101
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> subCommands1 = new ArrayList<String>(Arrays.asList("help", "toggle", "reload"));
        if (args.length == 1) {
            return subCommands1;
        }
        return null;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player user;
        //checking if player, error if not
        if (sender instanceof Player) {
            user = (Player) sender;
        } else {
            if(args[0].equalsIgnoreCase("reload")) {
                VillagerInfo.plugin.reloadConfig();
                MessageHandler.loadConfigMsgs();
                sender.sendMessage(MessageHandler.configReload);
            } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to run this command");
            }
            return true;
        }

        //checking for arguments
        if (args.length == 0) {
            user.sendMessage(MessageHandler.prefix + ChatColor.translateAlternateColorCodes('&', "\n&aAuthor: &6IllogicalSong\n&aVersion:&7 ALPHA\n&aSpecial Thanks to Peashooter101"));
            return true;
        }

        //aaaaaaaaaaaaaaaaaaaaaaa
        if(args.length == 1) {
            if (args[0].equalsIgnoreCase("toggle")) {
                if(user.hasPermission("villagerinfo.toggle")) {
                    if (toggleSetting(user)) {
                        user.sendMessage(MessageHandler.prefix + " " + MessageHandler.toggleOn);
                    } else {
                        user.sendMessage(MessageHandler.prefix + " " + MessageHandler.toggleOff);
                    }
                } else {
                    user.sendMessage(MessageHandler.noPermission);
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("help")) {
                if(user.hasPermission("villagerinfo.use")) {
                    user.sendMessage(MessageHandler.prefix);
                    user.sendMessage(MessageHandler.helpMain);
                    user.sendMessage(MessageHandler.helpToggle);
                    user.sendMessage(MessageHandler.helpReload);
                } else {
                    user.sendMessage(MessageHandler.noPermission);
                }
                return true;
            }
            if(args[0].equalsIgnoreCase("reload")){
                if(user.hasPermission("villagerinfo.reload")){
                    VillagerInfo.plugin.reloadConfig();
                    MessageHandler.loadConfigMsgs();
                    user.sendMessage(MessageHandler.prefix + " " + MessageHandler.configReload);
                } else {
                    user.sendMessage(MessageHandler.noPermission);
                }
                return true;
            }
            user.sendMessage(MessageHandler.prefix + " " + MessageHandler.noCommand);
            return true;
        }
        //why ppl tryina put too many words tho
        user.sendMessage(MessageHandler.prefix + " " + MessageHandler.noCommand);
        return true;
    }
    //Toggle
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
}
