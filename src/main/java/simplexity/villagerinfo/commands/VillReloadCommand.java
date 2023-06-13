package simplexity.villagerinfo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import simplexity.villagerinfo.VillagerInfo;
import simplexity.villagerinfo.configurations.locale.LocaleConfig;
import simplexity.villagerinfo.configurations.locale.ServerMessage;
import simplexity.villagerinfo.util.Resolvers;

public class VillReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        VillagerInfo.getInstance().reloadConfig();
        VillagerInfo.getInstance().reloadVillInfoConfigs();
        LocaleConfig.getInstance().reloadLocale();
        sender.sendMessage(Resolvers.getInstance().prefixResolver(ServerMessage.CONFIG_AND_LOCALE_RELOADED.getMessage()));
        return true;
    }
}
