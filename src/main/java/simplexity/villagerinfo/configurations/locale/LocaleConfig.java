package simplexity.villagerinfo.configurations.locale;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import simplexity.villagerinfo.VillagerInfo;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

public class LocaleConfig {

    private static LocaleConfig instance;
    private final String fileName = "locale.yml";
    private final File localeFile = new File(VillagerInfo.getInstance().getDataFolder(), fileName);
    private final FileConfiguration localeConfig = new YamlConfiguration();
    Logger logger = VillagerInfo.getInstance().getVillagerInfoLogger();


    private LocaleConfig() {
        if (!localeFile.exists()) VillagerInfo.getInstance().saveResource(fileName, false);
        reloadLocale();
    }

    public static LocaleConfig getInstance() {
        if (instance == null) instance = new LocaleConfig();
        return instance;
    }

    public FileConfiguration getLocale() {
        return localeConfig;
    }

    public void reloadLocale() {
        try {
            localeConfig.load(localeFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        ConfigurationSection villagerInformationSection = localeConfig.getConfigurationSection("villager-information-messages");
        ConfigurationSection insertionMessageSection = localeConfig.getConfigurationSection("insertion-messages");
        ConfigurationSection serverMessageSection = localeConfig.getConfigurationSection("server-messages");
        if (villagerInformationSection == null) {
            logger.warning(ServerMessage.NO_LOCALE_SECTION_FOUND.getMessage() + "villager-information-messages - " + ServerMessage.ERROR_CHECK_FOR_TABS.getMessage());
        } else {
            Set<String> villagerInformationKeys = villagerInformationSection.getKeys(false);
            for (String key : villagerInformationKeys) {
                try {
                    VillagerMessage message = VillagerMessage.valueOf(key);
                    message.setMessage(villagerInformationSection.getString(key, message.getMessage()));
                } catch (IllegalArgumentException e) {
                    logger.warning(ServerMessage.LOGGER_INVALID_LOCALE_KEY.getMessage() + key);
                }
            }
        }
        if (insertionMessageSection == null) {
            logger.warning(ServerMessage.NO_LOCALE_SECTION_FOUND.getMessage() + "insertion-messages - " + ServerMessage.ERROR_CHECK_FOR_TABS.getMessage());
        } else {
            Set<String> insertionMessageKeys = insertionMessageSection.getKeys(false);
            for (String key : insertionMessageKeys) {
                try {
                    MessageInsert message = MessageInsert.valueOf(key);
                    message.setMessage(insertionMessageSection.getString(key, message.getMessage()));
                } catch (IllegalArgumentException e) {
                    logger.warning(ServerMessage.LOGGER_INVALID_LOCALE_KEY.getMessage() + key);
                }
            }
        }
        if (serverMessageSection == null) {
            logger.warning(ServerMessage.NO_LOCALE_SECTION_FOUND.getMessage() + "server-messages - " + ServerMessage.ERROR_CHECK_FOR_TABS.getMessage());
        } else {
            Set<String> serverMessageKeys = serverMessageSection.getKeys(false);
            for (String key : serverMessageKeys) {
                try {
                    ServerMessage message = ServerMessage.valueOf(key);
                    message.setMessage(serverMessageSection.getString(key, message.getMessage()));
                } catch (IllegalArgumentException e) {
                    logger.warning(ServerMessage.LOGGER_INVALID_LOCALE_KEY.getMessage() + key);
                }
            }
        }

    }
}

