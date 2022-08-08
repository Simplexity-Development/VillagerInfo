package adhdmc.villagerinfo.Commands;

import adhdmc.villagerinfo.Config.ConfigValidator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.*;

public class CommandHandler implements CommandExecutor, TabExecutor {

    public static HashMap<String, SubCommand> subcommandList = new HashMap<String, SubCommand>();
    Map<ConfigValidator.Message, String> msgs = ConfigValidator.getMapping();
    MiniMessage mM = MiniMessage.miniMessage();
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
            sender.sendMessage(mM.deserialize("<green><click:open_url:'https://github.com/RhythmicSys/VillagerInfo'><hover:show_text:'<gray>Click here to visit the GitHub!'>VillagerInfo | Version:<version> </hover></click>\nAuthors: <gold> Rhythmic </gold> | <gold>Peashooter101</gold>"));
            return true;
        }
        //if has an argument, check to see if it's contained in the list of arguments
        String command = args[0].toLowerCase();
        if (subcommandList.containsKey(command)) {
            subcommandList.get(command).doThing(sender, Arrays.copyOfRange(args, 1, args.length));
        } else {
            sender.sendMessage(mM.deserialize(msgs.get(ConfigValidator.Message.NO_COMMAND)));
        }
        return true;
    }
}
