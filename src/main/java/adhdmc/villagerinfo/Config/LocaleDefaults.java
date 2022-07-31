package adhdmc.villagerinfo.Config;

import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

public class LocaleDefaults {

    public static void langConfigDefaults() throws IOException, InvalidConfigurationException {
        YamlConfiguration config = VillagerInfo.localeConfig.getlocaleConfig();
        config.addDefault("prefix", "&#3256a8&l[&#4dd5ffVillager Info&#3256a8&l]");
        config.addDefault("toggle-on", "&aVillager Info Toggled &nON");
        config.addDefault("toggle-off", "&cVillager Info Toggled &nOFF");
        config.addDefault("no-permission", "&cYou don't have permission to use this command!");
        config.addDefault("no-command", "&cNo subcommand by that name!");
        config.addDefault("config-reload", "&6VillagerInfo Config Reloaded!");
        config.addDefault("help-main", "&#4dd5ff• How to use Villager Info\n&7Shift-right-click a villager while toggle is on to have a villager's information displayed");
        config.addDefault("help-toggle", "&#4dd5ff•/vill toggle\n&7Toggles the ability to receive villager information on or off.");
        config.addDefault("help-reload", "&#4dd5ff•/vill reload\n&7Reloads the plugin, applies config values");
        config.addDefault("not-a-player", "&cSorry, you must be a player to use this command");
        config.addDefault("villager-profession", "&aPROFESSION:\n • <profession>");
        config.addDefault("villager-jobsite-msg", "&aJOB SITE:\n • <jobsitelocation>");
        config.addDefault("villager-last-worked-msg", "&aLAST WORKED AT WORKSTATION:\n <worktime>");
        config.addDefault("villager-num-restocks-msg", "&aRESTOCKS TODAY:\n <restockcount>");
        config.addDefault("villager-home-msg", "&aHOME:\n <homelocation>");
        config.addDefault("villager-slept-msg", "&aLAST SLEPT:\n <sleeptime>");
        config.addDefault("villager-inventory-msg", "&aVILLAGER INVENTORY:");
        config.addDefault("none-msg", "&7NONE");
        config.addDefault("never-msg", "&7NEVER");
        config.addDefault("empty-msg", "&7EMPTY");
        config.addDefault("player-reputation-msg", "&aPLAYER REPUTATION:\n<reputation>");
        config.addDefault("debug-message", "CONFIG HAS BEEN LOADED");
        config.addDefault("hour", "Hour, ");
        config.addDefault("hours", "Hours, ");
        config.addDefault("minute", "Minute, ");
        config.addDefault("minutes", "Minutes, ");
        config.addDefault("second-ago", "Second Ago");
        config.addDefault("seconds-ago", "Seconds Ago");
        config.addDefault("location-x", "<int>x, ");
        config.addDefault("location-y", "<int>y, ");
        config.addDefault("location-z", "<int>z");



    }
}
