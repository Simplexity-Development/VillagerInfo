package adhdmc.villagerinfo.Config;

import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ConfigValidator {

    private static Sound configSound = null;
    private static int configTime = 0;
    private static float soundVolume = 0;
    private static float soundPitch = 0;


    public static void configValidator() {
        configSound = null;
        configTime = 0;
        soundVolume = 0;
        soundPitch =  0;
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
        if (0 < config.getDouble("sound-volume") && config.getDouble("sound-volume") < 2) {
            soundVolume = (float) config.getDouble("sound-volume");
        } else {
            VillagerInfo.getInstance().getLogger().warning("Configuration Error: 'sound-volume' must be between 0.0 and 2.0. " +
                    "Setting to 0.5 until a valid number is provided");
            soundVolume = 0.5F;
        }
        if (0 < config.getDouble("sound-pitch") && config.getDouble("sound-pitch") < 2) {
            soundPitch = (float) config.getDouble("sound-pitch");
        } else {
            VillagerInfo.getInstance().getLogger().warning("Configuration Error: 'sound-pitch' must be between 0.0 and 2.0. " +
                    "Setting to 1.5 until a valid number is provided");
            soundPitch = 1.5F;
        }
        ToggleSetting.reloadToggles();
        Message.reloadLocale();
    }

    public static float getSoundPitch() {
        return soundPitch;
    }

    public static float getSoundVolume() {
        return soundVolume;
    }

    public static int getConfigTime() {
        return configTime;
    }

    public static Sound getConfigSound() {
        return configSound;
    }
}
