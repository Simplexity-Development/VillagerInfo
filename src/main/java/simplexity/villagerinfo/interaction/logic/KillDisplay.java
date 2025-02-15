package simplexity.villagerinfo.interaction.logic;

import org.bukkit.entity.BlockDisplay;

import java.util.UUID;

public class KillDisplay {
    private static KillDisplay instance;

    public KillDisplay() {
    }

    public static KillDisplay getInstance() {
        if (instance == null) {
            instance = new KillDisplay();
        }
        return instance;
    }

    public void clearAllCurrentHighlights() {
        for(UUID uuid: HighlightLogic.villagerBlockDisplayMap.keySet()) {
            BlockDisplay blockDisplay = HighlightLogic.villagerBlockDisplayMap.get(uuid);
            blockDisplay.remove();
            HighlightLogic.villagerBlockDisplayMap.remove(uuid);
        }
    }

}
