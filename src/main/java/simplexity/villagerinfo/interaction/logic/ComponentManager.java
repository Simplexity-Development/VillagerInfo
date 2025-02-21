package simplexity.villagerinfo.interaction.logic;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import simplexity.villagerinfo.commands.villagerinfo.subcommands.toggle.subcommands.OutputToggle;
import simplexity.villagerinfo.events.VillagerOutputEvent;
import simplexity.villagerinfo.events.ZombieVillagerOutputEvent;

public class ComponentManager {

    public static void sendZombieVillagerInfo(Player player, ZombieVillager zombieVillager) {
        if (!PlayerToggle.isPdcToggleEnabled(player, OutputToggle.outputEnabledKey)) return;
        ZombieVillagerData zombieVillagerData = new ZombieVillagerData(zombieVillager);
        Component componentToSend = ZombieVillagerComponentFactory.zombieVillagerComponentBuilder(zombieVillagerData);
        ZombieVillagerOutputEvent outputEvent = callZombieVillagerOutputEvent(player, zombieVillager, componentToSend);
        if (outputEvent == null) return;
        outputEvent.getOutputPlayer().sendMessage(outputEvent.getOutputComponent());
    }

    public static void sendVillagerInfo(Player player, OfflinePlayer playerToParse, Villager villager) {
        if (!PlayerToggle.isPdcToggleEnabled(player, OutputToggle.outputEnabledKey)) return;
        VillagerData villagerData = new VillagerData(villager, playerToParse);
        Component componentToSend = VillagerComponentFactory.villagerComponentBuilder(villagerData);
        VillagerOutputEvent outputEvent = callVillagerOutputEvent(player, villager, componentToSend);
        if (outputEvent == null) return;
        outputEvent.getOutputPlayer().sendMessage(outputEvent.getOutputComponent());
    }

    private static VillagerOutputEvent callVillagerOutputEvent(Player player, Villager villager, Component componentToSend) {
        VillagerOutputEvent outputEvent = new VillagerOutputEvent(villager, player, componentToSend);
        Bukkit.getPluginManager().callEvent(outputEvent);
        if (outputEvent.isCancelled()) return null;
        return outputEvent;
    }

    private static ZombieVillagerOutputEvent callZombieVillagerOutputEvent(Player player, ZombieVillager zombieVillager, Component componentToSend) {
        ZombieVillagerOutputEvent outputEvent = new ZombieVillagerOutputEvent(zombieVillager, player, componentToSend);
        Bukkit.getPluginManager().callEvent(outputEvent);
        if (outputEvent.isCancelled()) return null;
        return outputEvent;
    }




}
