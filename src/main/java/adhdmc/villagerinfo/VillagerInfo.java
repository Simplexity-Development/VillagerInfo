package adhdmc.villagerinfo;

import adhdmc.villagerinfo.Commands.CommandHandler;
import adhdmc.villagerinfo.Commands.SubCommands.HelpCommand;
import adhdmc.villagerinfo.Commands.SubCommands.ReloadCommand;
import adhdmc.villagerinfo.Commands.SubCommands.ToggleCommand;
import adhdmc.villagerinfo.Config.ConfigValidator;
import adhdmc.villagerinfo.Config.LocaleConfig;
import adhdmc.villagerinfo.VillagerHandling.HighlightHandling;
import adhdmc.villagerinfo.VillagerHandling.VillagerHandler;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bstats.bukkit.Metrics;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class VillagerInfo extends JavaPlugin {
    private static VillagerInfo instance;
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();
    public static final NamespacedKey INFO_ENABLED_KEY = new NamespacedKey("villagerinfo", "info-enabled");
    public static final NamespacedKey HIGHLIGHT_STATUS = new NamespacedKey("villagerinfo", "highlighted");
    public static boolean usingPurpur = true;

    @Override
    public void onEnable() {
        instance = this;
        try {
            Class.forName("net.kyori.adventure.text.minimessage.MiniMessage");
            Class.forName("com.destroystokyo.paper.entity.villager.Reputation");
        } catch (ClassNotFoundException e) {
            this.getLogger().severe("VillagerInfo relies on methods in classes not present on your server. Disabling plugin");
            this.getServer().getPluginManager().disablePlugin(this);
        }
        LocaleConfig.getInstance();
        this.saveDefaultConfig();
        this.reloadConfig();
        Metrics metrics = new Metrics(this, 13653);
        getServer().getPluginManager().registerEvents(new VillagerHandler(), this);
        Objects.requireNonNull(this.getCommand("vill")).setExecutor(new CommandHandler());
        ConfigValidator.configValidator();
        registerCommands();
    }

    @Override
    public void onDisable() {
        HighlightHandling.WORKSTATION_HIGHLIGHT_BLOCK.forEach((uuid, fallingBlock) -> fallingBlock.remove());
        HighlightHandling.VILLAGER_PDC.forEach((uuid, persistentDataContainer) -> persistentDataContainer.remove(HIGHLIGHT_STATUS));
        HighlightHandling.WORKSTATION_HIGHLIGHT_BLOCK.clear();
    }

    private void registerCommands() {
        CommandHandler.subcommandList.put("help", new HelpCommand());
        CommandHandler.subcommandList.put("toggle", new ToggleCommand());
        CommandHandler.subcommandList.put("reload", new ReloadCommand());
    }

    public static MiniMessage getMiniMessage(){
        return miniMessage;
    }
    public static VillagerInfo getInstance(){
        return instance;
    }
    public static Logger getVillagerInfoLogger() {
        return getInstance().getLogger();
    }
}
