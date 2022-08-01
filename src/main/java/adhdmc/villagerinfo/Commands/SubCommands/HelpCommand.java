package adhdmc.villagerinfo.Commands.SubCommands;

import adhdmc.villagerinfo.Commands.SubCommand;
import adhdmc.villagerinfo.Config.ConfigValidator;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;

public class HelpCommand extends SubCommand {

    public HelpCommand(){
        super ("help","VillagerInfo help", "/vill help");
    }

    @Override
    public void doThing(CommandSender sender, String[] args) {
        HashMap<String, String> msgs = ConfigValidator.localeMap;

        if(sender.hasPermission("villagerinfo.use")) {
            sender.sendMessage(msgs.get("prefix"));
            sender.sendMessage(msgs.get("help-main"));
            sender.sendMessage(msgs.get("help-toggle"));
            sender.sendMessage(msgs.get("help-reload"));
            return;
        }
        sender.sendMessage(msgs.get("no-permission"));
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return null;
    }
}
