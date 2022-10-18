package adhdmc.villagerinfo.VillagerHandling;

import adhdmc.villagerinfo.Config.Message;
import adhdmc.villagerinfo.Config.Perms;
import adhdmc.villagerinfo.Config.ToggleSetting;
import adhdmc.villagerinfo.VillagerInfo;
import com.destroystokyo.paper.entity.villager.Reputation;
import com.destroystokyo.paper.entity.villager.ReputationType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class VillagerHandler implements Listener {

    MiniMessage miniMessage = VillagerInfo.getMiniMessage();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onVillagerClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        byte togglePDC = playerPDC.getOrDefault(VillagerInfo.INFO_ENABLED_KEY, PersistentDataType.BYTE, (byte)0);
        if (togglePDC == 0) {
            return;
        }
        if (event.getHand().equals(EquipmentSlot.OFF_HAND)) {
            return;
        }
        if (!event.getPlayer().isSneaking()) {
            return;
        }
        if (!(event.getRightClicked() instanceof Villager villager)) {
            return;
        }

        if (!player.hasPermission(Perms.USE.getPerm())) {
            return;
        }
        if (Arrays.stream(ToggleSetting.values()).noneMatch(ToggleSetting::isEnabled)) {
            VillagerInfo.getInstance().getLogger().warning("You have all VillagerInfo options turned off. That seems silly, why do you have the plugin installed if you don't want to use it?");
            return;
        }
        event.setCancelled(true);
        Location villagerPOI = villager.getMemory(MemoryKey.JOB_SITE);
        ArrayList<Component> messageList = new ArrayList<>();
        boolean hasProfession = villager.getProfession() != Villager.Profession.NONE
                && villager.getProfession() != Villager.Profession.NITWIT;
        boolean hasWorkSite = villager.getMemory(MemoryKey.JOB_SITE) != null;
        boolean hasBed = villager.getMemory(MemoryKey.HOME) != null;
        boolean isAdult = villager.isAdult();
        //time until adult
        if (ToggleSetting.BABY_AGE.isEnabled() && !isAdult) {
           messageList.add(villagerTimeTillAdult(villager));
        }
        //profession
        if (ToggleSetting.PROFESSION.isEnabled() && isAdult) {
            messageList.add(villagerProfession(villager));
        }
        //job-site
        // Only show job site and last worked info if the villager has a profession
        if (hasProfession && ToggleSetting.JOB_SITE.isEnabled() && isAdult) {
            messageList.add(villagerJobSite(villager));
        }
        //last-worked
        // Only show last worked info if the villager has a profession and a work site
        if (hasProfession && hasWorkSite && ToggleSetting.LAST_WORKED.isEnabled() && isAdult) {
            messageList.add(villagerLastWorked(villager));
        }
        //bed-location
        if (ToggleSetting.BED_LOCATION.isEnabled()) {
            messageList.add(villagerBed(villager));
        }
        //last-slept
        if (hasBed && ToggleSetting.LAST_SLEPT.isEnabled()) {
            messageList.add(villagerLastSlept(villager));
        }
        //inventory
        if (ToggleSetting.INVENTORY.isEnabled()) {
            messageList.add(villagerInventory(villager));
        }
        //restocks
        // Only show restocks info if the villager has a profession and a work site
        if (hasProfession && hasWorkSite && ToggleSetting.RESTOCKS.isEnabled() && isAdult) {
            messageList.add(villagerRestocks(villager));
        }
        //reputation
        if (ToggleSetting.REPUTATION.isEnabled()) {
            messageList.add(villagerPlayerReputation(villager, player));
        }
        if (ToggleSetting.HIGHLIGHT_WORKSTATION.isEnabled() && villagerPOI != null) {
            HighlightHandling.villagerJobsiteHighlight(villager.getPersistentDataContainer(), villager.getUniqueId(), villagerPOI);
        }
        //Messages
        Component prefix = miniMessage.deserialize(Message.PREFIX.getMessage());
        player.sendMessage(prefix);
        for (Component component : messageList) {
            player.sendMessage(component);
        }
    }

    /**
     * Checks and returns formatted 'time till adult' message component
     * @param villager Clicked Villager
     * @return Formatted Time Component
     */
    private Component villagerTimeTillAdult(Villager villager) {
        Component timeTillAdultFinal;
        long villAge = villager.getAge();
        villAge = villAge * -1;
        VillagerInfo.getInstance().getLogger().info("" + villAge);
        String timeCalc = timeMath(villAge);
        timeTillAdultFinal = miniMessage.deserialize(Message.VILLAGER_AGE.getMessage(), Placeholder.unparsed("age", timeCalc));
        return timeTillAdultFinal;
    }

    /**
     * Checks and returns villager profession component
     * @param villager Clicked Villager
     * @return Profession Component
     */
    private Component villagerProfession(Villager villager) {
        Component professionFinal;
        String villagerProfessionString = villager.getProfession().toString();
        if (villager.getProfession() == Villager.Profession.NONE) {
            professionFinal = miniMessage.deserialize(Message.VILLAGER_PROFESSION.getMessage(), Placeholder.parsed("profession", Message.NONE.getMessage()));
        } else {
            professionFinal = miniMessage.deserialize(Message.VILLAGER_PROFESSION.getMessage(), Placeholder.parsed("profession", villagerProfessionString));
        }
        return professionFinal;
    }

    /**
     * Checks and returns formatted jobsite location component
     * @param villager clicked villager
     * @return Jobsite Location Component
     */
    private Component villagerJobSite(Villager villager) {
        Location villagerJobLocation = villager.getMemory(MemoryKey.JOB_SITE);
        Component jobSiteFinal;
        if (villagerJobLocation == null) {
            jobSiteFinal = miniMessage.deserialize(Message.VILLAGER_JOBSITE.getMessage(),
                    Placeholder.parsed("jobsitelocation", Message.NONE.getMessage()));
        } else {
            Component jobX = miniMessage.deserialize(Message.LOCATION_X.getMessage(),
                    Placeholder.unparsed("int", String.valueOf(villagerJobLocation.getBlockX())));
            Component jobY = miniMessage.deserialize(Message.LOCATION_Y.getMessage(),
                    Placeholder.unparsed("int", String.valueOf(villagerJobLocation.getBlockY())));
            Component jobZ = miniMessage.deserialize(Message.LOCATION_Z.getMessage(),
                    Placeholder.unparsed("int", String.valueOf(villagerJobLocation.getBlockZ())));
            jobSiteFinal = miniMessage.deserialize(Message.VILLAGER_JOBSITE.getMessage(),
                    Placeholder.component("jobsitelocation", jobX.append(jobY).append(jobZ)));
        }
        return jobSiteFinal;
    }

    /**
     * Checks and returns formatted 'villager last worked' message component
     * @param villager Clicked Villager
     * @return Formatted Time Component
     */
    private Component villagerLastWorked(Villager villager) {
        Component villagerLastWorkedFinal;
        Long lastWorked = villager.getMemory(MemoryKey.LAST_WORKED_AT_POI);
        if (lastWorked == null) {
            villagerLastWorkedFinal = miniMessage.deserialize(Message.VILLAGER_LAST_WORKED.getMessage(),
                    Placeholder.parsed("worktime", Message.NEVER.getMessage()));
        } else {
            Long timeSinceWorked = villager.getWorld().getGameTime() - lastWorked;
            String formattedTime = timeMath(timeSinceWorked) + Message.AGO.getMessage();
            villagerLastWorkedFinal = miniMessage.deserialize(Message.VILLAGER_LAST_WORKED.getMessage(),
                    Placeholder.unparsed("worktime", formattedTime));
        }
        return villagerLastWorkedFinal;
    }

    /**
     * Checks and returns formatted home location component
     * @param villager clicked villager
     * @return Home Location Component
     */
    private Component villagerBed(Villager villager) {
        Component villagerBedFinal;
        Location bedLocation = villager.getMemory(MemoryKey.HOME);
        if (bedLocation == null) {
            villagerBedFinal = miniMessage.deserialize(Message.VILLAGER_HOME.getMessage(),
                    Placeholder.parsed("homelocation", Message.NONE.getMessage()));
        } else {
            Component bedX = miniMessage.deserialize(Message.LOCATION_X.getMessage(),
                    Placeholder.unparsed("int", String.valueOf(bedLocation.getBlockX())));
            Component bedY = miniMessage.deserialize(Message.LOCATION_Y.getMessage(),
                    Placeholder.unparsed("int", String.valueOf(bedLocation.getBlockY())));
            Component bedZ = miniMessage.deserialize(Message.LOCATION_Z.getMessage(),
                    Placeholder.unparsed("int", String.valueOf(bedLocation.getBlockZ())));
            villagerBedFinal = miniMessage.deserialize(Message.VILLAGER_HOME.getMessage(),
                    Placeholder.component("homelocation", bedX.append(bedY).append(bedZ)));
        }
        return villagerBedFinal;
    }

    /**
     * Checks and returns formatted 'villager last slept' message component
     * @param villager Clicked Villager
     * @return Formatted Time Component
     */
    private Component villagerLastSlept(Villager villager) {
        Component villagerLastSleptFinal;
        Long lastSlept = villager.getMemory(MemoryKey.LAST_SLEPT);
        if (lastSlept == null) {
            villagerLastSleptFinal = miniMessage.deserialize(Message.VILLAGER_SLEPT.getMessage(),
                    Placeholder.parsed("sleeptime", Message.NEVER.getMessage()));
        } else {
            Long timeSinceSlept = villager.getWorld().getGameTime() - lastSlept;
            String formattedTime = timeMath(timeSinceSlept) + Message.AGO.getMessage() ;
            villagerLastSleptFinal = miniMessage.deserialize(Message.VILLAGER_SLEPT.getMessage(),
                    Placeholder.unparsed("sleeptime", formattedTime));
        }
        return villagerLastSleptFinal;
    }

    /**
     * Checks and lists all the items in a villager's inventory
     * @param villager Clicked Villager
     * @return Inventory List Component
     */
    private Component villagerInventory(Villager villager) {
        Component villagerInventoryFinal;
        if (villager.getInventory().isEmpty()) {
            villagerInventoryFinal = miniMessage.deserialize(Message.VILLAGER_INVENTORY.getMessage(), Placeholder.parsed("contents", Message.EMPTY.getMessage()));
        } else {
            Component villagerInventory = Component.text("");
            String inventoryOutput = Message.INVENTORY_CONTENTS.getMessage();
            ItemStack[] inventoryItems = villager.getInventory().getContents();
            for (ItemStack items : inventoryItems) {
                if (items == null) continue;
                villagerInventory = villagerInventory.append(miniMessage.deserialize(inventoryOutput, Placeholder.parsed("item", items.getType().toString()), Placeholder.parsed("amount", String.valueOf(items.getAmount()))));
            }
            villagerInventoryFinal = miniMessage.deserialize(Message.VILLAGER_INVENTORY.getMessage(), Placeholder.component("contents", villagerInventory));
        }
        return villagerInventoryFinal;
    }

    /**
     * Gets the number of times a villager has restocked that day and returns a component
     * @param villager Clicked Villager
     * @return Restock Count Component
     */
    private Component villagerRestocks(Villager villager) {
        if (villager.getRestocksToday() == 0) {
            return miniMessage.deserialize(Message.VILLAGER_RESTOCKS.getMessage(), Placeholder.parsed("restockcount", Message.NONE.getMessage()));
        } else {
            return miniMessage.deserialize(Message.VILLAGER_RESTOCKS.getMessage(), Placeholder.parsed("restockcount", String.valueOf(villager.getRestocksToday())));
        }
    }

    /**
     * Gets a player's reputation and returns a component
     * @param villager Clicked Villager
     * @param player Player to evaluate
     * @return Reputation Component
     */
    private Component villagerPlayerReputation(Villager villager, Player player) {
        Component villagerReputationFinal;
        int reputationRawTotal = reputationTotal(villager, player.getUniqueId());
        String playerReputation = ReputationHandler.villagerReputation(reputationRawTotal);
        villagerReputationFinal = miniMessage.deserialize(Message.PLAYER_REPUTATION.getMessage(), Placeholder.unparsed("reputation", playerReputation));
        return villagerReputationFinal;

    }

    /**
     * Calculates the total reputation of a player, using all reputation types
     * @param villager Clicked Villager
     * @param player Player to evaluate
     * @return Total reputation score
     */
    private int reputationTotal(Villager villager, UUID player) {
        Reputation playerReputation = villager.getReputation(player);
        if (playerReputation == null) return 0;
        int playerReputationMP = playerReputation.getReputation(ReputationType.MAJOR_POSITIVE);
        int playerReputationP = playerReputation.getReputation(ReputationType.MINOR_POSITIVE);
        int playerReputationMN = playerReputation.getReputation(ReputationType.MAJOR_NEGATIVE);
        int playerReputationN = playerReputation.getReputation(ReputationType.MINOR_NEGATIVE);
        int playerReputationT = playerReputation.getReputation(ReputationType.TRADING);
        //5MP+P+T-N-5MN = Total Reputation Score. Maxes at -700, 725
        return (playerReputationMP * 5) + playerReputationP + playerReputationT - playerReputationN - (playerReputationMN * 5);
    }

    /**
     * Formats a tick-based-time into a human-readable format
     * @param mathTime Time in Ticks
     * @return Formatted String
     */
    private String timeMath(Long mathTime) {
        String mathResult = "";
        //Remainder after dividing by 72,000 (one hour)
        long mathTime2 = mathTime % 72000;
        //Normal number from dividing (hours)
        long mathTimeB = mathTime / 72000;
        //Remainder after dividing by 1200 (1 minute)
        long mathTime3 = mathTime2 % 1200;
        //Normal number from dividing (minutes)
        long mathTimeC = mathTime2 / 1200;
        //Normal number from dividing (seconds)
        long mathTimeD = mathTime3 / 20;
        if (mathTimeB > 0) mathResult += mathTimeB + Message.HOUR.getMessage();
        if (mathTimeC > 0) mathResult += mathTimeC + Message.MINUTE.getMessage();
        if (mathTimeD > 0) mathResult += mathTimeD + Message.SECOND.getMessage();
        if (mathResult.isEmpty()) {
            mathResult += "0" + Message.SECOND.getMessage();
        }
        return mathResult;
    }
}
