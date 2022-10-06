package adhdmc.villagerinfo.Config;

import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ConfigValidator {

    public static Sound configSound = null;
    public static int configTime = 0;


    public static void configValidator() {
        configSound = null;
        configTime = 0;
        FileConfiguration config = VillagerInfo.getInstance().getConfig();
        try {
            configSound = Sound.valueOf(config.getString("sound", "BLOCK_AMETHYST_BLOCK_BREAK").toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException | NullPointerException e) {
            VillagerInfo.getInstance().getLogger().warning("Configuration Error: " + configSound + " is not a valid sound! Please supply a valid sound");
            configSound = Sound.BLOCK_AMETHYST_BLOCK_BREAK;
        }
        if (config.getInt("highlight-time", 10) <= 0) {
            VillagerInfo.getInstance().getLogger().warning("Configuration Error: Invalid highlight time. If you would like to disable this feature, please set 'highlight-workstation' to 'false'. Otherwise please use an integer greater than zero. Setting value to 10s");
            configTime = 10;
        } else {
            configTime = config.getInt("highlight-time");
        }
        ToggleSetting.reloadToggles();
        Message.reloadLocale();
    }
}
