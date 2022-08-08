package adhdmc.villagerinfo.Commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommand {

    private String name;
    private String description;
    private String syntax;

    public SubCommand(String name, String description, String syntax) {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSyntax() {
        return syntax;
    }

    public abstract void doThing(CommandSender sender, String[] args);

    public abstract List<String> getSubcommandArguments(CommandSender sender, String[] args);

}
