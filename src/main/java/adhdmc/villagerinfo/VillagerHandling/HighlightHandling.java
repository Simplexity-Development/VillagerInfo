package adhdmc.villagerinfo.VillagerHandling;

import adhdmc.villagerinfo.Config.ConfigValidator;
import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Shulker;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

import static org.bukkit.entity.EntityType.SHULKER;

public class HighlightHandling{
    static NamespacedKey highlightStatus = new NamespacedKey(VillagerInfo.plugin, "highlightStatus");
    static String currentlyHighlighted = VillagerInfo.isCurrentlyHighlighted;
    static String notHighlighted = VillagerInfo.isNotCurrentlyHighlighted;
    public static HashMap<UUID, Shulker> workstationShulker = new HashMap<>();
    public static HashMap<UUID, PersistentDataContainer> villagerPDC = new HashMap<>();



    public static void villagerJobsiteHighlight(PersistentDataContainer villPDC, UUID villUUID, Location villPOI) {
        if (villPDC.getOrDefault(highlightStatus, PersistentDataType.STRING, notHighlighted).equals(currentlyHighlighted)) return;
            villPDC.set(highlightStatus, PersistentDataType.STRING, currentlyHighlighted);
            villagerPDC.put(villUUID, villPDC);
            spawnShulker(villUUID, villPOI);
            new BukkitRunnable() {
                @Override
                public void run() {
                    killShulker(villUUID);
                    villPDC.set(highlightStatus, PersistentDataType.STRING, notHighlighted);
                    villagerPDC.put(villUUID, villPDC);
            }
        }.runTaskLater(VillagerInfo.plugin, 20L * ConfigValidator.configTime);
    }

    private static void spawnShulker(UUID villUUID, Location location) {
        Shulker spawnedShulker = (Shulker) location.getWorld().spawnEntity(location, SHULKER, CreatureSpawnEvent.SpawnReason.CUSTOM, (Entity) -> {
            Shulker highlightbox = (Shulker) Entity;
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
