package simplexity.villagerinfo.interaction.logic;

import org.bukkit.Bukkit;
import simplexity.villagerinfo.events.VillagerOutputEvent;
import simplexity.villagerinfo.events.ZombieVillagerOutputEvent;

public class OutputLogic {

    private static OutputLogic instance;

    private OutputLogic() {
    }

    public static OutputLogic getInstance() {
        if (instance == null) instance = new OutputLogic();
        return instance;
    }

    public void runVillagerOutput(org.bukkit.entity.Villager villager, org.bukkit.entity.Player player) {
        VillagerOutputEvent villagerOutputEvent = callVillagerOutputEvent(villager, player);
        if (villagerOutputEvent == null) return;
        villagerOutputEvent.sendOutputToPlayer();
    }

    public VillagerOutputEvent callVillagerOutputEvent(org.bukkit.entity.Villager villager, org.bukkit.entity.Player player) {
        VillagerOutputEvent villagerOutputEvent = new VillagerOutputEvent(villager, player);
        Bukkit.getServer().getPluginManager().callEvent(villagerOutputEvent);
        if (villagerOutputEvent.isCancelled()) return null;
        return villagerOutputEvent;
    }

    public void runZombieVillagerOutput(org.bukkit.entity.ZombieVillager zombieVillager, org.bukkit.entity.Player player) {
        ZombieVillagerOutputEvent zombieVillagerOutputEvent = callZombieVillagerOutputEvent(zombieVillager, player);
        if (zombieVillagerOutputEvent == null) return;
        zombieVillagerOutputEvent.sendOutputToPlayer();
    }

    public ZombieVillagerOutputEvent callZombieVillagerOutputEvent(org.bukkit.entity.ZombieVillager zombieVillager, org.bukkit.entity.Player player) {
        ZombieVillagerOutputEvent zombieVillagerOutputEvent = new ZombieVillagerOutputEvent(zombieVillager, player);
        Bukkit.getServer().getPluginManager().callEvent(zombieVillagerOutputEvent);
        if (zombieVillagerOutputEvent.isCancelled()) return null;
        return zombieVillagerOutputEvent;
    }
}
