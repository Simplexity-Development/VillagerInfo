package adhdmc.villagerinfo.Config;

import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LocaleConfig {
    private final VillagerInfo plugin;
    private static final String localeName = "locale.yml";
    private YamlConfiguration localeConfig = null;
    private File localeFile = null;

    public LocaleConfig(VillagerInfo plugin){
        this.plugin = plugin;
    }

    public void reloadConfig(){
        if (this.localeFile == null){
            this.localeFile = new File(this.plugin.getDataFolder(), localeName);
        }
        this.localeConfig = YamlConfiguration.loadConfiguration(this.localeFile);
        InputStream defaultStream = this.plugin.getResource(localeName);
        if(defaultStream != null){
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration( new InputStreamReader(defaultStream));
            this.localeConfig.setDefaults(defaultConfig);
        }
    }

    public YamlConfiguration getlocaleConfig(){
        if (this.localeConfig == null){
            reloadConfig();
        }
        return this.localeConfig;
    }

    public void saveConfig(){
        if(this.localeConfig == null || this.localeFile == null){
            return;
        }
        try{
            this.getlocaleConfig().save(this.localeFile);
        } catch (IOException e) {
            plugin.getLogger().severe("[saveConfig()] Could not save config to " + this.localeFile);
            e.printStackTrace();
        }
        if (!this.localeFile.exists()){
            this.plugin.saveResource(localeName, false);
        }
    }
}

