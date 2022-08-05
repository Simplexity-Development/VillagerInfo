package adhdmc.villagerinfo.Config;

import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

public class LocaleDefaults {

    public static void langConfigDefaults() throws IOException, InvalidConfigurationException {
        YamlConfiguration config = VillagerInfo.localeConfig.getlocaleConfig();
        config.addDefault("prefix", "<#3256a8><bold>[</bold><#4dd5ff>Villager Info<#3256a8><bold>]<reset>");
        config.addDefault("toggle-on", "<green>Villager Info Toggled <u>ON");
        config.addDefault("toggle-off", "<red>Villager Info Toggled <u>OFF");
        config.addDefault("no-permission", "<red>You don't have permission to use this command!");
        config.addDefault("no-command", "<red>No subcommand by that name!");
        config.addDefault("config-reload", "<gold>VillagerInfo Config Reloaded!");
        config.addDefault("help-main", "<#4dd5ff> • How to use Villager Info\n<grey>Shift-right-click a villager while toggle is on to have a villager's information displayed");
        config.addDefault("help-toggle", "<#4dd5ff> • /vill toggle\n<grey>Toggles the ability to receive villager information on or off.");
        config.addDefault("help-reload", "<#4dd5ff> • /vill reload\n<grey>Reloads the plugin, applies config values");
        config.addDefault("not-a-player", "<red>Sorry, you must be a player to use this command");
        config.addDefault("villager-profession", "<green>PROFESSION:\n • <profession>");
        config.addDefault("villager-jobsite-msg", "<green>JOB SITE:\n • <jobsitelocation>");
        config.addDefault("villager-last-worked-msg", "<green>LAST WORKED AT WORKSTATION:\n <worktime>");
        config.addDefault("villager-num-restocks-msg", "<green>RESTOCKS TODAY:\n <restockcount>");
        config.addDefault("villager-home-msg", "<green>HOME:\n <homelocation>");
        config.addDefault("villager-slept-msg", "<green>LAST SLEPT:\n <sleeptime>");
        config.addDefault("villager-inventory-msg", "<green>VILLAGER INVENTORY: <contents>");
        config.addDefault("none-msg", "<grey> • NONE");
        config.addDefault("never-msg", "<grey> • NEVER");
        config.addDefault("empty-msg", "<grey>\n • EMPTY");
        config.addDefault("player-reputation-msg", "<green>PLAYER REPUTATION:\n<reputation>");
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
