package adhdmc.villagerinfo.VillagerHandling;

import adhdmc.villagerinfo.Config.ConfigValidator;
import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Shulker;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.UUID;

import static org.bukkit.entity.EntityType.SHULKER;

public class HighlightHandling {
    static NamespacedKey highlightStatus = new NamespacedKey(VillagerInfo.getInstance(), "highlighted");
    public static HashMap<UUID, Shulker> workstationShulker = new HashMap<>();
    public static HashMap<UUID, PersistentDataContainer> villagerPDC = new HashMap<>();


    public static void villagerJobsiteHighlight(PersistentDataContainer villPDC, UUID villUUID, Location villPOI) {
        if (villPDC.getOrDefault(highlightStatus, PersistentDataType.BYTE, (byte)0) == 1) return;
        villPDC.set(highlightStatus, PersistentDataType.BYTE, (byte)1);
        villagerPDC.put(villUUID, villPDC);
        spawnShulker(villUUID, villPOI);
        Bukkit.getScheduler().runTaskLater(VillagerInfo.getInstance(), () -> {
            killShulker(villUUID);
            villPDC.set(highlightStatus, PersistentDataType.BYTE, (byte)0);
            villagerPDC.put(villUUID, villPDC);
        }, 20L * ConfigValidator.configTime);
    }

    private static void spawnShulker(UUID villUUID, Location location) {
        Shulker spawnedShulker = (Shulker) location.getWorld().spawnEntity(location, SHULKER, CreatureSpawnEvent.SpawnReason.CUSTOM, (entity) -> {
            Shulker highlightbox = (Shulker) entity;
            highlightbox.setAI(false);
            highlightbox.setAware(false);
            highlightbox.setCollidable(false);
            highlightbox.setGlowing(true);
            highlightbox.setInvisible(true);
            highlightbox.setInvulnerable(true);
        });
        workstationShulker.put(villUUID, spawnedShulker);
    }

    public static void killShulker(UUID villUUID) {
        workstationShulker.get(villUUID).remove();
        workstationShulker.remove(villUUID);
    }
}
