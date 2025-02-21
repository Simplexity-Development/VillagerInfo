package simplexity.villagerinfo.interaction.logic;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import simplexity.villagerinfo.commands.villagerinfo.subcommands.toggle.subcommands.HighlightToggle;
import simplexity.villagerinfo.commands.villagerinfo.subcommands.toggle.subcommands.SoundToggle;

public class OutputManager {


    public static void handleVillagerOutput(Player player, OfflinePlayer playerToProcess, Villager villager) {
        ComponentManager.sendVillagerInfo(player, playerToProcess, villager);
        highlightBlocks(player, villager);
        outputSound(player);
    }

    public static void handleZombieVillagerOutput(Player player, ZombieVillager zombieVillager) {
        ComponentManager.sendZombieVillagerInfo(player, zombieVillager);
    }

    public static void highlightBlocks(Player player, Villager villager) {
        if (!PlayerToggle.isPdcToggleEnabled(player, HighlightToggle.highlightEnabledKey)) return;
        DisplayManager.handleBedHighlight(villager);
        DisplayManager.handleWorkstationHighlight(villager);
    }

    public static void outputSound(Player player) {
        if (!PlayerToggle.isPdcToggleEnabled(player, SoundToggle.soundEnabledKey)) return;
        SoundLogic.outputSound(player);
    }


}
