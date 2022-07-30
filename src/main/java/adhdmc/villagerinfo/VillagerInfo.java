package adhdmc.villagerinfo;

import adhdmc.villagerinfo.Commands.CommandHandler;
import adhdmc.villagerinfo.Commands.SubCommands.HelpCommand;
import adhdmc.villagerinfo.Commands.SubCommands.ReloadCommand;
import adhdmc.villagerinfo.Commands.SubCommands.ToggleCommand;
import adhdmc.villagerinfo.Config.ConfigDefaults;
import adhdmc.villagerinfo.Config.LocaleConfig;
import adhdmc.villagerinfo.Config.LocaleDefaults;
import adhdmc.villagerinfo.VillagerHandling.VillagerHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class VillagerInfo extends JavaPlugin {
    public static VillagerInfo plugin;
    public static LocaleConfig localeConfig;
    public static boolean isPaper;
    YamlConfiguration langConfig = new YamlConfiguration();
    @Override
    public void onEnable(){
        plugin = this;
        localeConfig = new LocaleConfig(this);
        int pluginId = 13653; // bStats ID
        getServer().getPluginManager().registerEvents(new VillagerHandler(), this);
        this.getCommand("vill").setExecutor(new CommandHandler());
        this.saveDefaultConfig();
        ConfigDefaults.mainConfigDefaults();
        try {
            LocaleDefaults.langConfigDefaults();
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().severe("Could not save config");
        }
        getLogger().info( localeConfig.getlocaleConfig().getString("debug-message"));
        this.saveResource("locale.yml", false);

        paperCheck();
        registerCommands();
    }
    public void onDisable(){
        VillagerHandler.workstationShulker.forEach((uuid, shulker) -> {
            shulker.remove();
        });
        VillagerHandler.villagerPDC.forEach((uuid, persistentDataContainer) -> {
            persistentDataContainer.set(new NamespacedKey(VillagerInfo.plugin, "IsHighlighted"), PersistentDataType.INTEGER, 0);
        });
        VillagerHandler.workstationShulker.clear();
    }
    private void paperCheck() {
        try {
            Class.forName("com.destroystokyo.paper.entity.villager.Reputation");
            Class.forName("com.destroystokyo.paper.entity.villager.ReputationType");
            isPaper = true;
        } catch (ClassNotFoundException e) {
            isPaper = false;
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[VillagerInfo] This version does not run on Paper, some features may be disabled.");
        }
    }
/*    private void configDefaults() {
        this.saveDefaultConfig();
        getConfig().addDefault("Profession", true);
        getConfig().addDefault("Job Site", true);
        getConfig().addDefault("Last Worked", true);
        getConfig().addDefault("Bed Location", true);
        getConfig().addDefault("Last Slept", true);
        getConfig().addDefault("Villager Inventory Contents", true);
        getConfig().addDefault("Number of Restocks", true);
        getConfig().addDefault("Player Reputation", true);
        getConfig().addDefault("configReload", "&6VillagerInfo Config Reloaded!");
        getConfig().addDefault("soundToggle", true);
        getConfig().addDefault("Sound","BLOCK_AMETHYST_BLOCK_BREAK");
        getConfig().addDefault("Highlight Workstation", true);
        getConfig().addDefault("Length of time to highlight workstation", 10);
        getConfig().addDefault("prefix", "&#3256a8&l[&#4dd5ffVillager Info&#3256a8&l]");
        getConfig().addDefault("toggleOn", "&aVillager Info Toggled &nON");
        getConfig().addDefault("toggleOff", "&cVillager Info Toggled &nOFF");
        getConfig().addDefault("noPermission", "&cYou don't have permission to use this command!");
        getConfig().addDefault("noCommand", "&cNo subcommand by that name!");
        getConfig().addDefault("configReload", "&6VillagerInfo Config Reloaded!");
        getConfig().addDefault("helpMain", "&#4dd5ff• How to use Villager Info\n&7Shift-right-click a villager while toggle is on to have a villager's information displayed");
        getConfig().addDefault("helpToggle", "&#4dd5ff•/vill toggle\n&7Toggles the ability to receive villager information on or off.");
        getConfig().addDefault("helpReload", "&#4dd5ff•/vill reload\n&7Reloads the plugin, applies config values");
        getConfig().addDefault("notAPlayer", "&cSorry, you must be a player to use this command");
        getConfig().addDefault("VillagerProfession", "&aPROFESSION:\n");
        getConfig().addDefault("villagerJobsiteMsg", "&aJOB SITE:\n");
        getConfig().addDefault("villagerLastWorkedMsg", "&aLAST WORKED AT WORKSTATION:\n");
        getConfig().addDefault("villagerNumRestocksMsg", "&aRESTOCKS TODAY:\n");
        getConfig().addDefault("villagerHomeMsg", "&aHOME:\n");
        getConfig().addDefault("villagerSleptMsg", "&aLAST SLEPT:\n");
        getConfig().addDefault("villagerInventoryMsg", "&aVILLAGER INVENTORY:");
        getConfig().addDefault("villagerSeparatorMsg", "&b  • ");
        getConfig().addDefault("villagerNoneMsg", "&7NONE");
        getConfig().addDefault("villagerNeverMsg", "&7NEVER");
        getConfig().addDefault("villagerEmptyMsg", "&7EMPTY");
        getConfig().addDefault("playerReputationMsg", "&aPLAYER REPUTATION:\n");

    }*/
    private void registerCommands() {
        CommandHandler.subcommandList.put("help", new HelpCommand());
        CommandHandler.subcommandList.put("toggle", new ToggleCommand());
        CommandHandler.subcommandList.put("reload", new ReloadCommand());
    }
}
