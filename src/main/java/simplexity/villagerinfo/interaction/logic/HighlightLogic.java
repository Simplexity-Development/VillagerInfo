package simplexity.villagerinfo.interaction.logic;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Villager;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import simplexity.villagerinfo.VillagerInfo;
import simplexity.villagerinfo.configurations.functionality.ConfigToggle;
import simplexity.villagerinfo.configurations.functionality.VillConfig;
import simplexity.villagerinfo.events.WorkstationHighlightEvent;
import simplexity.villagerinfo.events.WorkstationRemoveHighlightEvent;
import simplexity.villagerinfo.util.PDCTag;

import java.util.HashMap;

public class HighlightLogic {
    NamespacedKey workstationTag = PDCTag.VILLAGER_WORKSTATION_CURRENTLY_HIGHLIGHTED.getPdcTag();

    private static HighlightLogic instance;

    private HighlightLogic() {
    }

    public static HighlightLogic getInstance() {
        if (instance == null) instance = new HighlightLogic();
        return instance;
    }

    public void runHighlightWorkstationBlock(Villager villager, org.bukkit.entity.Player player) {
        if (!ConfigToggle.HIGHLIGHT_VILLAGER_WORKSTATION_ON_OUTPUT.isEnabled()) return;
        WorkstationHighlightEvent highlightEvent = callHighlightEvent(villager, player);
        if (highlightEvent == null) return;
        highlightEvent.highlightVillagerWorkstation();
        if (highlightEvent.isCancelled()) return;
        Bukkit.getScheduler().runTaskLater(VillagerInfo.getInstance(), () -> {
            callWorkstationRemoveHighlightEvent(villager, highlightEvent);
        }, 20L * VillConfig.getInstance().getConfiguredHighlightTime());
    }

    public WorkstationHighlightEvent callHighlightEvent(Villager villager, org.bukkit.entity.Player player) {
        WorkstationHighlightEvent highlightEvent = new WorkstationHighlightEvent(player, villager, PDCTag.VILLAGER_WORKSTATION_CURRENTLY_HIGHLIGHTED.getPdcTag());
        Bukkit.getServer().getPluginManager().callEvent(highlightEvent);
        if (highlightEvent.isCancelled()) return null;
        return highlightEvent;
    }

    public void callWorkstationRemoveHighlightEvent(Villager villager, WorkstationHighlightEvent highlightEvent) {
        WorkstationRemoveHighlightEvent removeEvent = new WorkstationRemoveHighlightEvent(villager, highlightEvent.getBlockDisplayEntity(), PDCTag.VILLAGER_WORKSTATION_CURRENTLY_HIGHLIGHTED.getPdcTag());
        Bukkit.getServer().getPluginManager().callEvent(removeEvent);
        if (removeEvent.isCancelled()) return;
        removeEvent.killBlockDisplay();
    }

    public void villagerPDCHighlightsSetOff(Villager villager) {
        PersistentDataContainer villagerPDC = villager.getPersistentDataContainer();
        villagerPDC.set(workstationTag, PersistentDataType.BYTE, (byte) 0);
    }

    public void clearAllCurrentHighlights() {
        HashMap<Villager, BlockDisplay> blockDisplays = VillagerInfo.getInstance().getCurrentlyHighlighted();
        blockDisplays.forEach((villager, blockDisplay) -> {
            blockDisplay.remove();
            villagerPDCHighlightsSetOff(villager);
        });
    }
}
