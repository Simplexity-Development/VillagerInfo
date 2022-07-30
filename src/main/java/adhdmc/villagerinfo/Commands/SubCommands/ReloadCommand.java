package adhdmc.villagerinfo.Commands.SubCommands;

import adhdmc.villagerinfo.Config.ConfigValidator;
import adhdmc.villagerinfo.MiscHandling.MessageHandler;
import adhdmc.villagerinfo.VillagerInfo;
import adhdmc.villagerinfo.Commands.SubCommand;
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
        sender.sendMessage(MessageHandler.configReload);
        if(ConfigValidator.soundErrorMsg("") != null){
            sender.sendMessage(ConfigValidator.soundErrorMsg(""));
            }
        }
        if(ConfigValidator.timeErrorMsg("") != null){
            sender.sendMessage(ConfigValidator.timeErrorMsg(""));
        }
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return null;
    }
}
