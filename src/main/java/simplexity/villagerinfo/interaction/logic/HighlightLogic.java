package simplexity.villagerinfo.interaction.logic;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import simplexity.villagerinfo.VillagerInfo;
import simplexity.villagerinfo.configurations.functionality.ConfigToggle;
import simplexity.villagerinfo.configurations.functionality.VillConfig;
import simplexity.villagerinfo.events.HighlightEvent;
import simplexity.villagerinfo.util.PDCTag;

public class HighlightLogic {
    NamespacedKey workstationTag = PDCTag.VILLAGER_WORKSTATION_CURRENTLY_HIGHLIGHTED.getPdcTag();

    public static HighlightLogic instance;
    private BlockDisplay blockDisplayEntity;

    private HighlightLogic() {
    }

    public static HighlightLogic getInstance() {
        if (instance == null) instance = new HighlightLogic();
        return instance;
    }

    /**
     * Summons the block display
     * Uses getJobBlock(), xScaleOffset, yScaleOffset, zScaleOffset, blockLight, skyLight
     * @param highlightEvent
     */
    public void summonBlockDisplayEntity(HighlightEvent highlightEvent) {
        Block block = highlightEvent.getJobBlock();
        BlockDisplay blockDisplay = (BlockDisplay) block.getLocation().getWorld().spawnEntity(block.getLocation(), EntityType.BLOCK_DISPLAY);
        blockDisplayEntity = blockDisplay;
        blockDisplay.setBlock(block.getBlockData());
        blockDisplay.getTransformation().getScale().add(xScaleOffset, yScaleOffset, zScaleOffset);
        blockDisplay.setBrightness(new Display.Brightness(blockLight, skyLight));
        blockDisplay.setGlowing(true);
        blockDisplay.setGlowColorOverride(highlightEvent.getHighlightColor());
        blockDisplay.getPersistentDataContainer().set(PDCTag.BLOCK_DISPLAY_IS_FROM_VILLAGER_INFO.getPdcTag(), PersistentDataType.BYTE, (byte) 1);
    }

    public void runHighlightWorkstationBlock(Villager villager, org.bukkit.entity.Player player) {
        if (!ConfigToggle.HIGHLIGHT_VILLAGER_WORKSTATION_ON_OUTPUT.isEnabled()) return;
        HighlightEvent highlightEvent = callHighlightEvent(villager, player);
        if (highlightEvent == null || highlightEvent.isCancelled()) return;
        Bukkit.getScheduler().runTaskLater(VillagerInfo.getInstance(), () -> {
            summonBlockDisplayEntity(highlightEvent);
        }, 20L * VillConfig.getInstance().getConfiguredHighlightTime());
    }

    public HighlightEvent callHighlightEvent(Villager villager, org.bukkit.entity.Player player) {
        HighlightEvent highlightEvent = new HighlightEvent(villager, villager.getMemory(MemoryKey.JOB_SITE).getBlock(), defaultColor);
        Bukkit.getServer().getPluginManager().callEvent(highlightEvent);
        if (highlightEvent.isCancelled()) return null;
        return highlightEvent;
    }

    /*
    public void callWorkstationRemoveHighlightEvent(Villager villager, HighlightEvent highlightEvent) {
        RemoveHighlightEvent removeEvent = new RemoveHighlightEvent(villager, highlightEvent.getBlockDisplayEntity(), PDCTag.VILLAGER_WORKSTATION_CURRENTLY_HIGHLIGHTED.getPdcTag());
        Bukkit.getServer().getPluginManager().callEvent(removeEvent);
        if (removeEvent.isCancelled()) return;
        removeEvent.killBlockDisplay();
    }
    */

    public void villagerPDCHighlightsSetOff(Villager villager) {
        PersistentDataContainer villagerPDC = villager.getPersistentDataContainer();
        villagerPDC.set(workstationTag, PersistentDataType.BYTE, (byte) 0);
    }


}
