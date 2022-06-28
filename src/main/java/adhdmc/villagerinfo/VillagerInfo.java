package adhdmc.villagerinfo;

import adhdmc.villagerinfo.MiscHandling.MessageHandler;
import adhdmc.villagerinfo.MiscHandling.Metrics;
import adhdmc.villagerinfo.VillagerHandling.VillagerHandler;
import adhdmc.villagerinfo.Commands.CommandHandler;
import adhdmc.villagerinfo.Commands.SubCommands.HelpCommand;
import adhdmc.villagerinfo.Commands.SubCommands.ReloadCommand;
import adhdmc.villagerinfo.Commands.SubCommands.ToggleCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public final class VillagerInfo extends JavaPlugin {
    public static VillagerInfo plugin;
    public static boolean isPaper;
    @Override
    public void onEnable(){
        plugin = this;
        int pluginId = 13653; // bStats ID
        Metrics metrics = new Metrics(this, pluginId);
        getServer().getPluginManager().registerEvents(new VillagerHandler(), this);
        this.getCommand("vill").setExecutor(new CommandHandler());
        configDefaults();
        MessageHandler.loadConfigMsgs();
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
            Class.forName("com.destroystokyo.paper.VersionHistoryManager$VersionData");
            isPaper = true;
        } catch (ClassNotFoundException e) {
            isPaper = false;
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[VillagerInfo] This version does not run on Paper, some features may be disabled.");
        }
    }

    private void configDefaults() {
        this.saveDefaultConfig();
        getConfig().addDefault("Profession", true);
        getConfig().addDefault("Job Site", true);
        getConfig().addDefault("Last Worked", true);
        getConfig().addDefault("Bed Location", true);
        getConfig().addDefault("Last Slept", true);
        getConfig().addDefault("Villager Inventory Contents", true);
        getConfig().addDefault("Number of Restocks", true);
        getConfig().addDefault("Player Reputation", true);
        getConfig().addDefault("Prefix", "&#3256a8&l[&#4dd5ffVillager Info&#3256a8&l]");
        getConfig().addDefault("Toggle On", "&aVillager Info Toggled &nON");
        getConfig().addDefault("Toggle Off", "&cVillager Info Toggled &nOFF");
        getConfig().addDefault("No Permission", "&cYou don't have permission to use this command!");
        getConfig().addDefault("No Command", "&cNo subcommand by that name!");
        getConfig().addDefault("Config Reload", "&6VillagerInfo Config Reloaded!");
        getConfig().addDefault("Sound Toggle", true);
        getConfig().addDefault("Sound","BLOCK_AMETHYST_BLOCK_BREAK");
        getConfig().addDefault("Highlight Workstation", true);
        getConfig().addDefault("Length of time to highlight workstation", 10);
    }
    private void registerCommands() {
        CommandHandler.subcommandList.put("help", new HelpCommand());
        CommandHandler.subcommandList.put("toggle", new ToggleCommand());
        CommandHandler.subcommandList.put("reload", new ReloadCommand());
    }

}