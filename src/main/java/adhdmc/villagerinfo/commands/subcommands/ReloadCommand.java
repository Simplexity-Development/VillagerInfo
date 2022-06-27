package adhdmc.villagerinfo.commands.subcommands;

import adhdmc.villagerinfo.MessageHandler;
import adhdmc.villagerinfo.VillagerInfo;
import adhdmc.villagerinfo.commands.SubCommand;
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
        MessageHandler.loadConfigMsgs();
        sender.sendMessage(MessageHandler.configReload);
        if(MessageHandler.soundErrorMsg("") != null){
            sender.sendMessage(MessageHandler.soundErrorMsg(""));
            }
        }
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return null;
    }
}
