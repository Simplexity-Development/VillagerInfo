package adhdmc.villagerinfo.Config;

import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigDefaults {

        public static void mainConfigDefaults() {
            FileConfiguration config = VillagerInfo.plugin.getConfig();
            config.addDefault("profession", true);
            config.addDefault("job-site", true);
            config.addDefault("last-worked", true);
            config.addDefault("bed-location", true);
            config.addDefault("last-slept", true);
            config.addDefault("inventory", true);
            config.addDefault("restocks", true);
            config.addDefault("reputation", true);
            config.addDefault("sound-toggle", true);
            config.addDefault("sound","BLOCK_AMETHYST_BLOCK_BREAK");
            config.addDefault("highlight-workstation", true);
            config.addDefault("highlight-time", 10);
        }
}
