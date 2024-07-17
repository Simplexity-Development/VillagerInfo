package simplexity.villagerinfo.interaction.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import simplexity.villagerinfo.configurations.functionality.ConfigToggle;
import simplexity.villagerinfo.configurations.functionality.VillConfig;
import simplexity.villagerinfo.interaction.logic.HighlightLogic;
import simplexity.villagerinfo.interaction.logic.OutputLogic;
import simplexity.villagerinfo.interaction.logic.SoundLogic;
import simplexity.villagerinfo.util.Perm;

public class PlayerInteractEntityListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)

    public void onVillagerInteract(PlayerInteractEntityEvent interactEntityEvent) {
        if (interactEntityEvent.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        Player player = interactEntityEvent.getPlayer();
        Material material = player.getEquipment().getItemInMainHand().getType();
        if (!VillConfig.getInstance().isValidItem(material)) return;
        if (!player.isSneaking()) return;
        if (!((interactEntityEvent.getRightClicked() instanceof Villager) || (interactEntityEvent.getRightClicked() instanceof ZombieVillager)))
            return;
        if (!player.hasPermission(Perm.VILL_INFO_OUTPUT.getPerm())) return;
        if (!(ConfigToggle.OUTPUT_ENABLED.isEnabled() || ConfigToggle.HIGHLIGHT_VILLAGER_WORKSTATION_ON_OUTPUT.isEnabled() || ConfigToggle.PLAY_SOUND_ON_INFO_DISPLAY.isEnabled()))
            return;
        interactEntityEvent.setCancelled(true);
        if (interactEntityEvent.getRightClicked() instanceof Villager villager) {
            if (ConfigToggle.OUTPUT_ENABLED.isEnabled()) {
                OutputLogic.getInstance().runVillagerOutput(villager, player);
            }
            if (ConfigToggle.HIGHLIGHT_VILLAGER_WORKSTATION_ON_OUTPUT.isEnabled()) {
                HighlightLogic.getInstance().runHighlightWorkstationBlock(villager, player);
            }
            if (ConfigToggle.PLAY_SOUND_ON_INFO_DISPLAY.isEnabled()) {
                SoundLogic.getInstance().runSoundEffect(player);
            }
            return;
        }
        if (interactEntityEvent.getRightClicked() instanceof ZombieVillager zombieVillager) {
            if (ConfigToggle.OUTPUT_ENABLED.isEnabled()) {
                OutputLogic.getInstance().runZombieVillagerOutput(zombieVillager, player);
            }
            if (ConfigToggle.PLAY_SOUND_ON_INFO_DISPLAY.isEnabled()) {
                SoundLogic.getInstance().runSoundEffect(player);
            }
        }
    }
}
