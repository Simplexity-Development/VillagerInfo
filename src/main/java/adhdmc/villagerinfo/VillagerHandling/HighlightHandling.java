package adhdmc.villagerinfo.VillagerHandling;

import adhdmc.villagerinfo.Config.ConfigValidator;
import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.FallingBlock;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.UUID;

public class HighlightHandling {
    private static final double CENTER = 0.5;
    private static final double OFFSET = -0.001;
    private static final int MAX_FALL_DISTANCE = -2147483648;

    public static final HashMap<UUID, FallingBlock> WORKSTATION_HIGHLIGHT_BLOCK = new HashMap<>();
    public static final HashMap<UUID, PersistentDataContainer> VILLAGER_PDC = new HashMap<>();

    /**
     * villagerJobsiteHighlight
     * @param villPDC the villager's PersistentDataContainer
     * @param villUUID the villager's UUID
     * @param villPOI the villager's Point Of Interest Location
     */
    public static void villagerJobsiteHighlight(PersistentDataContainer villPDC, UUID villUUID, Location villPOI) {
        if (villPDC.getOrDefault(VillagerInfo.HIGHLIGHT_STATUS, PersistentDataType.BYTE, (byte)0) == 1) return;
        villPDC.set(VillagerInfo.HIGHLIGHT_STATUS, PersistentDataType.BYTE, (byte)1);
        VILLAGER_PDC.put(villUUID, villPDC);
        spawnFallingBlock(villUUID, villPOI);
        Bukkit.getScheduler().runTaskLater(VillagerInfo.getInstance(), () -> {
            killFallingBlock(villUUID);
            villPDC.set(VillagerInfo.HIGHLIGHT_STATUS, PersistentDataType.BYTE, (byte)0);
            VILLAGER_PDC.put(villUUID, villPDC);
        }, 20L * ConfigValidator.configTime);
    }

    /**
     * spawnFallingBlock
     * @param villUUID the villager's UUID
     * @param location location to spawn the FallingBlock
     */
    private static void spawnFallingBlock(UUID villUUID, Location location) {
        BlockData blockData = location.getBlock().getBlockData();
        // spawn entity a bit lower than the original block, otherwise the glow won't show
        Location fallingBlockLocation = location.add(CENTER, OFFSET, CENTER);

        FallingBlock fallingBlock = location.getWorld().spawnFallingBlock(fallingBlockLocation, blockData);
        fallingBlock.setGlowing(true);
        // setting fall distance to it's max w/ gravity disabled makes it "fall infinitely"
        fallingBlock.setGravity(false);
        fallingBlock.setFallDistance(MAX_FALL_DISTANCE);

        WORKSTATION_HIGHLIGHT_BLOCK.put(villUUID, fallingBlock);
    }

    /**
     * killFallingBlock
     * @param villUUID villager's UUID that the FallingBlock was attached to
     */
    public static void killFallingBlock(UUID villUUID) {
        WORKSTATION_HIGHLIGHT_BLOCK.get(villUUID).remove();
        WORKSTATION_HIGHLIGHT_BLOCK.remove(villUUID);
    }
}
