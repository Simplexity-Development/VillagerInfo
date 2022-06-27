package adhdmc.villagerinfo.commands.subcommands;

import adhdmc.villagerinfo.MessageHandler;
import adhdmc.villagerinfo.commands.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HelpCommand extends SubCommand {

    public HelpCommand(){
        super ("help","Villager Info help", "/vill help");
    }

    @Override
    public void doThing(CommandSender sender, String[] args) {
        if(sender.hasPermission("villagerinfo.use")) {
            sender.sendMessage(MessageHandler.prefix);
            sender.sendMessage(MessageHandler.helpMain);
            sender.sendMessage(MessageHandler.helpToggle);
            sender.sendMessage(MessageHandler.helpReload);
            return;
        }
        sender.sendMessage(MessageHandler.noPermission);
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return null;
    }
}
