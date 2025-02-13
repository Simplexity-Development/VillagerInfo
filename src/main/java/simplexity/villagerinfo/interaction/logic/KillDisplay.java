package simplexity.villagerinfo.interaction.logic;

import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Villager;
import simplexity.villagerinfo.VillagerInfo;

import java.util.HashMap;

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

    public void clearAllCurrentHighlights(HighlightLogic highlightLogic) {
        HashMap<Villager, BlockDisplay> blockDisplays = VillagerInfo.getInstance().getCurrentlyHighlighted();
        blockDisplays.forEach((villager, blockDisplay) -> {
            blockDisplay.remove();
            highlightLogic.villagerPDCHighlightsSetOff(villager);
        });
    }

}
