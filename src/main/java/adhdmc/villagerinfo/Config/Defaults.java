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
        locale.addDefault("zombie-villager-conversion-time", "<green><hover:show_text:'<grey><time>'>[TIME UNTIL CONVERTED]</hover>");
        locale.addDefault("villager-age", "<green><hover:show_text:'<grey><age>'>[TIME UNTIL ADULT]");
        locale.addDefault("purpur-lobotomized","<green><hover:show_text:'<grey><state>'>[LOBOTOMIZED]");
        locale.addDefault("villager-health", "<green><hover:show_text:'<grey><current><aqua>/</aqua><total>'>[HEALTH]");
        locale.addDefault("villager-profession", "<green><hover:show_text:'<grey><profession>'>[PROFESSION]");
        locale.addDefault("villager-jobsite-msg", "<green><hover:show_text:'<grey><jobsitelocation>'>[JOB SITE]");
        locale.addDefault("villager-last-worked-msg", "<green><hover:show_text:'<grey><worktime>'>[LAST WORKED AT WORKSTATION]");
        locale.addDefault("villager-num-restocks-msg", "<green><hover:show_text:'<grey><restockcount>'>[RESTOCKS TODAY]");
        locale.addDefault("villager-home-msg", "<green><hover:show_text:'<grey><homelocation>'>[HOME]");
        locale.addDefault("villager-slept-msg", "<green><hover:show_text:'<grey><sleeptime>'>[LAST SLEPT]");
        locale.addDefault("villager-inventory-msg", "<green><hover:show_text:'<grey><contents>'>[VILLAGER INVENTORY]");
        locale.addDefault("inventory-contents-msg", "\n • <item> (<amount>)");
        locale.addDefault("player-reputation-msg", "<green>PLAYER REPUTATION:\n<reputation>");
        locale.addDefault("true-msg", "<grey>TRUE");
        locale.addDefault("false-msg", "<grey>FALSE");
        locale.addDefault("none-msg", "<grey>NONE");
        locale.addDefault("never-msg", "<grey>NEVER");
        locale.addDefault("empty-msg", "<grey>EMPTY");
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
        config.addDefault("hover-messages", true);
        config.addDefault("purpur-lobotomized", false);
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
