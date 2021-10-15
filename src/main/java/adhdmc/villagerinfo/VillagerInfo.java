package adhdmc.villagerinfo;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class VillagerInfo extends JavaPlugin {
    public static VillagerInfo plugin;
    @Override
    public void onEnable(){
        plugin = this;
        getServer().getPluginManager().registerEvents(new VillagerHandler(), this);
        Objects.requireNonNull(this.getCommand("vill")).setExecutor(new CommandHandler());
    }
}