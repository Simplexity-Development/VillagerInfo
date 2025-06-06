package simplexity.villagerinfo.interaction.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import simplexity.villagerinfo.commands.villagerinfo.subcommands.toggle.subcommands.InteractType;
import simplexity.villagerinfo.configurations.functionality.VillConfig;
import simplexity.villagerinfo.interaction.logic.OutputManager;
import simplexity.villagerinfo.interaction.logic.PlayerToggle;
import simplexity.villagerinfo.util.Perm;


public class PlayerInteractEntityListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)

    public void onVillagerInteract(PlayerInteractEntityEvent interactEntityEvent) {
        if (interactEntityEvent.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        Player player = interactEntityEvent.getPlayer();
        if (!passPlayerChecks(player)) return;
        Entity entity = interactEntityEvent.getRightClicked();
        if (!passEntityChecks(entity)) return;
        interactEntityEvent.setCancelled(true);
        if (entity instanceof Villager villager) OutputManager.handleVillagerOutput(player, player, villager);
        if (entity instanceof ZombieVillager zombieVillager)
            OutputManager.handleZombieVillagerOutput(player, zombieVillager);
    }

    private boolean passPlayerChecks(Player player) {
        Material material = player.getEquipment().getItemInMainHand().getType();
        if (!player.isSneaking()) return false;
        if (!PlayerToggle.interactTypeAllowed(player, InteractType.RIGHT_CLICK)) return false;
        if (!VillConfig.getInstance().isValidItem(material)) return false;
        return player.hasPermission(Perm.VILL_INFO_OUTPUT.getPerm());
    }

    private boolean passEntityChecks(Entity entity) {
        if (!(entity instanceof LivingEntity)) return false;
        return entity instanceof ZombieVillager || entity instanceof Villager;
    }


}
