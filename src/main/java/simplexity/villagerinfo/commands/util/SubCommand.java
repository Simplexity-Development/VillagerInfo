package simplexity.villagerinfo.commands.util;

import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommand {
    private final String helpMessage;
    private final String permission;

    public SubCommand(String permission, String helpMessage) {
        this.permission = permission;
        this.helpMessage = helpMessage;
    }

    public String getPermission() {
        return permission;
    }

    public String getHelpMessage() {
        return helpMessage;
    }

    public abstract void execute(CommandSender sender, String[] args);
    public abstract List<String> subCommandTabCompletions(CommandSender sender, String[] args);
    
}
