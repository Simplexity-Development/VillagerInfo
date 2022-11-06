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
    PURPUR_LOBOTOMIZED("<#05bff7><hover:show_text:'<aqua>Lobotomized: <grey><state>'>[<#c4fff7>Lobotomized</#c4fff7>]"),
    ZOMBIE_VILLAGER_CONVERSION_TIME("<#05bff7><hover:show_text:'<aqua>Conversion Time: <grey><time>'>[<#c4fff7>Time Until Converted</#c4fff7>]</hover>"),
    VILLAGER_AGE("<#05bff7><hover:show_text:'<aqua>Childhood Left: <grey><age>'>[<#c4fff7>Time Until Adult</#c4fff7>]"),
    VILLAGER_PROFESSION("<#05bff7><hover:show_text:'<aqua>Profession: <grey><profession>'>[<#c4fff7>Profession</#c4fff7>]"),
    VILLAGER_HEALTH("<#05bff7><hover:show_text:'<aqua>Health: <grey><current><aqua>/</aqua><total>'>[<#c4fff7>Health</#c4fff7>]"),
    VILLAGER_JOBSITE("<#05bff7><hover:show_text:'<aqua>POI: <grey><jobsitelocation>'>[<#c4fff7>Job Site</#c4fff7>]"),
    VILLAGER_LAST_WORKED("<#05bff7><hover:show_text:'<aqua>Last Worked: <grey><worktime>'>[<#c4fff7>Last Worked At Workstation</#c4fff7>]"),
    VILLAGER_RESTOCKS("<#05bff7><hover:show_text:'<aqua>Restocks: <grey><restockcount>'>[<#c4fff7>Restocks Today</#c4fff7>]"),
    VILLAGER_HOME("<#05bff7><hover:show_text:'<aqua>Bed: <grey><homelocation>'>[<#c4fff7>Home</#c4fff7>]"),
    VILLAGER_SLEPT("<#05bff7><hover:show_text:'<aqua>Last Slept: <grey><sleeptime>'>[<#c4fff7>Last Slept</#c4fff7>]"),
    VILLAGER_INVENTORY("<#05bff7><hover:show_text:'<aqua>Inventory: <grey><contents>'>[<#c4fff7>Villager Inventory</#c4fff7>]"),
    INVENTORY_CONTENTS("\n • <item> (<amount>)"),
    PLAYER_REPUTATION("<#05bff7><hover:show_text:'<#05bff7>[<#c4fff7>Player Reputation</#c4fff7>]'><reputation>"),

    // Fillers
    TRUE("<grey>True"), FALSE("<grey>False"),
    NONE("<grey>None"), NEVER("<grey>Never"), EMPTY("\n<grey>Empty"),
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
        PREFIX.setMessage(locale.getString("prefix"));
        TOGGLE_ON.setMessage(locale.getString("toggle-on"));
        TOGGLE_OFF.setMessage(locale.getString("toggle-off"));
        NO_PERMISSION.setMessage(locale.getString("no-permission"));
        // Commands
        NO_COMMAND.setMessage(locale.getString("no-command"));
        CONFIG_RELOADED.setMessage(locale.getString("config-reloaded"));
        HELP_MAIN.setMessage(locale.getString("help-main"));
        HELP_TOGGLE.setMessage(locale.getString("help-toggle"));
        HELP_RELOAD.setMessage(locale.getString("help-reload"));
        NOT_A_PLAYER.setMessage(locale.getString("not-a-player"));
        // Villager Info
        PURPUR_LOBOTOMIZED.setMessage(locale.getString("purpur-lobotomized"));
        ZOMBIE_VILLAGER_CONVERSION_TIME.setMessage(locale.getString("zombie-villager-conversion-time"));
        VILLAGER_AGE.setMessage(locale.getString("villager-age"));
        VILLAGER_HEALTH.setMessage(locale.getString("villager-health"));
        VILLAGER_PROFESSION.setMessage(locale.getString("villager-profession"));
        VILLAGER_JOBSITE.setMessage(locale.getString("villager-jobsite-msg"));
        VILLAGER_LAST_WORKED.setMessage(locale.getString("villager-last-worked-msg"));
        VILLAGER_RESTOCKS.setMessage(locale.getString("villager-num-restocks-msg"));
        VILLAGER_HOME.setMessage(locale.getString("villager-home-msg"));
        VILLAGER_SLEPT.setMessage(locale.getString("villager-slept-msg"));
        VILLAGER_INVENTORY.setMessage(locale.getString("villager-inventory-msg"));
        INVENTORY_CONTENTS.setMessage(locale.getString("inventory-contents-msg"));
        PLAYER_REPUTATION.setMessage(locale.getString("player-reputation-msg"));
        // Fillers
        TRUE.setMessage(locale.getString("true-msg"));
        FALSE.setMessage(locale.getString("false-msg"));
        NONE.setMessage(locale.getString("none-msg"));
        NEVER.setMessage(locale.getString("never-msg"));
        EMPTY.setMessage(locale.getString("empty-msg"));
        NO_INFORMATION.setMessage(locale.getString("no-information"));
        // Time
        HOUR.setMessage(locale.getString("hour"));
        MINUTE.setMessage(locale.getString("minute"));
        SECOND.setMessage(locale.getString("second"));
        AGO.setMessage(locale.getString("ago"));

        // Location
        LOCATION_X.setMessage(locale.getString("location-x"));
        LOCATION_Y.setMessage(locale.getString("location-y"));
        LOCATION_Z.setMessage(locale.getString("location-z"));
    }
}
