package adhdmc.villagerinfo.VillagerHandling;

import adhdmc.villagerinfo.Config.ConfigValidator;
import adhdmc.villagerinfo.Config.Message;
import adhdmc.villagerinfo.Config.Perms;
import adhdmc.villagerinfo.Config.ToggleSetting;
import adhdmc.villagerinfo.VillagerInfo;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;


public class VillagerHandler implements Listener {
    private boolean usingPurpur = VillagerInfo.usingPurpur;

    MiniMessage miniMessage = VillagerInfo.getMiniMessage();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onVillagerClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        byte togglePDC = playerPDC.getOrDefault(VillagerInfo.INFO_ENABLED_KEY, PersistentDataType.BYTE, (byte)1);
        if (togglePDC == 0) {
            return;
        }
        if (event.getHand().equals(EquipmentSlot.OFF_HAND)) {
            return;
        }
        if (!event.getPlayer().isSneaking()) {
            return;
        }
        if (event.getRightClicked() instanceof Villager villager) {
            if (!player.hasPermission(Perms.USE.getPerm())) {
                return;
            }
            if (Arrays.stream(ToggleSetting.values()).noneMatch(ToggleSetting::isEnabled)) {
                VillagerInfo.getInstance().getLogger().warning("You have all VillagerInfo options turned off. That seems silly, why do you have the plugin installed if you don't want to use it?");
                return;
            }
            event.setCancelled(true);
            processVillager(player, villager);
        } else if (event.getRightClicked() instanceof ZombieVillager zombieVillager) {
            if (!player.hasPermission(Perms.USE.getPerm())) {
                return;
            }
            if (Arrays.stream(ToggleSetting.values()).noneMatch(ToggleSetting::isEnabled)) {
                VillagerInfo.getInstance().getLogger().warning("You have all VillagerInfo options turned off. That seems silly, why do you have the plugin installed if you don't want to use it?");
                return;
            }
            event.setCancelled(true);
            processZombieVillager(player, zombieVillager);
        }
    }

    private void processVillager(Player player, Villager villager) {
        Location villagerPOI = villager.getMemory(MemoryKey.JOB_SITE);
        ArrayList<Component> messageList = new ArrayList<>();
        boolean hasProfession = villager.getProfession() != Villager.Profession.NONE
                && villager.getProfession() != Villager.Profession.NITWIT;
        boolean hasWorkSite = villager.getMemory(MemoryKey.JOB_SITE) != null;
        boolean hasBed = villager.getMemory(MemoryKey.HOME) != null;
        boolean isAdult = villager.isAdult();
        //lobotomized
        if (ToggleSetting.PURPUR_LOBOTOMIZED.isEnabled() && usingPurpur && isAdult) {
            messageList.add(ComponentHandler.villagerLobotomized(villager));
        }
        //time until adult
        if (ToggleSetting.BABY_AGE.isEnabled() && !isAdult) {
           messageList.add(ComponentHandler.timeTillAdult(villager));
        }
        //health
        if (ToggleSetting.HEALTH.isEnabled()) {
            messageList.add(ComponentHandler.villagerHealth(villager));
        }
        //profession
        if (ToggleSetting.PROFESSION.isEnabled() && isAdult) {
            messageList.add(ComponentHandler.villagerProfession(villager.getProfession()));
        }
        //job-site
        // Only show job site and last worked info if the villager has a profession
        if (hasProfession && ToggleSetting.JOB_SITE.isEnabled() && isAdult) {
            messageList.add(ComponentHandler.villagerJobSite(villager));
        }
        //last-worked
        // Only show last worked info if the villager has a profession and a work site
        if (hasProfession && hasWorkSite && ToggleSetting.LAST_WORKED.isEnabled() && isAdult) {
            messageList.add(ComponentHandler.villagerLastWorked(villager));
        }
        //bed-location
        if (ToggleSetting.BED_LOCATION.isEnabled()) {
            messageList.add(ComponentHandler.villagerBed(villager));
        }
        //last-slept
        if (hasBed && ToggleSetting.LAST_SLEPT.isEnabled()) {
            messageList.add(ComponentHandler.villagerLastSlept(villager));
        }
        //inventory
        if (ToggleSetting.INVENTORY.isEnabled()) {
            messageList.add(ComponentHandler.villagerInventory(villager));
        }
        //restocks
        // Only show restocks info if the villager has a profession and a work site
        if (hasProfession && hasWorkSite && ToggleSetting.RESTOCKS.isEnabled() && isAdult) {
            messageList.add(ComponentHandler.villagerRestocks(villager));
        }
        //reputation
        if (ToggleSetting.REPUTATION.isEnabled()) {
            messageList.add(ComponentHandler.villagerPlayerReputation(villager.getReputation(player.getUniqueId())));
        }
        if (ToggleSetting.HIGHLIGHT_WORKSTATION.isEnabled() && villagerPOI != null) {
            HighlightHandling.villagerJobsiteHighlight(villager.getPersistentDataContainer(), villager.getUniqueId(), villagerPOI);
        }
        //Messages
        Component prefix = miniMessage.deserialize(Message.PREFIX.getMessage());
        player.playSound(player.getLocation(), ConfigValidator.getConfigSound(), ConfigValidator.getSoundVolume(), ConfigValidator.getSoundPitch());
        player.sendMessage(prefix);
        if (messageList.size() == 0) {
            player.sendMessage(miniMessage.deserialize(Message.NO_INFORMATION.getMessage()));
            return;
        }
        for (Component component : messageList) {
            player.sendMessage(component);
        }
    }

    private void processZombieVillager(Player player, ZombieVillager zombieVillager) {
        ArrayList<Component> messageList = new ArrayList<>();
        boolean isAdult = zombieVillager.isAdult();
        //profession
        if (ToggleSetting.PROFESSION.isEnabled() && isAdult) {
            messageList.add(ComponentHandler.villagerProfession(zombieVillager.getVillagerProfession()));
        }
        //time until converted
        if (ToggleSetting.ZOMBIE_CONVERSION.isEnabled() && zombieVillager.isConverting()) {
            messageList.add(ComponentHandler.timeTillConverted(zombieVillager));
        }
        if (ToggleSetting.HEALTH.isEnabled()) {
            messageList.add(ComponentHandler.villagerHealth(zombieVillager));
        }
        //reputation
        // TODO: wait for Reputation API to be added for Zombie Villagers or use PDC to calculate reputation NBT by hand
        //if (ToggleSetting.REPUTATION.isEnabled()) {
        //    messageList.add(villagerPlayerReputation(zombieVillager.getReputation(player.getUniqueId())));
        //}
        //Messages
        Component prefix = miniMessage.deserialize(Message.PREFIX.getMessage());
        player.playSound(player.getLocation(), ConfigValidator.getConfigSound(), ConfigValidator.getSoundVolume(), ConfigValidator.getSoundPitch());
        player.sendMessage(prefix);
        if (messageList.size() == 0) {
            player.sendMessage(miniMessage.deserialize(Message.NO_INFORMATION.getMessage()));
            return;
        }
        for (Component component : messageList) {
            player.sendMessage(component);
        }
    }
}
