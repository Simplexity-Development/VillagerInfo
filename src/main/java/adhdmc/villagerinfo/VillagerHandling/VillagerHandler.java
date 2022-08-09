package adhdmc.villagerinfo.VillagerHandling;

import adhdmc.villagerinfo.Config.ConfigValidator;
import adhdmc.villagerinfo.Config.ConfigValidator.Message;
import adhdmc.villagerinfo.VillagerInfo;
import com.destroystokyo.paper.entity.villager.Reputation;
import com.destroystokyo.paper.entity.villager.ReputationType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class VillagerHandler implements Listener {
    MiniMessage mM = MiniMessage.miniMessage();
    Map<Message, String> messages = ConfigValidator.getLocaleMap();
    Map<ConfigValidator.ToggleSetting, Boolean> toggleSettings = ConfigValidator.getToggleSettings();
    NamespacedKey infoToggle = new NamespacedKey(VillagerInfo.plugin, "infoToggle");
    String toggleOff =  VillagerInfo.toggleInfoOff;


    @EventHandler
    public void onVillagerClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        if (event.getHand().equals(EquipmentSlot.OFF_HAND)) {
            return;
        }
        if (!event.getPlayer().isSneaking()) {
            return;
        }
        if (!(event.getRightClicked() instanceof Villager villager)) {
            return;
        }
        String togglePDC = playerPDC.get(infoToggle, PersistentDataType.STRING);
        if (togglePDC != null && togglePDC.equals(toggleOff)) {
            return;
        }
        if (!player.hasPermission("villagerinfo.use")) {
            return;
        }
        event.setCancelled(true);
        Location villagerPOI = villager.getMemory(MemoryKey.JOB_SITE);
        ArrayList<Component> messageList = new ArrayList<>();
        //profession
        if (toggleSettings.get(ConfigValidator.ToggleSetting.PROFESSION)) {
            messageList.add(villagerProfession(villager));
        }
        //job-site
        if (toggleSettings.get(ConfigValidator.ToggleSetting.JOB_SITE)) {
            messageList.add(villagerJobSite(villager));
        }
        //last-worked
        if (toggleSettings.get(ConfigValidator.ToggleSetting.LAST_WORKED)) {
            messageList.add(villagerLastWorked(villager));
        }
        //bed-location
        if (toggleSettings.get(ConfigValidator.ToggleSetting.BED_LOCATION)) {
            messageList.add(villagerBed(villager));
        }
        //last-slept
        if (toggleSettings.get(ConfigValidator.ToggleSetting.LAST_WORKED)) {
            messageList.add(villagerLastSlept(villager));
        }
        //inventory
        if (toggleSettings.get(ConfigValidator.ToggleSetting.INVENTORY)) {
            messageList.add(villagerInventory(villager));
        }
        //restocks
        if (toggleSettings.get(ConfigValidator.ToggleSetting.RESTOCKS)) {
            messageList.add(villagerRestocks(villager));
        }
        //reputation
        if (toggleSettings.get(ConfigValidator.ToggleSetting.REPUTATION)) {
            messageList.add(villagerPlayerReputation(villager, player));
        }
        if (toggleSettings.get(ConfigValidator.ToggleSetting.HIGHLIGHT_WORKSTATION) && villagerPOI != null) {
            HighlightHandling.villagerJobsiteHighlight(villager.getPersistentDataContainer(), villager.getUniqueId(), villagerPOI);
        }
        //Messages
        Component prefix = mM.deserialize(messages.get(Message.PREFIX));
        player.sendMessage(prefix);
        for (Component component : messageList) {
            player.sendMessage(component);
        }
    }

    private Component villagerProfession(Villager villager) {
        Component professionFinal;
        String villagerProfessionString = villager.getProfession().toString();
        if (villager.getProfession() == Villager.Profession.NONE) {
            professionFinal = mM.deserialize(messages.get(Message.VILLAGER_PROFESSION), Placeholder.parsed("profession", messages.get(Message.NONE)));
        } else {
            professionFinal = mM.deserialize(messages.get(Message.VILLAGER_PROFESSION), Placeholder.parsed("profession", villagerProfessionString));
        }
        return professionFinal;
    }

    private Component villagerJobSite(Villager villager) {
        Location villagerJobLocation = villager.getMemory(MemoryKey.JOB_SITE);
        Component jobSiteFinal;
        if (villagerJobLocation == null) {
            jobSiteFinal = mM.deserialize(messages.get(Message.VILLAGER_JOBSITE),
                    Placeholder.parsed("jobsitelocation", messages.get(Message.NONE)));
        } else {
            Component jobX = mM.deserialize(messages.get(Message.LOCATION_X),
                    Placeholder.unparsed("int", String.valueOf(villagerJobLocation.getBlockX())));
            Component jobY = mM.deserialize(messages.get(Message.LOCATION_Y),
                    Placeholder.unparsed("int", String.valueOf(villagerJobLocation.getBlockY())));
            Component jobZ = mM.deserialize(messages.get(Message.LOCATION_Z),
                    Placeholder.unparsed("int", String.valueOf(villagerJobLocation.getBlockZ())));
            jobSiteFinal = mM.deserialize(messages.get(Message.VILLAGER_JOBSITE),
                    Placeholder.component("jobsitelocation", jobX.append(jobY).append(jobZ)));
        }
        return jobSiteFinal;
    }

    private Component villagerLastWorked(Villager villager) {
        Component villagerLastWorkedFinal;
        Long lastWorked = villager.getMemory(MemoryKey.LAST_WORKED_AT_POI);
        if (lastWorked == null) {
            villagerLastWorkedFinal = mM.deserialize(messages.get(Message.VILLAGER_LAST_WORKED),
                    Placeholder.parsed("worktime", messages.get(Message.NEVER)));
        } else {
            Long timeSinceWorked = villager.getWorld().getGameTime() - lastWorked;
            String formattedTime = timeMath(timeSinceWorked);
            villagerLastWorkedFinal = mM.deserialize(messages.get(Message.VILLAGER_LAST_WORKED),
                    Placeholder.unparsed("worktime", formattedTime));
        }
        return villagerLastWorkedFinal;
    }

    private Component villagerBed(Villager villager) {
        Component villagerBedFinal;
        Location bedLocation = villager.getMemory(MemoryKey.HOME);
        if (bedLocation == null) {
            villagerBedFinal = mM.deserialize(messages.get(Message.VILLAGER_HOME),
                    Placeholder.parsed("homelocation", messages.get(Message.NONE)));
        } else {
            Component bedX = mM.deserialize(messages.get(Message.LOCATION_X),
                    Placeholder.unparsed("int", String.valueOf(bedLocation.getBlockX())));
            Component bedY = mM.deserialize(messages.get(Message.LOCATION_Y),
                    Placeholder.unparsed("int", String.valueOf(bedLocation.getBlockY())));
            Component bedZ = mM.deserialize(messages.get(Message.LOCATION_Z),
                    Placeholder.unparsed("int", String.valueOf(bedLocation.getBlockZ())));
            villagerBedFinal = mM.deserialize(messages.get(Message.VILLAGER_HOME),
                    Placeholder.component("homelocation", bedX.append(bedY).append(bedZ)));
        }
        return villagerBedFinal;
    }

    private Component villagerLastSlept(Villager villager) {
        Component villagerLastSleptFinal;
        Long lastSlept = villager.getMemory(MemoryKey.LAST_SLEPT);
        if (lastSlept == null) {
            villagerLastSleptFinal = mM.deserialize(messages.get(Message.VILLAGER_SLEPT),
                    Placeholder.parsed("sleeptime", messages.get(Message.NEVER)));
        } else {
            Long timeSinceSlept = villager.getWorld().getGameTime() - lastSlept;
            String formattedTime = timeMath(timeSinceSlept);
            villagerLastSleptFinal = mM.deserialize(messages.get(Message.VILLAGER_SLEPT),
                    Placeholder.unparsed("sleeptime", formattedTime));
        }
        return villagerLastSleptFinal;
    }

    private Component villagerInventory(Villager villager) {
        Component villagerInventoryFinal;
        if (villager.getInventory().isEmpty()) {
            villagerInventoryFinal = mM.deserialize(messages.get(Message.VILLAGER_INVENTORY), Placeholder.parsed("contents", messages.get(Message.EMPTY)));
        } else {
            Component villagerInventory = Component.text("");
            String inventoryOutput = messages.get(Message.INVENTORY_CONTENTS);
            ItemStack[] inventoryItems = villager.getInventory().getContents();
            for (ItemStack items : inventoryItems) {
                if (items == null) continue;
                villagerInventory = villagerInventory.append(mM.deserialize(inventoryOutput, Placeholder.parsed("item", items.getType().toString()), Placeholder.parsed("amount", String.valueOf(items.getAmount()))));
            }
            villagerInventoryFinal = mM.deserialize(messages.get(Message.VILLAGER_INVENTORY), Placeholder.component("contents", villagerInventory));
        }
        return villagerInventoryFinal;
    }

    private Component villagerRestocks(Villager villager) {
        if(villager.getRestocksToday() == 0) {
            return mM.deserialize(messages.get(Message.VILLAGER_RESTOCKS), Placeholder.parsed("restockcount", messages.get(Message.NONE)));
        }else{
            return mM.deserialize(messages.get(Message.VILLAGER_RESTOCKS), Placeholder.parsed("restockcount", String.valueOf(villager.getRestocksToday())));
        }
    }

    private Component villagerPlayerReputation(Villager villager, Player player) {
        Component villagerReputationFinal;
        int reputationRawTotal = reputationTotal(villager, player.getUniqueId());
        String playerReputation = ReputationHandler.villagerReputation(reputationRawTotal);
        villagerReputationFinal = mM.deserialize(messages.get(Message.PLAYER_REPUTATION), Placeholder.unparsed("reputation", playerReputation));
        return villagerReputationFinal;

    }

    private int reputationTotal(Villager vil, UUID p) {
        Reputation playerReputation = vil.getReputation(p);
        if (playerReputation == null) return 0;
        int playerReputationMP = playerReputation.getReputation(ReputationType.MAJOR_POSITIVE);
        int playerReputationP = playerReputation.getReputation(ReputationType.MINOR_POSITIVE);
        int playerReputationMN = playerReputation.getReputation(ReputationType.MAJOR_NEGATIVE);
        int playerReputationN = playerReputation.getReputation(ReputationType.MINOR_NEGATIVE);
        int playerReputationT = playerReputation.getReputation(ReputationType.TRADING);
        //5MP+P+T-N-5MN = Total Reputation Score. Maxes at -700, 725
        return (playerReputationMP * 5) + playerReputationP + playerReputationT - playerReputationN - (playerReputationMN * 5);
    }

    private String timeMath(Long mathTime) {
        Map<Message, String> msgs = ConfigValidator.getLocaleMap();
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
        if(mathTimeB > 0) mathResult += mathTimeB + msgs.get(Message.HOUR);
        if(mathTimeC > 0) mathResult += mathTimeC + msgs.get(Message.MINUTE);
        if(mathTimeD > 0) mathResult += mathTimeD + msgs.get(Message.SECOND);
        if(mathResult.isEmpty()){
            mathResult += "0" + msgs.get(Message.SECOND) + msgs.get(Message.AGO);
            return mathResult;
        }
        mathResult += msgs.get(Message.AGO);
        return mathResult;
    }
}
