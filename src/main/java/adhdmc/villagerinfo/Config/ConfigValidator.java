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
            configSound = Sound.valueOf(config.getString("Sound").toUpperCase(Locale.ROOT));
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
        localeMap.put("prefixString", locale.getString("prefix"));
        localeMap.put("toggleOnString",  locale.getString("toggle-on"));
        localeMap.put("toggleOffString",  locale.getString("toggle-off"));
        localeMap.put("noPermissionString",  locale.getString("no-permission"));
        localeMap.put("noSubCommandString",  locale.getString("no-command"));
        localeMap.put("configReloadedString",  locale.getString("config-reloaded"));
        localeMap.put("helpMainString",  locale.getString("help-main"));
        localeMap.put("helpToggleString",  locale.getString("help-toggle"));
        localeMap.put("helpReloadString",  locale.getString("help-reload"));
        localeMap.put("notAPlayerString",  locale.getString("not-a-player"));
        localeMap.put("villagerProfessionString",  locale.getString("villager-profession"));
        localeMap.put("villagerJobsiteString",  locale.getString("villager-jobsite-msg"));
        localeMap.put("villagerLastWorkedString",  locale.getString("villager-last-worked-msg"));
        localeMap.put("villagerRestocksString",  locale.getString("villager-num-restocks-msg"));
        localeMap.put("villagerHomeString",  locale.getString("villager-home-msg"));
        localeMap.put("villagerSleptString",  locale.getString("villager-slept-msg"));
        localeMap.put("villagerInventoryString",  locale.getString("villager-inventory-msg"));
        localeMap.put("reputationString",  locale.getString("player-reputation-msg"));
        localeMap.put("noneString",  locale.getString("none-msg"));
        localeMap.put("neverString",  locale.getString("never-msg"));
        localeMap.put("emptyString",  locale.getString("empty-msg"));
        localeMap.put("hour",  locale.getString("Hour, "));
        localeMap.put("hours",  locale.getString("Hours, "));
        localeMap.put("minute",  locale.getString("Minute, "));
        localeMap.put("minutes",  locale.getString("Minutes, "));
        localeMap.put("second-ago",  locale.getString("Second Ago"));
        localeMap.put("seconds-ago",  locale.getString("Seconds Ago"));
        localeMap.put("locationX",  locale.getString("villager-jobsite-x"));
        localeMap.put("locationY",  locale.getString("villager-jobsite-y"));
        localeMap.put("locationZ",  locale.getString("villager-jobsite-z"));
    }
}
