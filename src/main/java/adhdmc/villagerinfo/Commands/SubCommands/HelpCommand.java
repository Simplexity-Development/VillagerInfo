package adhdmc.villagerinfo.Commands.SubCommands;

import adhdmc.villagerinfo.Commands.SubCommand;
import adhdmc.villagerinfo.Config.ConfigValidator;
import adhdmc.villagerinfo.VillagerInfo;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import adhdmc.villagerinfo.Config.ConfigValidator.Message;

import java.util.HashMap;
import java.util.List;

public class HelpCommand extends SubCommand {
    MiniMessage mM = MiniMessage.miniMessage();

    public HelpCommand(){
        super ("help","VillagerInfo help", "/vill help");
    }

    @Override
    public void doThing(CommandSender sender, String[] args) {
        HashMap<Message, String> msgs = ConfigValidator.localeMap;

        if(sender.hasPermission(VillagerInfo.usePermission)) {
            sender.sendMessage(mM.deserialize(msgs.get(Message.PREFIX)));
            sender.sendMessage(mM.deserialize(msgs.get(Message.HELP_MAIN)));
            sender.sendMessage(mM.deserialize(msgs.get(Message.HELP_TOGGLE)));
            sender.sendMessage(mM.deserialize(msgs.get(Message.HELP_RELOAD)));
            return;
        }
        sender.sendMessage(mM.deserialize(msgs.get(Message.NO_PERMISSION)));
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return null;
    }
}
