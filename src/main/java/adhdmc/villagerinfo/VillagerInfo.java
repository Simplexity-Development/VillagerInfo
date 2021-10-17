package adhdmc.villagerinfo;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class VillagerInfo extends JavaPlugin {
    public static VillagerInfo plugin;
    @Override
    public void onEnable(){
        plugin = this;
        getServer().getPluginManager().registerEvents(new VillagerHandler(), this);
        Objects.requireNonNull(this.getCommand("vill")).setExecutor(new CommandHandler());
        this.saveDefaultConfig();
        getConfig().addDefault("Profession", true);
        getConfig().addDefault("Job Site", true);
        getConfig().addDefault("Last Worked", true);
        getConfig().addDefault("Number of Restocks", true);
        getConfig().addDefault("Bed Location", true);
        getConfig().addDefault("Last Slept", true);
        getConfig().addDefault("Villager Inventory Contents", true);
        getConfig().addDefault("Prefix", "&#3256a8&l[&#4dd5ffVillager Info&#3256a8&l]");
        getConfig().addDefault("Toggle On", "&aVillager Info Toggled &nON");
        getConfig().addDefault("Toggle Off", "&cVillager Info Toggled &nOFF");
        getConfig().addDefault("No Permission", "&cYou don't have permission to use this command!");
        getConfig().addDefault("No Command", "&cNo subcommand by that name!");
        getConfig().addDefault("Config Reload", "&6VillagerInfo Config Reloaded!");
        MessageHandler.loadConfigMsgs();
    }
}