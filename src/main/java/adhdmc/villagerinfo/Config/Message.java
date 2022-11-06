package adhdmc.villagerinfo.Config;

import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.configuration.file.FileConfiguration;

public enum Message {
    // General
    PREFIX("<#3256a8><bold>[</bold><#4dd5ff>Villager Info<#3256a8><bold>]<reset>"),
    TOGGLE_ON("<green> Villager Info Toggled <u>ON"),
    TOGGLE_OFF("<red> Villager Info Toggled <u>OFF"),
    NO_PERMISSION("<red>You don't have permission to use this command!"),
    NO_COMMAND("<red>No command by that name!"),
    CONFIG_RELOADED("<gold>VillagerInfo Config Reloaded!"),
    HELP_MAIN("<#4dd5ff> • How to use Villager Info\n<grey>Shift-right-click a villager while toggle is on to have a villager's information displayed"),
    HELP_TOGGLE("<#4dd5ff> • /vill toggle\n<grey>Toggles the ability to receive villager information on or off."),
    HELP_RELOAD("<#4dd5ff> • /vill reload\n<grey>Reloads the plugin, applies config values"),
    NOT_A_PLAYER("<red>Sorry, you must be a player to use this command"),

    // Villager Info
    PURPUR_LOBOTOMIZED("<green><hover:show_text:'<grey><state>'>[LOBOTOMIZED]"),
    ZOMBIE_VILLAGER_CONVERSION_TIME("<green><hover:show_text:'<grey><time>'>[TIME UNTIL CONVERTED]</hover>"),
    VILLAGER_AGE("<green><hover:show_text:'<grey><age>'>[TIME UNTIL ADULT]"),
    VILLAGER_PROFESSION("<green><hover:show_text:'<grey><profession>'>[PROFESSION]"),
    VILLAGER_HEALTH("<green><hover:show_text:'<grey><current><aqua>/</aqua><total>'>[HEALTH]"),
    VILLAGER_JOBSITE("<green><hover:show_text:'<grey><jobsitelocation>'>[JOB SITE]"),
    VILLAGER_LAST_WORKED("<green><hover:show_text:'<grey><worktime>'>[LAST WORKED AT WORKSTATION]"),
    VILLAGER_RESTOCKS("<green><hover:show_text:'<grey><restockcount>'>[RESTOCKS TODAY]"),
    VILLAGER_HOME("<green><hover:show_text:'<grey><homelocation>'>[HOME]"),
    VILLAGER_SLEPT("<green><hover:show_text:'<grey><sleeptime>'>[LAST SLEPT]"),
    VILLAGER_INVENTORY("<green><hover:show_text:'<grey><contents>'>[VILLAGER INVENTORY]"),
    INVENTORY_CONTENTS("\n • <item> (<amount>)"),
    PLAYER_REPUTATION("<green>PLAYER REPUTATION:\n<reputation>"),

    // Fillers
    TRUE("<grey>TRUE"), FALSE("<grey>FALSE"),
    NONE("<grey>NONE"), NEVER("<grey>NEVER"), EMPTY("<grey>EMPTY"),
    NO_INFORMATION("<grey>No information to display on this villager"),

    // Time
    HOUR("h, "), MINUTE("m, "), SECOND("s"), AGO(" Ago"),

    // Location
    LOCATION_X("<int>x, "), LOCATION_Y("<int>y, "), LOCATION_Z("<int>z");

    String message;
    Message(String message) {
        this.message = message;
    }
    public String getMessage() { return this.message; }
    private void setMessage(String message) { this.message = message; }


    public static void reloadLocale() {
        FileConfiguration locale = VillagerInfo.getLocaleConfig().getlocaleConfig();
        // General
        PREFIX.setMessage(locale.getString("prefix", "<#3256a8><bold>[</bold><#4dd5ff>Villager Info<#3256a8><bold>]<reset>"));
        TOGGLE_ON.setMessage(locale.getString("toggle-on", "<green> Villager Info Toggled <u>ON"));
        TOGGLE_OFF.setMessage(locale.getString("toggle-off", "<red> Villager Info Toggled <u>OFF"));
        NO_PERMISSION.setMessage(locale.getString("no-permission", "<red>You don't have permission to use this command!"));
        // Commands
        NO_COMMAND.setMessage(locale.getString("no-command", "<red>No subcommand by that name!"));
        CONFIG_RELOADED.setMessage(locale.getString("config-reloaded", "<gold>VillagerInfo Config Reloaded!"));
        HELP_MAIN.setMessage(locale.getString("help-main", "<#4dd5ff> • How to use Villager Info\n<grey>Shift-right-click a villager while toggle is on to have a villager's information displayed"));
        HELP_TOGGLE.setMessage(locale.getString("help-toggle", "<#4dd5ff> • /vill toggle\n<grey>Toggles the ability to receive villager information on or off."));
        HELP_RELOAD.setMessage(locale.getString("help-reload", "<#4dd5ff> • /vill reload\n<grey>Reloads the plugin, applies config values"));
        NOT_A_PLAYER.setMessage(locale.getString("not-a-player", "<red>Sorry, you must be a player to use this command"));
        // Villager Info
        PURPUR_LOBOTOMIZED.setMessage(locale.getString("purpur-lobotomized","<green><hover:show_text:'<grey><state>'>[LOBOTOMIZED]"));
        ZOMBIE_VILLAGER_CONVERSION_TIME.setMessage(locale.getString("zombie-villager-conversion-time", "<green><hover:show_text:'<grey><time>'>[TIME UNTIL CONVERTED]</hover>"));
        VILLAGER_AGE.setMessage(locale.getString("villager-age", "<green><hover:show_text:'<grey><age>'>[TIME UNTIL ADULT]"));
        VILLAGER_HEALTH.setMessage(locale.getString("villager-health", "<green><hover:show_text:'<grey><current><aqua>/</aqua><total>'>[HEALTH]"));
        VILLAGER_PROFESSION.setMessage(locale.getString("villager-profession", "<green><hover:show_text:'<grey><profession>'>[PROFESSION]"));
        VILLAGER_JOBSITE.setMessage(locale.getString("villager-jobsite-msg", "<green><hover:show_text:'<grey><jobsitelocation>'>[JOB SITE]"));
        VILLAGER_LAST_WORKED.setMessage(locale.getString("villager-last-worked-msg", "<green><hover:show_text:'<grey><worktime>'>[LAST WORKED AT WORKSTATION]"));
        VILLAGER_RESTOCKS.setMessage(locale.getString("villager-num-restocks-msg", "<green><hover:show_text:'<grey><restockcount>'>[RESTOCKS TODAY]"));
        VILLAGER_HOME.setMessage(locale.getString("villager-home-msg", "<green><hover:show_text:'<grey><homelocation>'>[HOME]"));
        VILLAGER_SLEPT.setMessage(locale.getString("villager-slept-msg", "<green><hover:show_text:'<grey><sleeptime>'>[LAST SLEPT]"));
        VILLAGER_INVENTORY.setMessage(locale.getString("villager-inventory-msg", "<green><hover:show_text:'<grey><contents>'>[VILLAGER INVENTORY]"));
        INVENTORY_CONTENTS.setMessage(locale.getString("inventory-contents-msg", "\n • <item> (<amount>)"));
        PLAYER_REPUTATION.setMessage(locale.getString("player-reputation-msg", "<green>PLAYER REPUTATION:\n<reputation>"));
        // Fillers
        TRUE.setMessage(locale.getString("true-msg", "<grey>TRUE"));
        FALSE.setMessage(locale.getString("false-msg", "<grey>FALSE"));
        NONE.setMessage(locale.getString("none-msg", "<grey>NONE"));
        NEVER.setMessage(locale.getString("never-msg", "<grey>NEVER"));
        EMPTY.setMessage(locale.getString("empty-msg", "<grey>EMPTY"));
        NO_INFORMATION.setMessage(locale.getString("no-information", "<grey>No information to display on this villager"));
        // Time
        HOUR.setMessage(locale.getString("hour", "h, "));
        MINUTE.setMessage(locale.getString("minute", "m, "));
        SECOND.setMessage(locale.getString("second", "s"));
        AGO.setMessage(locale.getString("ago", " Ago"));

        // Location
        LOCATION_X.setMessage(locale.getString("location-x", "<int>x, "));
        LOCATION_Y.setMessage(locale.getString("location-y", "<int>y, "));
        LOCATION_Z.setMessage(locale.getString("location-z", "<int>z"));
    }
}
