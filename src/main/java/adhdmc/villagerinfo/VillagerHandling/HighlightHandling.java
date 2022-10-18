package adhdmc.villagerinfo.VillagerHandling;

import adhdmc.villagerinfo.Config.ConfigValidator;
import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
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
    private static NamespacedKey highlightStatus = new NamespacedKey(VillagerInfo.getInstance(), "highlighted");

    public static HashMap<UUID, FallingBlock> workstationHighlightBlock = new HashMap<>();
    public static HashMap<UUID, PersistentDataContainer> villagerPDC = new HashMap<>();

    /**
     * villagerJobsiteHighlight
     * @param villPDC the villager's PersistentDataContainer
     * @param villUUID the villager's UUID
     * @param villPOI the villager's Point Of Interest Location
     */
    public static void villagerJobsiteHighlight(PersistentDataContainer villPDC, UUID villUUID, Location villPOI) {
        if (villPDC.getOrDefault(highlightStatus, PersistentDataType.BYTE, (byte)0) == 1) return;
        villPDC.set(highlightStatus, PersistentDataType.BYTE, (byte)1);
        villagerPDC.put(villUUID, villPDC);
        spawnFallingBlock(villUUID, villPOI);
        Bukkit.getScheduler().runTaskLater(VillagerInfo.getInstance(), () -> {
            killFallingBlock(villUUID);
            villPDC.set(highlightStatus, PersistentDataType.BYTE, (byte)0);
            villagerPDC.put(villUUID, villPDC);
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

        workstationHighlightBlock.put(villUUID, fallingBlock);
    }

    /**
     * killFallingBlock
     * @param villUUID villager's UUID that the FallingBlock was attached to
     */
    public static void killFallingBlock(UUID villUUID) {
        workstationHighlightBlock.get(villUUID).remove();
        workstationHighlightBlock.remove(villUUID);
    }
}
