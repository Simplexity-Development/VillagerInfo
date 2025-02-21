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

@SuppressWarnings("DuplicatedCode")
public class DisplayManager {
    public static HashMap<UUID, BlockDisplay> workStationVillagerMap = new HashMap<>();
    public static HashMap<UUID, BlockDisplay> bedVillagerMap = new HashMap<>();

    public static void handleWorkstationHighlight(Villager villager) {
        Block jobBlock = getJobBlock(villager);
        if (jobBlock == null) return;
        if (workStationVillagerMap.containsKey(villager.getUniqueId())) return;
        Color highlightColor = getColor(jobBlock);
        UUID villagerUuid = villager.getUniqueId();
        HighlightEvent highlightEvent = callHighlightEvent(villagerUuid, jobBlock, highlightColor);
        if (highlightEvent == null) return;
        villagerUuid = highlightEvent.getVillagerUUID();
        highlightColor = highlightEvent.getHighlightColor();
        jobBlock = highlightEvent.getHighlightedBlock();
        BlockDisplay blockDisplay = DisplayFactory.summonBlockDisplayEntity(
                highlightColor,
                jobBlock);
        workStationVillagerMap.put(villagerUuid, blockDisplay);
        scheduleRemoval(villagerUuid, workStationVillagerMap);
    }

    public static void handleBedHighlight(Villager villager) {
        Block homeBlock = getHomeBlock(villager);
        if (homeBlock == null) return;
        if (bedVillagerMap.containsKey(villager.getUniqueId())) return;
        if (!(homeBlock.getBlockData() instanceof Bed bedBlockData)) return;
        Bed.Part bedPart = bedBlockData.getPart();
        if (bedPart == Bed.Part.FOOT) {
            Location headLocation = getHeadLocation(homeBlock.getLocation(), bedBlockData.getFacing());
            if (headLocation == null) return;
            homeBlock = headLocation.getBlock();
        }
        Color highlightColor = getColor(homeBlock);
        UUID villagerUuid = villager.getUniqueId();
        HighlightEvent highlightEvent = callHighlightEvent(villagerUuid, homeBlock, highlightColor);
        if (highlightEvent == null) return;
        villagerUuid = highlightEvent.getVillagerUUID();
        highlightColor = highlightEvent.getHighlightColor();
        homeBlock = highlightEvent.getHighlightedBlock();
        BlockDisplay blockDisplay = DisplayFactory.summonBedDisplayEntity(
                highlightColor,
                homeBlock);
        bedVillagerMap.put(villagerUuid, blockDisplay);
        scheduleRemoval(villagerUuid, bedVillagerMap);
    }


    private static HighlightEvent callHighlightEvent(UUID villagerUUID, Block block, Color color) {
        HighlightEvent highlightEvent = new HighlightEvent(villagerUUID, block, color);
        Bukkit.getServer().getPluginManager().callEvent(highlightEvent);
        if (highlightEvent.isCancelled()) return null;
        return highlightEvent;
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

    private static void scheduleRemoval(UUID villagerUUID, HashMap<UUID, BlockDisplay> mapToRemoveFrom) {
        Bukkit.getScheduler().runTaskLater(VillagerInfo.getInstance(), () -> {
            KillDisplay.removeHighlight(villagerUUID, mapToRemoveFrom);
        }, VillConfig.getInstance().getConfiguredHighlightTime() * 20L);
    }


}
