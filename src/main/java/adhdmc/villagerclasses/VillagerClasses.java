package adhdmc.villagerclasses;

import org.bukkit.plugin.java.JavaPlugin;

public final class VillagerClasses extends JavaPlugin {

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(new VillagerHandler(), this);
    }
}