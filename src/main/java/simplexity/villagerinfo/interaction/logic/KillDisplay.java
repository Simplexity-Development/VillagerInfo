package simplexity.villagerinfo.interaction.logic;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import simplexity.villagerinfo.VillagerInfo;
import simplexity.villagerinfo.events.RemoveHighlightEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class KillDisplay {

    /**
     * Removes a highlight {@link BlockDisplay} previously spawned for a villager and clears its tracking entry.
     * <p>
     * The tracked {@link BlockDisplay} reference may be stale if its chunk unloaded (and possibly reloaded)
     * since it was spawned, in which case the Java object no longer reflects the live world state. When that
     * is detected via {@link Entity#isValid()}, the entity's last-known chunk is force-loaded and the entity
     * is re-resolved fresh via {@link World#getEntity(UUID)} before removal, so the persisted entity is
     * actually deleted rather than silently orphaned.
     *
     * @param villagerUuid    the UUID of the villager whose highlight should be removed
     * @param mapToRemoveFrom the tracking map (workstation or bed) to remove the entry from
     */
    public static void removeHighlight(@NotNull UUID villagerUuid, @NotNull HashMap<UUID, BlockDisplay> mapToRemoveFrom) {
        BlockDisplay blockDisplay = mapToRemoveFrom.get(villagerUuid);
        if (blockDisplay == null) return;

        BlockDisplay entityToRemove = blockDisplay;
        if (!entityToRemove.isValid()) {
            entityToRemove = resolveStaleHighlightEntity(villagerUuid, entityToRemove);
            if (entityToRemove == null) {
                mapToRemoveFrom.remove(villagerUuid);
                return;
            }
        }

        RemoveHighlightEvent removeHighlightEvent = new RemoveHighlightEvent(villagerUuid, entityToRemove);
        Bukkit.getServer().getPluginManager().callEvent(removeHighlightEvent);
        entityToRemove.remove();
        mapToRemoveFrom.remove(villagerUuid);
    }

    /**
     * Re-resolves a stale, invalid {@link BlockDisplay} reference by force-loading its last-known chunk and
     * fetching the live entity fresh by UUID. A stale Java reference does not automatically reattach to the
     * entity once its chunk reloads, so it must be looked up again via {@link World#getEntity(UUID)}.
     *
     * @param villagerUuid the UUID of the villager the highlight belongs to, used only for log context
     * @param staleDisplay the stale, invalid {@link BlockDisplay} reference
     * @return the re-resolved, live {@link BlockDisplay}, or {@code null} if it could not be resolved
     */
    private static BlockDisplay resolveStaleHighlightEntity(@NotNull UUID villagerUuid, @NotNull BlockDisplay staleDisplay) {
        Location lastKnownLocation = staleDisplay.getLocation();
        World world = lastKnownLocation.getWorld();
        if (world == null) {
            VillagerInfo.getInstance().getVillagerInfoLogger().warning(
                    "Could not resolve world for stale highlight entity " + staleDisplay.getUniqueId()
                            + " (villager " + villagerUuid + "); dropping stale tracking entry without removing the entity.");
            return null;
        }

        Chunk chunk = lastKnownLocation.getChunk();
        if (!chunk.isLoaded() && !chunk.load()) {
            VillagerInfo.getInstance().getVillagerInfoLogger().warning(
                    "Failed to load chunk to remove stale highlight entity " + staleDisplay.getUniqueId()
                            + " for villager " + villagerUuid + "; leaving tracking entry in place for a future retry.");
            return null;
        }

        Entity resolved = world.getEntity(staleDisplay.getUniqueId());
        if (resolved instanceof BlockDisplay resolvedDisplay) {
            return resolvedDisplay;
        }
        return null;
    }

    /**
     * Removes all currently tracked workstation and bed highlights. Iterates snapshots of the tracking maps'
     * key sets since {@link #removeHighlight(UUID, HashMap)} mutates the live maps as it runs.
     */
    public static void clearAllCurrentHighlights() {
        for (UUID uuid : new ArrayList<>(DisplayManager.workStationVillagerMap.keySet())) {
            removeHighlight(uuid, DisplayManager.workStationVillagerMap);
        }
        for (UUID uuid : new ArrayList<>(DisplayManager.bedVillagerMap.keySet())) {
            removeHighlight(uuid, DisplayManager.bedVillagerMap);
        }
    }
}
