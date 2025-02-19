package simplexity.villagerinfo.interaction.logic;

import org.bukkit.entity.BlockDisplay;

import java.util.HashMap;
import java.util.UUID;

public class KillDisplay {

    public static void removeHighlight(UUID villagerUUID, HashMap<UUID, BlockDisplay> mapToRemoveFrom){
        BlockDisplay blockDisplay = mapToRemoveFrom.get(villagerUUID);
        if (blockDisplay == null) return;
        blockDisplay.remove();
        mapToRemoveFrom.remove(villagerUUID);
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
