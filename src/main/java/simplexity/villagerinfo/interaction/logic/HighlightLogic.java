package simplexity.villagerinfo.interaction.logic;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import simplexity.villagerinfo.configurations.functionality.VillConfig;
import simplexity.villagerinfo.events.HighlightEvent;

import java.util.HashMap;
import java.util.UUID;

public class HighlightLogic {
    public static HashMap<UUID, BlockDisplay> villagerBlockDisplayMap = new HashMap<>();

    public static void handleWorkstationHighlight(Villager villager) {
        Block block = getJobBlock(villager);
        if (block == null) return;
        handleHighlight(villager, block);
    }

    public static void handleBedHighlight(Villager villager) {
        Block block = getHomeBlock(villager);
        if (block == null) return;
        if (!(block.getBlockData() instanceof Bed)) return;
        handleHighlight(villager, block);
    }

    private static void handleHighlight(Villager villager, Block block) {
        Color highlightColor = getColor(block);
        if (highlightColor == null) return;
        if (highlightEventCancelled(villager.getUniqueId(), block, highlightColor)) return;
        BlockDisplay blockDisplay = ShowDisplay.summonBlockDisplayEntity(highlightColor, block);
        villagerBlockDisplayMap.put(villager.getUniqueId(), blockDisplay);
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


}
