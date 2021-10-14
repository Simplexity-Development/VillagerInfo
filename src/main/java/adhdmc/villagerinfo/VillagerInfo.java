package adhdmc.villagerinfo;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class VillagerInfo extends JavaPlugin {

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(new VillagerHandler(), this);
        Objects.requireNonNull(this.getCommand("vill")).setExecutor(new CommandHandler());
    }
}