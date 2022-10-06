package adhdmc.villagerinfo.Commands.SubCommands;

import adhdmc.villagerinfo.Commands.SubCommand;
import adhdmc.villagerinfo.Config.Message;
import adhdmc.villagerinfo.Config.Perms;
import adhdmc.villagerinfo.VillagerInfo;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HelpCommand extends SubCommand {

    public HelpCommand() {
        super("help", "VillagerInfo help", "/vill help");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        MiniMessage miniMessage = VillagerInfo.getMiniMessage();

        if (sender.hasPermission(Perms.USE.getPerm())) {
            sender.sendMessage(miniMessage.deserialize(Message.PREFIX.getMessage()));
            sender.sendMessage(miniMessage.deserialize(Message.HELP_MAIN.getMessage()));
            sender.sendMessage(miniMessage.deserialize(Message.HELP_TOGGLE.getMessage()));
            sender.sendMessage(miniMessage.deserialize(Message.HELP_RELOAD.getMessage()));
            return;
        }
        sender.sendMessage(miniMessage.deserialize(Message.NO_PERMISSION.getMessage()));
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return null;
    }
}
