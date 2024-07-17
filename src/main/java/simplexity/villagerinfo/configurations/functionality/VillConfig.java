package simplexity.villagerinfo.configurations.functionality;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import simplexity.villagerinfo.VillagerInfo;
import simplexity.villagerinfo.configurations.locale.ServerMessage;

import java.util.*;
import java.util.logging.Logger;

public class VillConfig {
    private static VillConfig instance;

    private VillConfig() {
    }

    public static VillConfig getInstance() {
        if (instance == null) instance = new VillConfig();
        return instance;
    }

    private Sound configuredSound;
    private int configuredHighlightTime;
    private float configuredSoundVolume;
    private float configuredSoundPitch;
    private final HashMap<Material, Color> poiBlockHighlightColorsMap = new HashMap<>();
    String error = ServerMessage.CONFIGURATION_ERROR_PREFIX.getMessage();

    private boolean isWhitelist;
    private Set<Material> itemSet = new HashSet<>();

    private final Logger logger = VillagerInfo.getInstance().getVillagerInfoLogger();

    public void reloadVillConfig(FileConfiguration config) {
        reloadSound(config);
        reloadSoundPitch(config);
        reloadSoundVolume(config);
        reloadHighlightTime(config);
        reloadToggles(config);
        reloadColors(config);
        reloadItemList(config);
    }

    public void reloadSound(FileConfiguration config) {
        configuredSound = null;
        try {
            configuredSound = Sound.valueOf(config.getString("sound", "BLOCK_AMETHYST_BLOCK_BREAK").toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.warning(error + configuredSound + ServerMessage.CONFIGURED_SOUND_ERROR.getMessage());
            configuredSound = Sound.BLOCK_AMETHYST_BLOCK_BREAK;
        }
    }

    public void reloadSoundPitch(FileConfiguration config) {
        configuredSoundPitch = 0;
        if (0 < config.getDouble("sound-pitch") && config.getDouble("sound-pitch") < 2) {
            configuredSoundPitch = (float) config.getDouble("sound-pitch");
        } else {
            logger.warning(error + ServerMessage.CONFIGURED_PITCH_ERROR.getMessage());
            configuredSoundPitch = 1.5F;
        }
    }

    public void reloadSoundVolume(FileConfiguration config) {
        configuredSoundVolume = 0;
        if (0 < config.getDouble("sound-volume") && config.getDouble("sound-volume") < 2) {
            configuredSoundVolume = (float) config.getDouble("sound-volume");
        } else {
            logger.warning(error + ServerMessage.CONFIGURED_VOLUME_ERROR.getMessage());
            configuredSoundVolume = 0.5F;
        }
    }

    public void reloadHighlightTime(FileConfiguration config) {
        configuredHighlightTime = 0;
        if (config.getInt("highlight-time", 10) <= 0) {
            logger.warning(error + ServerMessage.CONFIGURED_HIGHLIGHT_TIME_ERROR.getMessage());
            configuredHighlightTime = 10;
        } else {
            configuredHighlightTime = config.getInt("highlight-time");
        }
    }

    public void reloadToggles(FileConfiguration config) {
        ConfigurationSection toggleSection = config.getConfigurationSection("toggles");
        if (toggleSection == null) {
            logger.warning(error + ServerMessage.NO_CONFIG_TOGGLES_FOUND.getMessage() + ServerMessage.ERROR_CHECK_FOR_TABS.getMessage());
            return;
        }
        Set<String> keys = toggleSection.getKeys(false);
        for (String key : keys) {
            try {
                ConfigToggle toggle = ConfigToggle.valueOf(key);
                toggle.setEnabled(toggleSection.getBoolean(key));
            } catch (IllegalArgumentException e) {
                logger.warning(ServerMessage.LOGGER_INVALID_TOGGLE_KEY.getMessage() + key);
            }
        }
        ConfigToggle.OUTPUT_ENABLED.setEnabled(config.getBoolean("output-enabled", true));
        ConfigToggle.PLAY_SOUND_ON_INFO_DISPLAY.setEnabled(config.getBoolean("play-sound-on-output", true));
        ConfigToggle.HIGHLIGHT_VILLAGER_WORKSTATION_ON_OUTPUT.setEnabled(config.getBoolean("highlight-workstation-on-output", true));
        if (!(ConfigToggle.OUTPUT_ENABLED.isEnabled() || ConfigToggle.HIGHLIGHT_VILLAGER_WORKSTATION_ON_OUTPUT.isEnabled() || ConfigToggle.PLAY_SOUND_ON_INFO_DISPLAY.isEnabled())) {
            logger.warning(error + ServerMessage.ERROR_NO_FUNCTIONALITY_ENABLED.getMessage());
        }
    }

    public void reloadColors(FileConfiguration config) {
        ConfigurationSection colorSection = config.getConfigurationSection("workstation-highlight-color");
        if (colorSection == null) {
            logger.warning(error + ServerMessage.NO_COLOR_SECTION_FOUND.getMessage() + ServerMessage.ERROR_CHECK_FOR_TABS.getMessage());
            return;
        }
        poiBlockHighlightColorsMap.clear();
        Set<String> colorKeys = colorSection.getKeys(false);
        Material blockMaterial;
        for (String key : colorKeys) {
            blockMaterial = Material.getMaterial(key);
            if (blockMaterial == null) {
                logger.warning(error + key + ServerMessage.ERROR_NOT_A_MATERIAL.getMessage());
                continue;
            }
            List<Integer> rgbList = colorSection.getIntegerList(key);
            if (rgbList.size() < 3) {
                logger.warning(error + ServerMessage.ERROR_COLOR_DECLARED_INCORRECTLY.getMessage() + rgbList);
                continue;
            }
            Color color = Color.fromRGB(rgbList.get(0), rgbList.get(1), rgbList.get(2));
            poiBlockHighlightColorsMap.put(blockMaterial, color);
        }
    }

    public void reloadItemList(FileConfiguration config) {
        isWhitelist = config.getBoolean("whitelist", false);
        for (String item : config.getStringList("items")) {
            Material material = Material.getMaterial(item);
            if (material != null) {
                itemSet.add(material);
                continue;
            }
            Logger logger = VillagerInfo.getInstance().getVillagerInfoLogger();
            logger.warning("Invalid material in item list: " + item);
        }
    }

    public Sound getConfiguredSound() {
        return configuredSound;
    }

    public int getConfiguredHighlightTime() {
        return configuredHighlightTime;
    }

    public float getConfiguredSoundPitch() {
        return configuredSoundPitch;
    }

    public float getConfiguredSoundVolume() {
        return configuredSoundVolume;
    }

    public Map<Material, Color> getPoiBlockHighlightColorsMap() {
        return Collections.unmodifiableMap(poiBlockHighlightColorsMap);
    }

    public boolean isValidItem(Material material) {
        return itemSet.contains(material) == isWhitelist;
    }
}
