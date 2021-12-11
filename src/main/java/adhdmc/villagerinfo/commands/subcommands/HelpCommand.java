package adhdmc.villagerinfo.commands.subcommands;

import adhdmc.villagerinfo.commands.SubCommand;
import org.bukkit.entity.Player;

import java.util.List;

public class HelpCommand extends SubCommand {

    public HelpCommand(){super ("help","Villager Info help", "/vill help");}


    @Override
    public void doThing(Player player, String[] args) {
        
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
