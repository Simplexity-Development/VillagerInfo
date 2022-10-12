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
    VILLAGER_AGE("<green>TIME UNTIL ADULT: \n<aqua> • <age>"),
    VILLAGER_PROFESSION("<green>PROFESSION:\n<aqua> • <profession>"),
    VILLAGER_JOBSITE("<green>JOB SITE:\n<aqua> • <jobsitelocation>"),
    VILLAGER_LAST_WORKED("<green>LAST WORKED AT WORKSTATION:\n<aqua> • <worktime>"),
    VILLAGER_RESTOCKS("<green>RESTOCKS TODAY:\n<aqua> • <restockcount>"),
    VILLAGER_HOME("<green>HOME:\n<aqua> • <homelocation>"),
    VILLAGER_SLEPT("<green>LAST SLEPT:\n<aqua> • <sleeptime>"),
    VILLAGER_INVENTORY("<green>VILLAGER INVENTORY:<aqua> <contents>"),
    INVENTORY_CONTENTS("\n • <item> (<amount>)"),
    PLAYER_REPUTATION("<green>PLAYER REPUTATION:\n<reputation>"),

    // Fillers
    NONE("<grey>NONE"), NEVER("<grey>NEVER"), EMPTY("\n • <grey>EMPTY"),

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
        VILLAGER_AGE.setMessage(locale.getString("villager-age", "<green>TIME UNTIL ADULT: \n<aqua> • <age>"));
        VILLAGER_PROFESSION.setMessage(locale.getString("villager-profession", "<green>PROFESSION:\n<aqua> • <profession>"));
        VILLAGER_JOBSITE.setMessage(locale.getString("villager-jobsite-msg", "<green>JOB SITE:\n<aqua> • <jobsitelocation>"));
        VILLAGER_LAST_WORKED.setMessage(locale.getString("villager-last-worked-msg", "<green>LAST WORKED AT WORKSTATION:\n<aqua> • <worktime>"));
        VILLAGER_RESTOCKS.setMessage(locale.getString("villager-num-restocks-msg", "<green>RESTOCKS TODAY:\n<aqua> • <restockcount>"));
        VILLAGER_HOME.setMessage(locale.getString("villager-home-msg", "<green>HOME:\n<aqua> • <homelocation>"));
        VILLAGER_SLEPT.setMessage(locale.getString("villager-slept-msg", "<green>LAST SLEPT:\n<aqua> • <sleeptime>"));
        VILLAGER_INVENTORY.setMessage(locale.getString("villager-inventory-msg", "<green>VILLAGER INVENTORY:<aqua> <contents>"));
        INVENTORY_CONTENTS.setMessage(locale.getString("inventory-contents-msg", "\n • <item> (<amount>)"));
        PLAYER_REPUTATION.setMessage(locale.getString("player-reputation-msg", "<green>PLAYER REPUTATION:\n<reputation>"));
        // Fillers
        NONE.setMessage(locale.getString("none-msg", "<grey>NONE"));
        NEVER.setMessage(locale.getString("never-msg", "<grey>NEVER"));
        EMPTY.setMessage(locale.getString("empty-msg", "\n • <grey>EMPTY"));
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
