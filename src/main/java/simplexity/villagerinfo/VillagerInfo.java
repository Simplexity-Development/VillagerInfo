package simplexity.villagerinfo;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.java.JavaPlugin;
import org.objectweb.asm.commons.Method;
import simplexity.villagerinfo.commands.VillReloadCommand;
import simplexity.villagerinfo.commands.util.SubCommand;
import simplexity.villagerinfo.commands.util.SubCommandMaps;
import simplexity.villagerinfo.commands.villagerinfo.VillagerInfoCommand;
import simplexity.villagerinfo.commands.villagerinfo.subcommands.HelpCommand;
import simplexity.villagerinfo.commands.villagerinfo.subcommands.toggle.ToggleCommand;
import simplexity.villagerinfo.commands.villagerinfo.subcommands.toggle.subcommands.HighlightToggle;
import simplexity.villagerinfo.commands.villagerinfo.subcommands.toggle.subcommands.OutputToggle;
import simplexity.villagerinfo.commands.villagerinfo.subcommands.toggle.subcommands.SoundToggle;
import simplexity.villagerinfo.configurations.functionality.VillConfig;
import simplexity.villagerinfo.configurations.locale.LocaleConfig;
import simplexity.villagerinfo.interaction.listeners.PlayerInteractEntityListener;
import simplexity.villagerinfo.interaction.logic.KillDisplay;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

public final class VillagerInfo extends JavaPlugin {
    private final HashMap<Villager, BlockDisplay> currentlyHighlighted = new HashMap<>();
    private static VillagerInfo instance;

    public static VillagerInfo getInstance() {
        return instance;
    }

    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private boolean usingPurpur = true;

    @Override
    public void onEnable() {
        instance = this;
        try {
            Class.forName("net.kyori.adventure.text.minimessage.MiniMessage");
        } catch (ClassNotFoundException e) {
            this.getVillagerInfoLogger().severe("You do not have a required dependency (net.kyori.adventure.text.minimessage.MiniMessage). Disabling VillagerInfo");
            this.getServer().getPluginManager().disablePlugin(this);
        }
        this.getServer().getPluginManager().registerEvents(new PlayerInteractEntityListener(), this);
        try {
            Method.getMethod(Villager.class.getMethod("isLobotomized"));
        } catch (NoSuchMethodException e) {
            usingPurpur = false;
        }
        reloadVillInfoConfigs();
        registerCommands();
    }

    public void reloadVillInfoConfigs() {
        getInstance().saveDefaultConfig();
        LocaleConfig.getInstance().reloadLocale();
        VillConfig.getInstance().reloadVillConfig(this.getConfig());
    }

    public void registerCommands() {
        Objects.requireNonNull(this.getCommand("villreload")).setExecutor(new VillReloadCommand());
        Objects.requireNonNull(this.getCommand("villagerinfo")).setExecutor(new VillagerInfoCommand());
        populateToggleSubcommands();
        populateVillagerInfoSubCommands();
    }

    public void populateToggleSubcommands() {
        HashMap<String, SubCommand> commandMap = SubCommandMaps.getInstance().getToggleSubCommands();
        commandMap.put("output", new OutputToggle());
        commandMap.put("highlight", new HighlightToggle());
        commandMap.put("sound", new SoundToggle());
    }

    public void populateVillagerInfoSubCommands() {
        HashMap<String, SubCommand> commandMap = SubCommandMaps.getInstance().getVillagerInfoSubCommands();
        commandMap.put("help", new HelpCommand());
        commandMap.put("toggle", new ToggleCommand());

    }

    public Logger getVillagerInfoLogger() {
        return instance.getLogger();
    }

    public MiniMessage getMiniMessage() {
        return miniMessage;
    }

    public boolean isUsingPurpur() {
        return usingPurpur;
    }

    @Override
    public void onDisable() {
        KillDisplay.clearAllCurrentHighlights();
    }


}
