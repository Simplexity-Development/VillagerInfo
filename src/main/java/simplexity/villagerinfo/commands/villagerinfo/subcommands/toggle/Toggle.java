package simplexity.villagerinfo.commands.villagerinfo.subcommands.toggle;

import org.bukkit.command.CommandSender;
import simplexity.villagerinfo.commands.util.SubCommand;
import simplexity.villagerinfo.commands.util.SubCommandMaps;
import simplexity.villagerinfo.configurations.locale.ServerMessage;
import simplexity.villagerinfo.util.Perm;
import simplexity.villagerinfo.util.Resolvers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Toggle extends SubCommand {
    List<String> tabCompleteList = new ArrayList<>();

    public Toggle() {
        super(Perm.VILL_COMMAND_TOGGLE.getPerm(), ServerMessage.HELP_TOGGLE_BASE.getMessage());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        HashMap<String, SubCommand> toggleSubCommands = SubCommandMaps.getInstance().getToggleSubCommands();
        if (!sender.hasPermission(Perm.VILL_COMMAND_TOGGLE.getPerm())) {
            sender.sendRichMessage(ServerMessage.NO_PERMISSION.getMessage());
            return;
        }
        if (args.length < 2) {
            sender.sendRichMessage(ServerMessage.NOT_ENOUGH_ARGUMENTS.getMessage());
            return;
        }
        if (!(sender instanceof org.bukkit.entity.Player player)) {
            sender.sendMessage(Resolvers.getInstance().prefixResolver(ServerMessage.NOT_A_PLAYER.getMessage()));
            return;
        }
        String subCommand = args[1];
        if (!toggleSubCommands.containsKey(subCommand)) {
            player.sendRichMessage(ServerMessage.SUBCOMMAND_DOES_NOT_EXIST.getMessage());
            return;
        }
        if (!player.hasPermission(toggleSubCommands.get(subCommand).getPermission())) {
            player.sendRichMessage(ServerMessage.NO_PERMISSION.getMessage());
            return;
        }
        toggleSubCommands.get(subCommand).execute(player, args);
    }

    @Override
    public List<String> subCommandTabCompletions(CommandSender sender) {
        tabCompleteList.clear();
        HashMap<String, SubCommand> toggleSubCommands = SubCommandMaps.getInstance().getToggleSubCommands();
        if (!sender.hasPermission(Perm.VILL_COMMAND_TOGGLE.getPerm())) {
            return tabCompleteList;
        }
        toggleSubCommands.forEach((string, subcommand) -> {
            String permission = subcommand.getPermission();
            if (sender.hasPermission(permission)) {
                tabCompleteList.add(string);
            }
        });
        return tabCompleteList;
    }
}
