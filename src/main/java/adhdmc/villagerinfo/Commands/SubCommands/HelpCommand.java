package adhdmc.villagerinfo.Commands.SubCommands;

import adhdmc.villagerinfo.Commands.SubCommand;
import adhdmc.villagerinfo.Config.ConfigValidator;
import adhdmc.villagerinfo.VillagerInfo;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;

public class HelpCommand extends SubCommand {
    MiniMessage mM = MiniMessage.miniMessage();

    public HelpCommand(){
        super ("help","VillagerInfo help", "/vill help");
    }

    @Override
    public void doThing(CommandSender sender, String[] args) {
        HashMap<String, String> msgs = ConfigValidator.localeMap;

        if(sender.hasPermission(VillagerInfo.usePermission)) {
            sender.sendMessage(mM.deserialize(msgs.get("prefix")));
            sender.sendMessage(mM.deserialize(msgs.get("help-main")));
            sender.sendMessage(mM.deserialize(msgs.get("help-toggle")));
            sender.sendMessage(mM.deserialize(msgs.get("help-reload")));
            return;
        }
        sender.sendMessage(mM.deserialize(msgs.get("no-permission")));
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return null;
    }
}
