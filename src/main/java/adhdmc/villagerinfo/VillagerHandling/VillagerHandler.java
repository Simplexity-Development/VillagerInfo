package adhdmc.villagerinfo.VillagerHandling;

import adhdmc.villagerinfo.Config.ConfigValidator;
import adhdmc.villagerinfo.MiscHandling.TimeFormatting;
import adhdmc.villagerinfo.VillagerInfo;
import com.destroystokyo.paper.entity.villager.Reputation;
import com.destroystokyo.paper.entity.villager.ReputationType;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

import static org.bukkit.entity.EntityType.SHULKER;

public class VillagerHandler implements Listener {
    public static HashMap<UUID, Shulker> workstationShulker = new HashMap<UUID, Shulker>();
    public static HashMap<UUID, PersistentDataContainer> villagerPDC = new HashMap<UUID, PersistentDataContainer>();
    public static Location villagerJobsiteLocation;
    NamespacedKey infoToggle = new NamespacedKey(VillagerInfo.plugin, "infoToggle");
    NamespacedKey isHighlighted = new NamespacedKey(VillagerInfo.plugin, "isHighlighted");

    byte f = 0;
    byte t = 1;

    @EventHandler
    public void onVillagerClick(PlayerInteractEntityEvent event) {
        HashMap<String, String> messages = ConfigValidator.localeMap;
        FileConfiguration config = VillagerInfo.plugin.getConfig();
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
        if (playerPDC.get(infoToggle, PersistentDataType.BYTE) != null
                && playerPDC.get(infoToggle, PersistentDataType.BYTE).equals(t)) {
            return;
        }
        if (!player.hasPermission("villagerinfo.use")) {
            return;
        }
        event.setCancelled(true);
        long worldTimeTotal = villager.getWorld().getFullTime();
        String villagerProfession = villager.getProfession().toString();
        Location villagerJobLocation = villager.getMemory(MemoryKey.JOB_SITE);
        if (villagerJobLocation != null){
        int villagerJobsiteX = villagerJobLocation.getBlockX();
        int villagerJobsiteY = villagerJobLocation.getBlockY();
        int villagerJobsiteZ = villagerJobLocation.getBlockZ();
        } else {
            String villagerJobSite = messages.get("none-msg");
        }

        if (villager.getMemory(MemoryKey.LAST_WORKED_AT_POI) != null) {
            String villagerLastWorked = TimeFormatting.timeMath(worldTimeTotal - villager.getMemory(MemoryKey.LAST_WORKED_AT_POI));
        } else {
            String villagerLastWorked = messages.get("never-msg");
        }
        Location bedLocation = villager.getMemory(MemoryKey.HOME);




    }
    }
    private void villagerJobsiteHighlight(PersistentDataContainer villPDC, UUID villUUID){
        if (villPDC.get(isHighlighted, PersistentDataType.BYTE) == null ||
            villPDC.get(isHighlighted, PersistentDataType.BYTE).equals(f)){
                villPDC.set(isHighlighted,PersistentDataType.BYTE,t);
                villagerPDC.put(villUUID,villPDC);
                spawnShulker(villUUID);
                new BukkitRunnable(){
                @Override
                public void run(){
                    killShulker(villUUID);
                    villPDC.set(isHighlighted,PersistentDataType.BYTE,f);
                    villagerPDC.put(villUUID,villPDC);
                }
            }.runTaskLater(VillagerInfo.plugin,20L* ConfigValidator.configTime);
        }
    }
    private int reputationTotal(Villager vil, UUID p){
        Reputation playerReputation = vil.getReputation(p);
        if(playerReputation == null) return 0;
        int playerReputationMP = playerReputation.getReputation(ReputationType.MAJOR_POSITIVE);
        int playerReputationP = playerReputation.getReputation(ReputationType.MINOR_POSITIVE);
        int playerReputationMN = playerReputation.getReputation(ReputationType.MAJOR_NEGATIVE);
        int playerReputationN = playerReputation.getReputation(ReputationType.MINOR_NEGATIVE);
        int playerReputationT = playerReputation.getReputation(ReputationType.TRADING);
        //5MP+P+T-N-5MN = Total Reputation Score. Maxes at -700, 725
        return (playerReputationMP * 5) + playerReputationP + playerReputationT - playerReputationN - (playerReputationMN * 5);
    }
    private void spawnShulker(UUID uuid){
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
