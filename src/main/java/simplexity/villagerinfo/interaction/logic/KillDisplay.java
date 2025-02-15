package simplexity.villagerinfo.interaction.logic;

import org.bukkit.entity.BlockDisplay;

import java.util.UUID;

public class KillDisplay {

    public static void removeHighlight(UUID villagerUUID){
        BlockDisplay blockDisplay = DisplayManager.villagerBlockDisplayMap.get(villagerUUID);
        if (blockDisplay == null) return;
        blockDisplay.remove();
        DisplayManager.villagerBlockDisplayMap.remove(villagerUUID);
    }

    public static void clearAllCurrentHighlights() {
        for (UUID uuid: DisplayManager.villagerBlockDisplayMap.keySet()) {
            removeHighlight(uuid);
        }
    }

}
