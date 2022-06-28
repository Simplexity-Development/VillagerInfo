package adhdmc.villagerinfo.Commands;

import adhdmc.villagerinfo.MiscHandling.MessageHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommandHandler implements CommandExecutor, TabExecutor {

    public static HashMap<String, SubCommand> subcommandList = new HashMap<String, SubCommand>();
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
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Checking for arguments
        if (args.length == 0){
            sender.sendMessage(MessageHandler.prefix + ChatColor.translateAlternateColorCodes('&', "\n&aAuthor: &6IllogicalSong\n&aVersion:&7 ALPHA\n&aSpecial Thanks to Peashooter101"));
            return true;
        }
        //if has an argument, check to see if it's contained in the list of arguments
        String command = args[0].toLowerCase();
        if (subcommandList.containsKey(command)) {
            subcommandList.get(command).doThing(sender, Arrays.copyOfRange(args, 1, args.length));
        } else {
            sender.sendMessage("Sorry, you input the command incorrectly. Please use /vill help to see proper usage");
        }
        return true;
    }
}
