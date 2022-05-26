package adhdmc.villagerinfo;

import adhdmc.villagerinfo.commands.CommandHandler;
import adhdmc.villagerinfo.commands.subcommands.HelpCommand;
import adhdmc.villagerinfo.commands.subcommands.ReloadCommand;
import adhdmc.villagerinfo.commands.subcommands.ToggleCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class VillagerInfo extends JavaPlugin {
    public static VillagerInfo plugin;
    public static boolean isPaper;
    @Override
    public void onEnable(){
        plugin = this;
        int pluginId = 13653; // bStats ID
        Metrics metrics = new Metrics(this, pluginId);
        getServer().getPluginManager().registerEvents(new VillagerHandler(), this);
        Objects.requireNonNull(this.getCommand("vill")).setExecutor(new CommandHandler());
        configDefaults();
        MessageHandler.loadConfigMsgs();
        paperCheck();
        registerCommands();
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
    }
    private void registerCommands() {
        CommandHandler.subcommandList.put("help", new HelpCommand());
        CommandHandler.subcommandList.put("toggle", new ToggleCommand());
        CommandHandler.subcommandList.put("reload", new ReloadCommand());
    }
}