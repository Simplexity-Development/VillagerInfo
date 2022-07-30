package adhdmc.villagerinfo.Config;

import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Locale;

public class ConfigValidator {
     public static String prefixString, toggleOnString,
     toggleOffString, noPermissionString, noSubCommandString,
     configReloadedString, helpMainString, helpToggleString,
     helpReloadString, notAPlayerString, villagerProfessionString,
     villagerJobsiteString, villagerLastWorkedString,
     villagerRestocksString, villagerHomeString, villagerSleptString,
     villagerInventoryString, reputationString,
     noneString, neverString, emptyString;
     public static Sound configSound = null;

    public static void configValidator(){
        prefixString = "";
        toggleOnString = "";
        toggleOffString = "";
        noPermissionString = "";
        noSubCommandString = "";
        configReloadedString = "";
        helpMainString = "";
        helpToggleString = "";
        helpReloadString = "";
        notAPlayerString = "";
        villagerProfessionString = "";
        villagerJobsiteString = "";
        villagerLastWorkedString = "";
        villagerRestocksString = "";
        villagerHomeString = "";
        villagerSleptString = "";
        villagerInventoryString = "";
        reputationString = "";
        noneString = "";
        neverString = "";
        emptyString = "";
        configSound = null;
        FileConfiguration config = VillagerInfo.plugin.getConfig();
        FileConfiguration locale = VillagerInfo.localeConfig.getlocaleConfig();
        try {
            configSound = Sound.valueOf(config.getString("Sound").toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException | NullPointerException e) {
            VillagerInfo.plugin.getLogger().warning("Configuration Error: " + configSound + " is not a valid sound! Please supply a valid sound");
        }
        if(config.getInt("highlight-time") <= 0){
            VillagerInfo.plugin.getLogger().warning("Configuration Error: Invalid highlight time. If you would like to disable this feature, please set 'highlight-workstation' to 'false'. Otherwise please use an integer greater than zero");
        }
    }

     timeErrorMsg(String s){
        FileConfiguration config = VillagerInfo.plugin.getConfig();
        int configTime = config.getInt("Length of time to highlight workstation");
        if (configTime <= 0){
            s = ChatColor.RED + "Configuration Error: Invalid highlight time.\nIf you would like to disable this feature,\nplease set 'highlight workstation' to false.\nOtherwise please use an integer greater than zero";
        }
        return s;
    }
}
