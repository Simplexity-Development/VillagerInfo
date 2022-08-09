package adhdmc.villagerinfo.Commands.SubCommands;

import adhdmc.villagerinfo.Commands.SubCommand;
import adhdmc.villagerinfo.Config.ConfigValidator;
import adhdmc.villagerinfo.Config.ConfigValidator.Message;
import adhdmc.villagerinfo.VillagerInfo;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public class HelpCommand extends SubCommand {

    public HelpCommand() {
        super("help", "VillagerInfo help", "/vill help");
    }

    @Override
    public void doThing(CommandSender sender, String[] args) {
        Map<Message, String> msgs = ConfigValidator.getLocaleMap();
        MiniMessage mM = MiniMessage.miniMessage();

        if (sender.hasPermission(VillagerInfo.usePermission)) {
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
