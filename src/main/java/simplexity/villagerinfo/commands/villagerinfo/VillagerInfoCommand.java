package simplexity.villagerinfo.commands.villagerinfo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import simplexity.villagerinfo.commands.util.SubCommand;
import simplexity.villagerinfo.commands.util.SubCommandMaps;
import simplexity.villagerinfo.configurations.locale.ServerMessage;
import simplexity.villagerinfo.util.Perm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VillagerInfoCommand implements TabExecutor {
    List<String> villagerInfoTabComplete = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(ServerMessage.NOT_A_PLAYER.getMessage());
            return false;
        }
        if (args.length < 1) {
            player.sendRichMessage(ServerMessage.NOT_ENOUGH_ARGUMENTS.getMessage());
            return false;
        }
        String commandArg = args[0];
        HashMap<String, SubCommand> subCommands = SubCommandMaps.getInstance().getVillagerInfoSubCommands();
        if (!subCommands.containsKey(commandArg)) {
            player.sendRichMessage(ServerMessage.SUBCOMMAND_DOES_NOT_EXIST.getMessage() + commandArg);
            return false;
        }
        subCommands.get(commandArg).execute(player, args);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2) {
            villagerInfoTabComplete.clear();
            HashMap<String, SubCommand> villInfoSubcommands = SubCommandMaps.getInstance().getVillagerInfoSubCommands();
            if (!sender.hasPermission(Perm.VILL_COMMAND_BASE.getPerm())) {
                return villagerInfoTabComplete;
            }
            villInfoSubcommands.forEach((string, subcommand) -> {
                String permission = subcommand.getPermission();
                if (sender.hasPermission(permission)) {
                    villagerInfoTabComplete.add(string);
                }
            });
            return villagerInfoTabComplete;
        }
        villagerInfoTabComplete.clear();
        HashMap<String, SubCommand> subCommands = SubCommandMaps.getInstance().getVillagerInfoSubCommands();
        if (!subCommands.containsKey(args[0])) {
            return villagerInfoTabComplete;
        }
        return subCommands.get(args[0]).subCommandTabCompletions(sender, args);
    }
}
