package adhdmc.villagerinfo.Config;

import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Locale;

public class ConfigValidator {
     public static HashMap<String, String> localeMap = new HashMap<>();
     public static Sound configSound = null;
     public static int configTime = 0;

    public static void configValidator(){
        localeMap.clear();
        configSound = null;
        configTime = 0;
        FileConfiguration config = VillagerInfo.plugin.getConfig();
        FileConfiguration locale = VillagerInfo.localeConfig.getlocaleConfig();
        try {
            configSound = Sound.valueOf(config.getString("sound").toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException | NullPointerException e) {
            VillagerInfo.plugin.getLogger().warning("Configuration Error: " + configSound + " is not a valid sound! Please supply a valid sound");
            configSound = Sound.BLOCK_AMETHYST_BLOCK_BREAK;
        }
        if(config.getInt("highlight-time") <= 0){
            VillagerInfo.plugin.getLogger().warning("Configuration Error: Invalid highlight time. If you would like to disable this feature, please set 'highlight-workstation' to 'false'. Otherwise please use an integer greater than zero. Setting value to 10s");
            configTime = 10;
        } else {
            configTime = config.getInt("highlight-time");
        }
        localeMap.put("prefix", locale.getString("prefix"));
        localeMap.put("toggle-on",  locale.getString("toggle-on"));
        localeMap.put("toggle-off",  locale.getString("toggle-off"));
        localeMap.put("no-permission",  locale.getString("no-permission"));
        localeMap.put("no-command",  locale.getString("no-command"));
        localeMap.put("config-reload",  locale.getString("config-reload"));
        localeMap.put("help-main",  locale.getString("help-main"));
        localeMap.put("help-toggle",  locale.getString("help-toggle"));
        localeMap.put("help-reload",  locale.getString("help-reload"));
        localeMap.put("not-a-player",  locale.getString("not-a-player"));
        localeMap.put("villager-profession",  locale.getString("villager-profession"));
        localeMap.put("villager-jobsite-msg",  locale.getString("villager-jobsite-msg"));
        localeMap.put("villager-last-worked-msg",  locale.getString("villager-last-worked-msg"));
        localeMap.put("villager-num-restocks-msg",  locale.getString("villager-num-restocks-msg"));
        localeMap.put("villager-home-msg",  locale.getString("villager-home-msg"));
        localeMap.put("villager-slept-msg",  locale.getString("villager-slept-msg"));
        localeMap.put("villager-inventory-msg",  locale.getString("villager-inventory-msg"));
        localeMap.put("player-reputation-msg",  locale.getString("player-reputation-msg"));
        localeMap.put("none-msg",  locale.getString("none-msg"));
        localeMap.put("never-msg",  locale.getString("never-msg"));
        localeMap.put("empty-string",  locale.getString("empty-msg"));
        localeMap.put("hour",  locale.getString("hour"));
        localeMap.put("hours",  locale.getString("hours"));
        localeMap.put("minute",  locale.getString("minute"));
        localeMap.put("minutes",  locale.getString("minutes"));
        localeMap.put("second-ago",  locale.getString("second-ago"));
        localeMap.put("seconds-ago",  locale.getString("seconds-ago"));
        localeMap.put("location-x",  locale.getString("location-x"));
        localeMap.put("location-y",  locale.getString("location-x"));
        localeMap.put("location-z",  locale.getString("location-z"));
    }
}
