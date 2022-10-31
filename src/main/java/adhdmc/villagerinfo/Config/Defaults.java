package adhdmc.villagerinfo.Config;

import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.configuration.file.FileConfiguration;

public class Defaults {

    public static void localeDefaults() {
        FileConfiguration locale = VillagerInfo.getLocaleConfig().getlocaleConfig();
        locale.addDefault("prefix", "<#3256a8><bold>[</bold><#4dd5ff>Villager Info<#3256a8><bold>]<reset>");
        locale.addDefault("toggle-on", "<green> Villager Info Toggled <u>ON");
        locale.addDefault("toggle-off", "<red> Villager Info Toggled <u>OFF");
        locale.addDefault("no-permission", "<red>You don't have permission to use this command!");
        locale.addDefault("no-command", "<red>No subcommand by that name!");
        locale.addDefault("config-reloaded", "<gold>VillagerInfo Config Reloaded!");
        locale.addDefault("help-main", "<#4dd5ff> • How to use Villager Info\n<grey>Shift-right-click a villager while toggle is on to have a villager's information displayed");
        locale.addDefault("help-toggle", "<#4dd5ff> • /vill toggle\n<grey>Toggles the ability to receive villager information on or off.");
        locale.addDefault("help-reload", "<#4dd5ff> • /vill reload\n<grey>Reloads the plugin, applies config values");
        locale.addDefault("not-a-player", "<red>Sorry, you must be a player to use this command");
        locale.addDefault("zombie-villager-conversion-time", "<green>TIME UNTIL CONVERTED: \n<aqua> • <time>");
        locale.addDefault("villager-age", "<green>TIME UNTIL ADULT: \n<aqua> • <age>");
        locale.addDefault("villager-profession", "<green>PROFESSION:\n<aqua> • <profession>");
        locale.addDefault("villager-jobsite-msg", "<green>JOB SITE:\n<aqua> • <jobsitelocation>");
        locale.addDefault("villager-last-worked-msg", "<green>LAST WORKED AT WORKSTATION:\n<aqua> • <worktime>");
        locale.addDefault("villager-num-restocks-msg", "<green>RESTOCKS TODAY:\n<aqua> • <restockcount>");
        locale.addDefault("villager-home-msg", "<green>HOME:\n<aqua> • <homelocation>");
        locale.addDefault("villager-slept-msg", "<green>LAST SLEPT:\n<aqua> • <sleeptime>");
        locale.addDefault("villager-inventory-msg", "<green>VILLAGER INVENTORY:<aqua> <contents>");
        locale.addDefault("inventory-contents-msg", "\n • <item> (<amount>)");
        locale.addDefault("player-reputation-msg", "<green>PLAYER REPUTATION:\n<reputation>");
        locale.addDefault("none-msg", "<grey>NONE");
        locale.addDefault("never-msg", "<grey>NEVER");
        locale.addDefault("empty-msg", "\n • <grey>EMPTY");
        locale.addDefault("no-information", "<grey>No information to display on this villager");
        locale.addDefault("hour", "h, ");
        locale.addDefault("minute", "m, ");
        locale.addDefault("second", "s");
        locale.addDefault("ago", " Ago");
        locale.addDefault("location-x", "<int>x, ");
        locale.addDefault("location-y", "<int>y, ");
        locale.addDefault("location-z", "<int>z");
    }

    public static void configDefaults() {
        FileConfiguration config = VillagerInfo.getInstance().getConfig();
        config.addDefault("baby-age", true);
        config.addDefault("zombie-conversion", true);
        config.addDefault("profession", true);
        config.addDefault("job-site", true);
        config.addDefault("last-worked", true);
        config.addDefault("bed-location", true);
        config.addDefault("last-slept", true);
        config.addDefault("inventory", true);
        config.addDefault("restocks", true);
        config.addDefault("reputation", true);
        config.addDefault("highlight-workstation", true);
        config.addDefault("sound-toggle", true);
        config.addDefault("sound", "BLOCK_AMETHYST_BLOCK_BREAK");
        config.addDefault("sound-volume", 0.5);
        config.addDefault("sound-pitch", 1.5);
        config.addDefault("highlight-time", 10);
    }

}
