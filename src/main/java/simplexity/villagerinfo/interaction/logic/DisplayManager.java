package simplexity.villagerinfo.interaction.logic;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import simplexity.villagerinfo.VillagerInfo;
import simplexity.villagerinfo.configurations.functionality.VillConfig;
import simplexity.villagerinfo.events.HighlightEvent;

import java.util.HashMap;
import java.util.UUID;

public class DisplayManager {
    public static HashMap<UUID, BlockDisplay> villagerBlockDisplayMap = new HashMap<>();

    public static void handleWorkstationHighlight(Villager villager) {
        Block block = getJobBlock(villager);
        if (block == null) return;
        handleHighlight(villager.getUniqueId(), block);
    }

    public static void handleBedHighlight(Villager villager) {
        Block block = getHomeBlock(villager);
        if (block == null) return;
        if (!(block.getBlockData() instanceof Bed bedBlockData)) return;
        Bed.Part bedPart = bedBlockData.getPart();
        if (bedPart == Bed.Part.FOOT) {
            Location headLocation = getHeadLocation(block.getLocation(), bedBlockData.getFacing());
            if (headLocation == null) return;
            block = headLocation.getBlock();
        }
        handleHighlight(villager.getUniqueId(), block);
    }

    private static void handleHighlight(UUID villagerUUID, Block block) {
        Color highlightColor = getColor(block);
        if (highlightColor == null) return;
        if (highlightEventCancelled(villagerUUID, block, highlightColor)) return;
        BlockDisplay blockDisplay = DisplayFactory.summonBlockDisplayEntity(highlightColor, block);
        villagerBlockDisplayMap.put(villagerUUID, blockDisplay);
        scheduleRemoval(villagerUUID);
    }

    private static boolean highlightEventCancelled(UUID villagerUUID, Block block, Color color) {
        HighlightEvent highlightEvent = new HighlightEvent(villagerUUID, block, color);
        Bukkit.getServer().getPluginManager().callEvent(highlightEvent);
        return highlightEvent.isCancelled();
    }

    private static Block getJobBlock(Villager villager) {
        return blockFromLocation(villager.getMemory(MemoryKey.JOB_SITE));
    }

    private static Block getHomeBlock(Villager villager) {
        return blockFromLocation(villager.getMemory(MemoryKey.HOME));
    }

    private static Block blockFromLocation(Location location) {
        if (location == null) return null;
        Block block = location.getBlock();
        if (block.isEmpty()) return null;
        return block;
    }

    private static Color getColor(Block block) {
        Material material = block.getType();
        if (material == Material.AIR) return null;
        Color color = VillConfig.getInstance().getPoiBlockHighlightColorsMap().get(material);
        if (color == null) return VillConfig.getInstance().getDefaultColor();
        return color;
    }

    private static Location getHeadLocation(Location location, BlockFace facing) {
        return switch (facing) {
            case NORTH -> location.add(0, 0, -1);
            case EAST -> location.add(1, 0, 0);
            case SOUTH -> location.add(0, 0, 1);
            case WEST -> location.add(-1, 0, 0);
            default -> null;
        };
    }

    private static void scheduleRemoval(UUID villagerUUID) {
        Bukkit.getScheduler().runTaskLater(VillagerInfo.getInstance(), () -> {
            KillDisplay.removeHighlight(villagerUUID);
        }, VillConfig.getInstance().getConfiguredHighlightTime() * 20L);
    }


}
