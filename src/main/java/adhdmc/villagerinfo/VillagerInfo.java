package adhdmc.villagerinfo;

import adhdmc.villagerinfo.Commands.CommandHandler;
import adhdmc.villagerinfo.Commands.SubCommands.HelpCommand;
import adhdmc.villagerinfo.Commands.SubCommands.ReloadCommand;
import adhdmc.villagerinfo.Commands.SubCommands.ToggleCommand;
import adhdmc.villagerinfo.Config.ConfigValidator;
import adhdmc.villagerinfo.Config.Defaults;
import adhdmc.villagerinfo.Config.LocaleConfig;
import adhdmc.villagerinfo.VillagerHandling.HighlightHandling;
import adhdmc.villagerinfo.VillagerHandling.VillagerHandler;
import org.bstats.bukkit.Metrics;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class VillagerInfo extends JavaPlugin {
    public static VillagerInfo plugin;
    public static LocaleConfig localeConfig;
    //These are for the PDC stuff because I manage to not be able to keep '0' and '1' straight in my head, for which is true or false
    public static final String toggleInfoOn = "toggleInfoOn";
    public static final String toggleInfoOff = "toggleInfoOff";
    public static final String isCurrentlyHighlighted = "isCurrentlyHighlighted";
    public static final String isNotCurrentlyHighlighted = "isNotCurrentlyHighlighted";
    public static final double version = 2.0;
    //Permissions
    public static final String toggleCommandPermission = "villagerinfo.toggle";
    public static final String reloadCommandPermission = "villagerinfo.reload";
    public static final String usePermission = "villagerinfo.use";

    @Override
    public void onEnable() {
        plugin = this;
        localeConfig = new LocaleConfig(this);
        localeConfig.getlocaleConfig();
        Metrics metrics = new Metrics(this, 13653);
        getServer().getPluginManager().registerEvents(new VillagerHandler(), this);
        this.getCommand("vill").setExecutor(new CommandHandler());
        this.saveDefaultConfig();
        Defaults.localeDefaults();
        Defaults.configDefaults();
        localeConfig.saveConfig();
        ConfigValidator.configValidator();
        registerCommands();
    }

    public void onDisable() {
        HighlightHandling.workstationShulker.forEach((uuid, shulker) -> shulker.remove());
        HighlightHandling.villagerPDC.forEach((uuid, persistentDataContainer) -> persistentDataContainer.remove(new NamespacedKey(VillagerInfo.plugin, "highlightStatus")));
        HighlightHandling.workstationShulker.clear();
    }

    private void registerCommands() {
        CommandHandler.subcommandList.put("help", new HelpCommand());
        CommandHandler.subcommandList.put("toggle", new ToggleCommand());
        CommandHandler.subcommandList.put("reload", new ReloadCommand());
    }
}
