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
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bstats.bukkit.Metrics;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class VillagerInfo extends JavaPlugin {
    private static VillagerInfo instance;
    private static MiniMessage miniMessage = MiniMessage.miniMessage();
    private static LocaleConfig localeConfig;
    //Permissions
    private static NamespacedKey infoEnabledKey;

    @Override
    public void onEnable() {
        instance = this;
        try {
            Class.forName("net.kyori.adventure.text.minimessage.MiniMessage");
            Class.forName("com.destroystokyo.paper.entity.villager.Reputation");
        } catch (ClassNotFoundException e) {
            this.getLogger().severe("VillagerInfo relies on methods in classes not present on your server. Disabling plugin");
            this.getServer().getPluginManager().disablePlugin(this);
        }
        infoEnabledKey = new NamespacedKey(VillagerInfo.instance, "infoEnabled");
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
        HighlightHandling.villagerPDC.forEach((uuid, persistentDataContainer) -> persistentDataContainer.remove(new NamespacedKey(VillagerInfo.instance, "highlightStatus")));
        HighlightHandling.workstationShulker.clear();
    }

    private void registerCommands() {
        CommandHandler.subcommandList.put("help", new HelpCommand());
        CommandHandler.subcommandList.put("toggle", new ToggleCommand());
        CommandHandler.subcommandList.put("reload", new ReloadCommand());
    }

    public static NamespacedKey getInfoEnabledKey() {
        return infoEnabledKey;
    }
    public static MiniMessage getMiniMessage(){
        return miniMessage;
    }
    public static VillagerInfo getInstance(){
        return instance;
    }
    public static LocaleConfig getLocaleConfig(){
        return localeConfig;
    }
}
