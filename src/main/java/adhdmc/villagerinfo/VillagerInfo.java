package adhdmc.villagerinfo;

import adhdmc.villagerinfo.Commands.CommandHandler;
import adhdmc.villagerinfo.Commands.SubCommands.HelpCommand;
import adhdmc.villagerinfo.Commands.SubCommands.ReloadCommand;
import adhdmc.villagerinfo.Commands.SubCommands.ToggleCommand;
import adhdmc.villagerinfo.Config.ConfigDefaults;
import adhdmc.villagerinfo.Config.LocaleConfig;
import adhdmc.villagerinfo.Config.LocaleDefaults;
import adhdmc.villagerinfo.VillagerHandling.VillagerHandler;
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
        localeConfig.saveConfig();
        ConfigDefaults.mainConfigDefaults();
        try {
            LocaleDefaults.langConfigDefaults();
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().severe("Could not save config");
        }
        getLogger().info( localeConfig.getlocaleConfig().getString("debug-message"));
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

    private void registerCommands() {
        CommandHandler.subcommandList.put("help", new HelpCommand());
        CommandHandler.subcommandList.put("toggle", new ToggleCommand());
        CommandHandler.subcommandList.put("reload", new ReloadCommand());
    }
}
