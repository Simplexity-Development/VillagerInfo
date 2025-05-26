package simplexity.villagerinfo.interaction.listeners;

import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import simplexity.villagerinfo.commands.villagerinfo.subcommands.toggle.subcommands.InteractType;
import simplexity.villagerinfo.configurations.functionality.VillConfig;
import simplexity.villagerinfo.interaction.logic.OutputManager;
import simplexity.villagerinfo.interaction.logic.PlayerToggle;
import simplexity.villagerinfo.util.Perm;

public class PrePlayerAttackEntityListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)

    public void onVillagerInteract(PrePlayerAttackEntityEvent attackEvent) {
        Player player = attackEvent.getPlayer();
        if (!passPlayerChecks(player)) return;
        Entity entity = attackEvent.getAttacked();
        if (!passEntityChecks(entity)) return;
        attackEvent.setCancelled(true);
        if (entity instanceof Villager villager) OutputManager.handleVillagerOutput(player, player, villager);
        if (entity instanceof ZombieVillager zombieVillager)
            OutputManager.handleZombieVillagerOutput(player, zombieVillager);
    }

    private boolean passPlayerChecks(Player player) {
        Material material = player.getEquipment().getItemInMainHand().getType();
        if (!player.isSneaking()) return false;
        if (!PlayerToggle.interactTypeAllowed(player, InteractType.PUNCH)) return false;
        if (!VillConfig.getInstance().isValidItem(material)) return false;
        return player.hasPermission(Perm.VILL_INFO_OUTPUT.getPerm());
    }

    private boolean passEntityChecks(Entity entity) {
        if (!(entity instanceof LivingEntity)) return false;
        return entity instanceof ZombieVillager || entity instanceof Villager;
    }
}
