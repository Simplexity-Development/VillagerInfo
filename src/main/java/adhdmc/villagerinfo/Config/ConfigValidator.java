package adhdmc.villagerinfo.Config;

import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ConfigValidator {
    public enum Message {
        PREFIX, TOGGLE_ON, TOGGLE_OFF, NO_PERMISSION,
        NO_COMMAND, CONFIG_RELOADED, HELP_MAIN, HELP_TOGGLE,
        HELP_RELOAD, NOT_A_PLAYER, VILLAGER_PROFESSION, VILLAGER_JOBSITE,
        VILLAGER_LAST_WORKED, VILLAGER_RESTOCKS, VILLAGER_HOME, VILLAGER_SLEPT,
        VILLAGER_INVENTORY, PLAYER_REPUTATION, NONE, NEVER, EMPTY,
        HOUR, HOURS, MINUTE, MINUTES, SECOND_AGO, SECONDS_AGO,
        LOCATION_X, LOCATION_Y, LOCATION_Z
    }

    private static final HashMap<Message, String> localeMap = new HashMap<>();
    public static Sound configSound = null;
    public static int configTime = 0;

    public static void configValidator() {
        localeMap.clear();
        configSound = null;
        configTime = 0;
        FileConfiguration config = VillagerInfo.plugin.getConfig();
        FileConfiguration locale = VillagerInfo.localeConfig.getlocaleConfig();
        try {
            configSound = Sound.valueOf(config.getString("sound", "").toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException | NullPointerException e) {
            VillagerInfo.plugin.getLogger().warning("Configuration Error: " + configSound + " is not a valid sound! Please supply a valid sound");
            configSound = Sound.BLOCK_AMETHYST_BLOCK_BREAK;
        }
        if (config.getInt("highlight-time") <= 0) {
            VillagerInfo.plugin.getLogger().warning("Configuration Error: Invalid highlight time. If you would like to disable this feature, please set 'highlight-workstation' to 'false'. Otherwise please use an integer greater than zero. Setting value to 10s");
            configTime = 10;
        } else {
            configTime = config.getInt("highlight-time");
        }
        localeMap.put(Message.PREFIX, locale.getString("prefix"));
        localeMap.put(Message.TOGGLE_ON, locale.getString("toggle-on"));
        localeMap.put(Message.TOGGLE_OFF, locale.getString("toggle-off"));
        localeMap.put(Message.NO_PERMISSION, locale.getString("no-permission"));
        localeMap.put(Message.NO_COMMAND, locale.getString("no-command"));
        localeMap.put(Message.CONFIG_RELOADED, locale.getString("config-reloaded"));
        localeMap.put(Message.HELP_MAIN, locale.getString("help-main"));
        localeMap.put(Message.HELP_TOGGLE, locale.getString("help-toggle"));
        localeMap.put(Message.HELP_RELOAD, locale.getString("help-reload"));
        localeMap.put(Message.NOT_A_PLAYER, locale.getString("not-a-player"));
        localeMap.put(Message.VILLAGER_PROFESSION, locale.getString("villager-profession"));
        localeMap.put(Message.VILLAGER_JOBSITE, locale.getString("villager-jobsite-msg"));
        localeMap.put(Message.VILLAGER_LAST_WORKED, locale.getString("villager-last-worked-msg"));
        localeMap.put(Message.VILLAGER_RESTOCKS, locale.getString("villager-num-restocks-msg"));
        localeMap.put(Message.VILLAGER_HOME, locale.getString("villager-home-msg"));
        localeMap.put(Message.VILLAGER_SLEPT, locale.getString("villager-slept-msg"));
        localeMap.put(Message.VILLAGER_INVENTORY, locale.getString("villager-inventory-msg"));
        localeMap.put(Message.PLAYER_REPUTATION, locale.getString("player-reputation-msg"));
        localeMap.put(Message.NONE, locale.getString("none-msg"));
        localeMap.put(Message.NEVER, locale.getString("never-msg"));
        localeMap.put(Message.EMPTY, locale.getString("empty-msg"));
        localeMap.put(Message.HOUR, locale.getString("hour"));
        localeMap.put(Message.HOURS, locale.getString("hours"));
        localeMap.put(Message.MINUTE, locale.getString("minute"));
        localeMap.put(Message.MINUTES, locale.getString("minutes"));
        localeMap.put(Message.SECOND_AGO, locale.getString("second-ago"));
        localeMap.put(Message.SECONDS_AGO, locale.getString("seconds-ago"));
        localeMap.put(Message.LOCATION_X, locale.getString("location-x"));
        localeMap.put(Message.LOCATION_Y, locale.getString("location-y"));
        localeMap.put(Message.LOCATION_Z, locale.getString("location-z"));
    }

    public static Map<Message, String> getMapping() { return Collections.unmodifiableMap(localeMap); }

}
