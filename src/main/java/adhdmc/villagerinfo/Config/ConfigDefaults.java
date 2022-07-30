package adhdmc.villagerinfo.Config;

import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigDefaults {

        public static void mainConfigDefaults() {
            FileConfiguration config = VillagerInfo.plugin.getConfig();
            config.addDefault("Profession", true);
            config.addDefault("Job Site", true);
            config.addDefault("Last Worked", true);
            config.addDefault("Bed Location", true);
            config.addDefault("Last Slept", true);
            config.addDefault("Villager Inventory Contents", true);
            config.addDefault("Number of Restocks", true);
            config.addDefault("Player Reputation", true);
            config.addDefault("configReload", "&6VillagerInfo Config Reloaded!");
            config.addDefault("soundToggle", true);
            config.addDefault("Sound","BLOCK_AMETHYST_BLOCK_BREAK");
            config.addDefault("Highlight Workstation", true);
            config.addDefault("Length of time to highlight workstation", 10);}
}
