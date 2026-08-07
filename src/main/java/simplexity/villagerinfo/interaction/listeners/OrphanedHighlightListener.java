package simplexity.villagerinfo.interaction.listeners;

import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import simplexity.villagerinfo.VillagerInfo;
import simplexity.villagerinfo.interaction.logic.DisplayFactory;
import simplexity.villagerinfo.interaction.logic.DisplayManager;
import simplexity.villagerinfo.util.HighlightType;

import java.util.HashMap;
import java.util.UUID;

/**
 * Backstop cleanup for highlight {@link BlockDisplay} entities left behind by a scenario the normal removal
 * path in {@code KillDisplay} can't cover: the in-memory tracking maps in {@code DisplayManager} are wiped by
 * a server/JVM restart, so a highlight that was still outstanding at that point has no tracking entry to be
 * found and removed by. Every highlight entity is tagged (see {@link DisplayFactory#HIGHLIGHT_VILLAGER_KEY})
 * with the villager UUID and {@link HighlightType} it belongs to, so it can be identified from world data
 * alone. As each chunk loads (naturally, whenever a player wanders back near it), any tagged entity found
 * that isn't the entity currently tracked for that villager/type is an orphan and is removed.
 */
public class OrphanedHighlightListener implements Listener {

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if (entity instanceof BlockDisplay blockDisplay) removeIfOrphaned(blockDisplay);
        }
    }

    private void removeIfOrphaned(BlockDisplay blockDisplay) {
        PersistentDataContainer pdc = blockDisplay.getPersistentDataContainer();
        String villagerUuidString = pdc.get(DisplayFactory.HIGHLIGHT_VILLAGER_KEY, PersistentDataType.STRING);
        String highlightTypeName = pdc.get(DisplayFactory.HIGHLIGHT_TYPE_KEY, PersistentDataType.STRING);
        if (villagerUuidString == null || highlightTypeName == null) return;

        UUID villagerUuid;
        HighlightType highlightType;
        try {
            villagerUuid = UUID.fromString(villagerUuidString);
            highlightType = HighlightType.valueOf(highlightTypeName);
        } catch (IllegalArgumentException e) {
            VillagerInfo.getInstance().getVillagerInfoLogger().warning(
                    "Found a highlight-tagged BlockDisplay (" + blockDisplay.getUniqueId()
                            + ") with an unreadable tag; removing it as orphaned.");
            blockDisplay.remove();
            return;
        }

        HashMap<UUID, BlockDisplay> trackingMap = highlightType == HighlightType.BED
                ? DisplayManager.bedVillagerMap
                : DisplayManager.workStationVillagerMap;
        BlockDisplay tracked = trackingMap.get(villagerUuid);
        if (tracked != null && tracked.getUniqueId().equals(blockDisplay.getUniqueId())) return;

        blockDisplay.remove();
    }
}
