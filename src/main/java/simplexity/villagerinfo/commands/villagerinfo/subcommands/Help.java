package simplexity.villagerinfo.commands.villagerinfo.subcommands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import simplexity.villagerinfo.commands.util.SubCommand;
import simplexity.villagerinfo.commands.util.SubCommandMaps;
import simplexity.villagerinfo.configurations.locale.ServerMessage;
import simplexity.villagerinfo.util.Perm;
import simplexity.villagerinfo.util.Resolvers;

import java.util.ArrayList;
import java.util.List;

public class Help extends SubCommand {
    public Help() {
        super(Perm.VILL_COMMAND_BASE.getPerm(), ServerMessage.HELP_MAIN.getMessage());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        List<String> helpMessages = new ArrayList<>();
        Component helpMessageFinal = Component.empty();

        SubCommandMaps.getInstance().getVillagerInfoSubCommands().forEach((string, subcommand) -> {
            if (sender.hasPermission(subcommand.getPermission())) {
                helpMessages.add(subcommand.getHelpMessage());
            }
        });
        if (sender.hasPermission(Perm.VILL_COMMAND_TOGGLE.getPerm())) {
            SubCommandMaps.getInstance().getToggleSubCommands().forEach((string, subcommand) -> {
                if (sender.hasPermission(subcommand.getPermission())) {
                    helpMessages.add(subcommand.getHelpMessage());
                }
            });
        }
        for (String message : helpMessages) {
            helpMessageFinal = helpMessageFinal.append(Resolvers.getInstance().prefixResolver(message));
        }
        sender.sendMessage(helpMessageFinal);
    }

    @Override
    public List<String> subCommandTabCompletions(CommandSender sender) {
        return List.of();
    }
}
