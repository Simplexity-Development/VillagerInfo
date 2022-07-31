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
        localeMap.put("toggleOnString", "toggle-on");
        localeMap.put("toggleOffString", "toggle-off");
        localeMap.put("noPermissionString", "no-permission");
        localeMap.put("noSubCommandString", "no-command");
        localeMap.put("configReloadedString", "config-reloaded");
        localeMap.put("helpMainString", "help-main");
        localeMap.put("helpToggleString", "help-toggle");
        localeMap.put("helpReloadString", "help-reload");
        localeMap.put("notAPlayerString", "not-a-player");
        localeMap.put("villagerProfessionString", "villager-profession");
        localeMap.put("villagerJobsiteString", "villager-jobsite-msg");
        localeMap.put("villagerLastWorkedString", "villager-last-worked-msg");
        localeMap.put("villagerRestocksString", "villager-num-restocks-msg");
        localeMap.put("villagerHomeString", "villager-home-msg");
        localeMap.put("villagerSleptString", "villager-slept-msg");
        localeMap.put("villagerInventoryString", "villager-inventory-msg");
        localeMap.put("reputationString", "player-reputation-msg");
        localeMap.put("noneString", "none-msg");
        localeMap.put("neverString", "never-msg");
        localeMap.put("emptyString", "empty-msg");
        localeMap.put("hour", "Hour, ");
        localeMap.put("hours", "Hours, ");
        localeMap.put("minute", "Minute, ");
        localeMap.put("minutes", "Minutes, ");
        localeMap.put("second-ago", "Second Ago");
        localeMap.put("seconds-ago", "Seconds Ago");
        localeMap.put("locationX", "villager-jobsite-x");
        localeMap.put("locationY", "villager-jobsite-y");
        localeMap.put("locationZ", "villager-jobsite-z");
    }
}
