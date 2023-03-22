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
        locale.addDefault("help-main", "<#4dd5ff> • How to use Villager Info\n<grey>Crouch-right-click a villager while toggle is on to have a villager's information displayed");
        locale.addDefault("help-toggle", "<#4dd5ff> • /vill toggle\n<grey>Toggles the ability to receive villager information on or off.");
        locale.addDefault("help-reload", "<#4dd5ff> • /vill reload\n<grey>Reloads the plugin, applies config values");
        locale.addDefault("not-a-player", "<red>Sorry, you must be a player to use this command");
        locale.addDefault("purpur-lobotomized","<#05bff7><hover:show_text:'<aqua>Lobotomized: <grey><state>'>[<#c4fff7>Lobotomized</#c4fff7>]");
        locale.addDefault("zombie-villager-conversion-time", "<#05bff7><hover:show_text:'<aqua>Conversion Time: <grey><time>'>[<#c4fff7>Time Until Converted</#c4fff7>]</hover>");
        locale.addDefault("villager-age", "<#05bff7><hover:show_text:'<aqua>Childhood Left: <grey><age>'>[<#c4fff7>Time Until Adult</#c4fff7>]");
        locale.addDefault("villager-health", "<#05bff7><hover:show_text:'<aqua>Health: <grey><current><aqua>/</aqua><total>'>[<#c4fff7>Health</#c4fff7>]");
        locale.addDefault("villager-profession", "<#05bff7><hover:show_text:'<aqua>Profession: <grey><profession>'>[<#c4fff7>Profession</#c4fff7>]");
        locale.addDefault("villager-jobsite-msg", "<#05bff7><hover:show_text:'<aqua>POI: <grey><jobsitelocation>'>[<#c4fff7>Job Site</#c4fff7>]");
        locale.addDefault("villager-last-worked-msg", "<#05bff7><hover:show_text:'<aqua>Last Worked: <grey><worktime>'>[<#c4fff7>Last Worked At Workstation</#c4fff7>]");
        locale.addDefault("villager-num-restocks-msg", "<#05bff7><hover:show_text:'<aqua>Restocks: <grey><restockcount>'>[<#c4fff7>Restocks Today</#c4fff7>]");
        locale.addDefault("villager-home-msg", "<#05bff7><hover:show_text:'<aqua>Bed: <grey><homelocation>'>[<#c4fff7>Home</#c4fff7>]");
        locale.addDefault("villager-slept-msg", "<#05bff7><hover:show_text:'<aqua>Last Slept: <grey><sleeptime>'>[<#c4fff7>Last Slept</#c4fff7>]");
        locale.addDefault("villager-inventory-msg", "<#05bff7><hover:show_text:'<aqua>Inventory: <grey><contents>'>[<#c4fff7>Villager Inventory</#c4fff7>]");
        locale.addDefault("inventory-contents-msg", "\n • <item> (<amount>)");
        locale.addDefault("player-reputation-msg", "<#05bff7><hover:show_text:'<#05bff7>[<#c4fff7>Player Reputation</#c4fff7>]'><reputation>");
        locale.addDefault("true-msg", "<grey>True");
        locale.addDefault("false-msg", "<grey>False");
        locale.addDefault("none-msg", "<grey>None");
        locale.addDefault("never-msg", "<grey>Never");
        locale.addDefault("empty-msg", "\n<grey>Empty");
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
