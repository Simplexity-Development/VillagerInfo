package simplexity.villagerinfo.interaction.logic;

import org.bukkit.Bukkit;
import org.bukkit.entity.BlockDisplay;
import simplexity.villagerinfo.events.RemoveHighlightEvent;

import java.util.HashMap;
import java.util.UUID;

public class KillDisplay {

    public static void removeHighlight(UUID villagerUuid, HashMap<UUID, BlockDisplay> mapToRemoveFrom){
        BlockDisplay blockDisplay = mapToRemoveFrom.get(villagerUuid);
        if (blockDisplay == null) return;
        RemoveHighlightEvent removeHighlightEvent = new RemoveHighlightEvent(villagerUuid, blockDisplay);
        Bukkit.getServer().getPluginManager().callEvent(removeHighlightEvent);
        blockDisplay.remove();
        mapToRemoveFrom.remove(villagerUuid);
    }


    public static void clearAllCurrentHighlights() {
        for (UUID uuid: DisplayManager.workStationVillagerMap.keySet()) {
            removeHighlight(uuid, DisplayManager.workStationVillagerMap);
        }
        for (UUID uuid: DisplayManager.bedVillagerMap.keySet()) {
            removeHighlight(uuid, DisplayManager.bedVillagerMap);
        }
    }

}
