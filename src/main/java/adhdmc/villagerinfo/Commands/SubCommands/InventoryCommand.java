package adhdmc.villagerinfo.Commands.SubCommands;

import adhdmc.villagerinfo.Commands.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class InventoryCommand extends SubCommand {


    public InventoryCommand() {
        super("inv", "gets the inventory of the specified villager", "/vill inv <UUID>");
    }


    @Override
    public void execute(CommandSender sender, String[] args) {

    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return null;
    }
}
