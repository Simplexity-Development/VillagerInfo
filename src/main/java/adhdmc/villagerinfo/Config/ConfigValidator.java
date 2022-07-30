package adhdmc.villagerinfo.Config;

import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigValidator {

    public static String soundErrorMsg(String s){
        FileConfiguration config = VillagerInfo.plugin.getConfig();
        Sound configSound = null;
        try {
            configSound = Sound.valueOf(config.getString("Sound"));
        } catch (IllegalArgumentException e) {
            s = ChatColor.RED + "Configuration Error: Invalid Sound. Please choose from these options.\nhttps://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html";
        }
        if (configSound != null) {
            s=null;
        }
        return s;
    }

    public static String timeErrorMsg(String s){
        FileConfiguration config = VillagerInfo.plugin.getConfig();
        int configTime = config.getInt("Length of time to highlight workstation");
        if (!(configTime > 0)){
            s = ChatColor.RED + "Configuration Error: Invalid highlight time.\nIf you would like to disable this feature,\nplease set 'highlight workstation' to false.\nOtherwise please use an integer greater than zero";
        }
        return s;
    }
}
