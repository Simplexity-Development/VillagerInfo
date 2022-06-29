package adhdmc.villagerinfo.VillagerHandling;

import adhdmc.villagerinfo.MiscHandling.MessageHandler;
import adhdmc.villagerinfo.MiscHandling.TimeFormatting;
import adhdmc.villagerinfo.VillagerInfo;
import com.destroystokyo.paper.entity.villager.Reputation;
import com.destroystokyo.paper.entity.villager.ReputationType;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

import static org.bukkit.ChatColor.*;
import static org.bukkit.entity.EntityType.SHULKER;

public class VillagerHandler extends MessageHandler implements Listener {
    public static HashMap<UUID, Shulker> workstationShulker = new HashMap<UUID, Shulker>();
    public static HashMap<UUID, PersistentDataContainer> villagerPDC = new HashMap<UUID, PersistentDataContainer>();
    public static Location villagerJobsiteLocation;


    @EventHandler
    public void onVillagerClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        if (event.getRightClicked().getType() != EntityType.VILLAGER) {
            return;
        }
        if(event.getHand().equals(EquipmentSlot.OFF_HAND)) {
            return;
        }
        if(!event.getPlayer().isSneaking()) {
            return;
        }
        if(playerPDC.get(new NamespacedKey(VillagerInfo.plugin, "infoToggle"), PersistentDataType.INTEGER) != null && playerPDC.get(new NamespacedKey(VillagerInfo.plugin, "infoToggle"), PersistentDataType.INTEGER).equals(1)){
            return;
        }
        if(!player.hasPermission("villagerinfo.use")){
            return;
        }
        FileConfiguration config = VillagerInfo.plugin.getConfig();
        boolean configProfession = config.getBoolean("Profession");
        boolean configJobSite = config.getBoolean("Job Site");
        boolean configLastWorked = config.getBoolean("Last Worked");
        boolean configRestocks = config.getBoolean("Number of Restocks");
        boolean configHome = config.getBoolean("Bed Location");
        boolean configLastSlept = config.getBoolean("Last Slept");
        boolean configInventory = config.getBoolean("Villager Inventory Contents");
        boolean configReputation = config.getBoolean("Player Reputation");
        boolean configHighlight = config.getBoolean("Highlight Workstation");
        int configTime = config.getInt("Length of time to highlight workstation");
        int highlightTime;
        if (!(configTime > 0)){
            highlightTime = 10;
        } else {
            highlightTime = configTime;
        }
        if(!configProfession && !configJobSite && !configLastWorked && !configRestocks && !configHome && !configLastSlept && !configInventory && !configReputation && !configHighlight) {
            player.sendMessage(MessageHandler.prefix);
            player.sendMessage(GOLD + "Why is this plugin even installed if every option is turned off?");
            return;
        }
        event.setCancelled(true);
        UUID pUUID = player.getUniqueId();
        Villager villagerClicked = (Villager) event.getRightClicked();
        String villagerProfession = villagerClicked.getProfession().toString();
        int villagerRestocks = VillagerInfo.isPaper ? villagerClicked.getRestocksToday() : Integer.MIN_VALUE;
        int playerReputationTotal = 0;
        Location villagerJobSite = villagerClicked.getMemory(MemoryKey.JOB_SITE);
        Long villagerWorked = villagerClicked.getMemory(MemoryKey.LAST_WORKED_AT_POI);
        Location villagerHome = villagerClicked.getMemory(MemoryKey.HOME);
        Long villagerSlept = villagerClicked.getMemory(MemoryKey.LAST_SLEPT);
        ItemStack[] villagerInventoryContents = villagerClicked.getInventory().getContents();
        PersistentDataContainer villPDC =  villagerClicked.getPersistentDataContainer();
        UUID villUUID = villagerClicked.getUniqueId();
        String villInfoPrefix = MessageHandler.prefix;
        if (VillagerInfo.isPaper) {
            Reputation playerReputation = villagerClicked.getReputation(pUUID);
            int playerReputationMP = playerReputation.getReputation(ReputationType.MAJOR_POSITIVE);
            int playerReputationP = playerReputation.getReputation(ReputationType.MINOR_POSITIVE);
            int playerReputationMN = playerReputation.getReputation(ReputationType.MAJOR_NEGATIVE);
            int playerReputationN = playerReputation.getReputation(ReputationType.MINOR_NEGATIVE);
            int playerReputationT = playerReputation.getReputation(ReputationType.TRADING);
            //5MP+P+T-N-5MN = Total Reputation Score. Maxes at -700, 725
            playerReputationTotal = (playerReputationMP * 5) + playerReputationP + playerReputationT - playerReputationN - (playerReputationMN * 5);
        }
        Sound configSound = null;
        if(VillagerInfo.plugin.getConfig().getBoolean("Sound Toggle")) {
            try {
                configSound = Sound.valueOf(VillagerInfo.plugin.getConfig().getString("Sound"));
            } catch (IllegalArgumentException e) {
                player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_BREAK, 2, 2);
            }
            if (configSound != null) {
                player.playSound(player.getLocation(), configSound, 2, 2);
            }
        }
        player.sendMessage(villInfoPrefix);
        StringBuilder villagerInventoryString = new StringBuilder(villagerInventoryMsg);
        if(configInventory){
            for (int i = 0; i < villagerInventoryContents.length; i++) {
                ItemStack villagerInventoryItem = villagerClicked.getInventory().getItem(i);
                if (villagerInventoryItem != null) {
                    villagerInventoryString.append("\n  ").append(AQUA).append(villagerSeparatorMsg).append(villagerInventoryItem.getType()).append(GRAY).append(" (").append(villagerInventoryItem.getAmount()).append(")");
                }
            }
        }
        if(configProfession) {
            player.sendMessage(villagerProfessionMsg + villagerSeparatorMsg + villagerProfession);
        }
        if(configJobSite){
            if (villagerJobSite != null) {
                player.sendMessage(villagerJobsiteMsg + villagerSeparatorMsg + villagerJobSite.getBlockX() + "x, " + villagerJobSite.getBlockY() + "y, " + villagerJobSite.getBlockZ() + "z");
                villagerJobsiteLocation = villagerJobSite;
                if(configHighlight){
                    if (villPDC.get(new NamespacedKey(VillagerInfo.plugin, "IsHighlighted"), PersistentDataType.INTEGER) == null ||
                        villPDC.get(new NamespacedKey(VillagerInfo.plugin, "IsHighlighted"), PersistentDataType.INTEGER).equals(0)){
                        villPDC.set(new NamespacedKey(VillagerInfo.plugin, "IsHighlighted"), PersistentDataType.INTEGER, 1);
                        villagerPDC.put(villUUID, villPDC);
                        spawnShulker(villagerClicked.getUniqueId());
                            new BukkitRunnable() {
                        @Override
                        public void run(){
                                killShulker(villagerClicked.getUniqueId());
                                villPDC.set(new NamespacedKey(VillagerInfo.plugin, "IsHighlighted"), PersistentDataType.INTEGER, 0);
                                villagerPDC.put(villUUID, villPDC);
                                }
                        }.runTaskLater(VillagerInfo.plugin, 20L * highlightTime);
                    }
                }
            } else {
            player.sendMessage(villagerJobsiteMsg + villagerSeparatorMsg + villagerNoneMsg);
            }
        }
        if(configLastWorked) {
            if (villagerWorked != null) {
                String villagerWorkedString = villagerLastWorkedMsg + villagerSeparatorMsg;
                long mathTime = villagerClicked.getWorld().getGameTime() - villagerWorked;
                player.sendMessage(villagerWorkedString + TimeFormatting.timeMath(mathTime));
            } else {
                player.sendMessage(villagerLastWorkedMsg + villagerSeparatorMsg + villagerNeverMsg);
            }
        }
        if(configRestocks && villagerRestocks != Integer.MIN_VALUE) {
            if (villagerRestocks == 0){
                player.sendMessage(villagerNumRestocksMsg + villagerSeparatorMsg + GRAY + villagerRestocks);
            } else {
                player.sendMessage(villagerNumRestocksMsg + villagerSeparatorMsg + villagerRestocks);
            }

        }
        if(configHome) {
            if (villagerHome != null) {
                player.sendMessage(villagerHomeMsg + villagerSeparatorMsg + villagerHome.getBlockX() + "x, " + villagerHome.getBlockY() + "y, " + villagerHome.getBlockZ() + "z");
            } else {
                player.sendMessage(villagerHomeMsg + villagerSeparatorMsg + villagerNoneMsg);
            }
        }
        if(configLastSlept) {
            if (villagerSlept != null) {
                String villagerSleptString = villagerSleptMsg + villagerSeparatorMsg;
                long mathTime = villagerClicked.getWorld().getGameTime() - villagerSlept;
                player.sendMessage(villagerSleptString + TimeFormatting.timeMath(mathTime));
            } else {
                player.sendMessage(villagerSleptMsg + villagerSeparatorMsg + villagerNeverMsg);
            }
        }
        if(configInventory) {
            if (villagerInventoryString.toString().equals(villagerInventoryMsg)) {
                player.sendMessage(villagerInventoryString + "\n" + villagerSeparatorMsg + villagerEmptyMsg);
            } else {
                player.sendMessage(villagerInventoryString.toString());
            }
        }
        if(configReputation){
            player.sendMessage(playerReputationMsg + ReputationHandler.villagerReputation(playerReputationTotal));
        }
    }
    public void spawnShulker(UUID uuid){
        Shulker spawnedShulker = (Shulker) villagerJobsiteLocation.getWorld().spawnEntity(villagerJobsiteLocation, SHULKER, CreatureSpawnEvent.SpawnReason.CUSTOM, (Entity) ->{
            Shulker highlightbox = (Shulker) Entity;
            highlightbox.setAI(false);
            highlightbox.setAware(false);
            highlightbox.setCollidable(false);
            highlightbox.setGlowing(true);
            highlightbox.setInvisible(true);
            highlightbox.setInvulnerable(true);
        });
        workstationShulker.put(uuid, spawnedShulker);
    }
    public static void killShulker(UUID uuid){
        workstationShulker.get(uuid).remove();
        workstationShulker.remove(uuid);
    }
}
