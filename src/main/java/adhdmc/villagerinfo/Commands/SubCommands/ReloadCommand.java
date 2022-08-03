package adhdmc.villagerinfo.Commands.SubCommands;

import adhdmc.villagerinfo.Commands.SubCommand;
import adhdmc.villagerinfo.Config.ConfigValidator;
import adhdmc.villagerinfo.VillagerInfo;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadCommand extends SubCommand {

    public ReloadCommand(){
        super("reload", "Reloads the VillagerInfo plugin", "/vill reload");
    }


    @Override
    public void doThing(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)|| sender.hasPermission("villagerinfo.reload")) {
            VillagerInfo.plugin.reloadConfig();
            VillagerInfo.localeConfig.reloadConfig();
            ConfigValidator.configValidator();
            String configReload = ConfigValidator.localeMap.get("config-reloaded");
            sender.sendMessage(MiniMessage.miniMessage().deserialize(configReload));
        }
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return null;
    }
}
