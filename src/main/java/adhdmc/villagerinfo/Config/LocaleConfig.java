package adhdmc.villagerinfo.Config;

import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class LocaleConfig {

    private static LocaleConfig instance;
    private final String fileName = "locale.yml";
    private final  File localeFile = new File(VillagerInfo.getInstance().getDataFolder(), fileName);
    private final FileConfiguration localeConfig = new YamlConfiguration();


    private LocaleConfig() {
        if (!localeFile.exists()) VillagerInfo.getInstance().saveResource(fileName, false);
        reloadLocale();
    }

    public static LocaleConfig getInstance() {
        if (instance == null) instance = new LocaleConfig();
        return instance;
    }

    public FileConfiguration getLocale() { return localeConfig; }

    public void reloadLocale() {
        try { localeConfig.load(localeFile); }
        catch (IOException | InvalidConfigurationException e) { e.printStackTrace(); }
        Set<String> keys = localeConfig.getKeys(false);
        for (String key : keys) {
            try {
                VIMessage message = VIMessage.valueOf(key);
                message.setMessage(localeConfig.getString(key, message.getMessage()));
            } catch (IllegalArgumentException e) {
                VillagerInfo.getVillagerInfoLogger().warning(VIMessage.LOGGER_INVALID_LOCALE_KEY.getMessage() + key);
            }
        }
    }
}
