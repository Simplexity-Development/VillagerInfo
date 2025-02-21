package simplexity.villagerinfo.interaction.logic;

import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import simplexity.villagerinfo.commands.villagerinfo.subcommands.toggle.subcommands.OutputToggle;

public class ComponentManager {

    public static void sendZombieVillagerInfo(Player player, ZombieVillager zombieVillager) {
        if (!PlayerToggle.isPdcToggleEnabled(player, OutputToggle.outputEnabledKey)) return;
        ZombieVillagerData zombieVillagerData = new ZombieVillagerData(zombieVillager);
        Component componentToSend = ZombieVillagerComponentFactory.zombieVillagerComponentBuilder(zombieVillagerData);
        player.sendMessage(componentToSend);
    }

    public static void sendVillagerInfo(Player player, OfflinePlayer playerToParse, Villager villager) {
        if (!PlayerToggle.isPdcToggleEnabled(player, OutputToggle.outputEnabledKey)) return;
        VillagerData villagerData = new VillagerData(villager, playerToParse);
        Component componentToSend = VillagerComponentFactory.villagerComponentBuilder(villagerData);
        player.sendMessage(componentToSend);
    }


}
