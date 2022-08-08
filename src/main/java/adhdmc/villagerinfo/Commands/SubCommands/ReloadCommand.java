package adhdmc.villagerinfo.Commands.SubCommands;

import adhdmc.villagerinfo.Commands.SubCommand;
import adhdmc.villagerinfo.Config.ConfigValidator;
import adhdmc.villagerinfo.Config.ConfigValidator.Message;
import adhdmc.villagerinfo.VillagerInfo;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class ReloadCommand extends SubCommand {

    public ReloadCommand() {
        super("reload", "Reloads the VillagerInfo plugin", "/vill reload");
    }


    @Override
    public void doThing(CommandSender sender, String[] args) {
        Map<Message, String> msgs = ConfigValidator.getLocaleMap();
        MiniMessage mM = MiniMessage.miniMessage();
        if (!(sender instanceof Player) || sender.hasPermission(VillagerInfo.reloadCommandPermission)) {
            VillagerInfo.plugin.reloadConfig();
            VillagerInfo.localeConfig.reloadConfig();
            ConfigValidator.configValidator();
            sender.sendMessage(mM.deserialize(msgs.get(Message.CONFIG_RELOADED)));
        } else {
            sender.sendMessage(mM.deserialize(msgs.get(Message.NO_PERMISSION)));
        }
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return null;
    }
}
