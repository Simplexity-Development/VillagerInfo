package simplexity.villagerinfo.events;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import simplexity.villagerinfo.VillagerInfo;
import simplexity.villagerinfo.configurations.functionality.VillConfig;
import simplexity.villagerinfo.util.PDCTag;

import java.util.Map;

/**
 * Called when a workstation is going to be highlighted, requires Bukkit Villager object and Bukkit Block object
 */
public class WorkstationHighlightEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private final Player player;
    private final Villager villager;
    private BlockDisplay blockDisplayEntity;
    private FallingBlock legacyFallingBlockEntity;
    private final NamespacedKey namespacedKey;
    private boolean playerHighlightToggleEnabled;
    private int blockLight = 15;
    private int skyLight = 15;
    private float xScaleOffset = -0.03f;
    private float yScaleOffset = -0.03f;
    private float zScaleOffset = -0.03f;
    private Color defaultColor = Color.fromRGB(255, 255, 255);

    public WorkstationHighlightEvent(Player player, Villager villager, NamespacedKey namespacedKey) {
        this.villager = villager;
        this.namespacedKey = namespacedKey;
        this.player = player;
    }

    /**
     * Gets whether to use legacy highlighting or not (pre-1.19.4)
     *
     * @return boolean
     */
    public boolean isLegacyHighlight() {
        return VillagerInfo.getInstance().isLegacyVersion();
    }

    /**
     * Gets whether this event has been cancelled
     *
     * @return boolean
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets whether this event should be cancelled
     *
     * @param cancel true if you wish to cancel this event
     */
    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Gets the villager from this event
     *
     * @return Villager
     */

    public Villager getVillager() {
        return villager;
    }

    /**
     * Gets the villager's workstation block, returns null if there is none found
     *
     * @return Block
     */

    public Block getJobBlock() {
        Location jobSite = villager.getMemory(MemoryKey.JOB_SITE);
        if (jobSite == null) {
            return null;
        }
        return jobSite.getBlock();
    }

    /**
     * Gets the configured highlight color, and returns the default if there is no found color
     *
     * @return Color
     */

    public Color getHighlightColor() {
        Map<Material, Color> configMap = VillConfig.getInstance().getPoiBlockHighlightColorsMap();
        Color rgbColor;
        Block block = getJobBlock();
        if (configMap.containsKey(block.getType())) {
            rgbColor = configMap.get(block.getType());
        } else {
            return defaultColor;
        }
        return rgbColor;
    }

    /**
     * Checks villager PDC switch, checks if workstation exists, summonds block display, sets villager switch on, adds villager and display to map
     */
    public void highlightVillagerWorkstation() {
        checkPlayerPDC();
        if (!getPlayerHighlightToggleEnabled()) {
            cancelled = true;
            return;
        }
        if (getCurrentSwitchState() == (byte) 1) {
            cancelled = true;
            return;
        }
        if (getJobBlock() == null) {
            cancelled = true;
            return;
        }
        if (isLegacyHighlight()) {
            summonFallingBlockEntity();
        } else {
            summonBlockDisplayEntity();
        }
        setVillagerPDCSwitchOn();
        if (isLegacyHighlight()) {
            addVillagerAndFallingBlockToMap();
        } else {
            addVillagerAndBlockDisplayToMap();
        }
    }

    /**
     * Summons a falling block entity for versions before 1.19.4
     * <br>May experience visual glitches
     * <br>Cannot have color applied
     */

    public void summonFallingBlockEntity() {
        Block block = getJobBlock();
        BlockData blockData = block.getBlockData();
        Location fallingBlockLocation = block.getLocation().add(0.5, -0.001, 0.5);
        FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(fallingBlockLocation, blockData);
        fallingBlock.setGlowing(true);
        fallingBlock.setGravity(false);
        fallingBlock.setDropItem(false);
        fallingBlock.setPersistent(true);
        fallingBlock.setInvulnerable(true);
        fallingBlock.setFallDistance(-2147483648);
        legacyFallingBlockEntity = fallingBlock;
    }

    /**
     * Summons the block display
     * Uses getJobBlock(), xScaleOffset, yScaleOffset, zScaleOffset, blockLight, skyLight
     */
    public void summonBlockDisplayEntity() {
        Block block = getJobBlock();
        BlockDisplay blockDisplay = (BlockDisplay) block.getLocation().getWorld().spawnEntity(block.getLocation(), EntityType.BLOCK_DISPLAY);
        blockDisplayEntity = blockDisplay;
        blockDisplay.setBlock(block.getBlockData());
        blockDisplay.getTransformation().getScale().add(xScaleOffset, yScaleOffset, zScaleOffset);
        blockDisplay.setBrightness(new Display.Brightness(blockLight, skyLight));
        blockDisplay.setGlowing(true);
        blockDisplay.setGlowColorOverride(getHighlightColor());
        blockDisplay.getPersistentDataContainer().set(PDCTag.BLOCK_DISPLAY_IS_FROM_VILLAGER_INFO.getPdcTag(), PersistentDataType.BYTE, (byte) 1);
    }

    /**
     * Adds the villager and block display to the map of currently highlighted workstations
     */
    public void addVillagerAndBlockDisplayToMap() {
        VillagerInfo.getInstance().getCurrentlyHighlighted().put(villager, blockDisplayEntity);
    }

    public void addVillagerAndFallingBlockToMap() {
        VillagerInfo.getInstance().getLegacyCurrentlyHighlighted().put(villager, legacyFallingBlockEntity);
    }

    /**
     * Sets the villager PDC switch on (1b) signifying that this villager's workstation is currently highlighted
     */
    public void setVillagerPDCSwitchOn() {
        PersistentDataContainer villagerPDC = villager.getPersistentDataContainer();
        villagerPDC.set(namespacedKey, PersistentDataType.BYTE, (byte) 1);
    }

    /**
     * Gets the block display entity that was spawned
     *
     * @return BlockDisplay
     */
    public BlockDisplay getBlockDisplayEntity() {
        return blockDisplayEntity;
    }

    /**
     * Gets the villager's current switch state
     * 0b for no current workstations highlighted
     * 1b for currently highlighted workstations
     *
     * @return byte
     */
    public byte getCurrentSwitchState() {
        PersistentDataContainer villagerPDC = villager.getPersistentDataContainer();
        return villagerPDC.getOrDefault(namespacedKey, PersistentDataType.BYTE, (byte) 0);
    }

    /**
     * Checks player's output toggle state and sets playerHighlightToggleEnabled
     */
    public void checkPlayerPDC() {
        byte highlightToggleState = player.getPersistentDataContainer().getOrDefault(PDCTag.PLAYER_TOGGLE_HIGHLIGHT_ENABLED.getPdcTag(), PersistentDataType.BYTE, (byte) 0);
        playerHighlightToggleEnabled = highlightToggleState != (byte) 1;
    }

    /**
     * Sets the blocklight for the block display entity
     * 15 by default
     *
     * @param blockLight int
     */
    public void setBlockLight(int blockLight) {
        this.blockLight = blockLight;
    }

    /**
     * Sets the skylight for the block display entity
     * 15 by default
     *
     * @param skyLight int
     */

    public void setSkyLight(int skyLight) {
        this.skyLight = skyLight;
    }

    /**
     * Sets the x scale offset for the block display entity
     * -0.03f by default
     *
     * @param xScaleOffset float
     */

    public void setXScaleOffset(float xScaleOffset) {
        this.xScaleOffset = xScaleOffset;
    }

    /**
     * Sets the y scale offset for the block display entity
     * -0.03f by default
     *
     * @param yScaleOffset float
     */

    public void setYScaleOffset(float yScaleOffset) {
        this.yScaleOffset = yScaleOffset;
    }

    /**
     * Sets the z scale offset for the block display entity
     * -0.03f by default
     *
     * @param zScaleOffset float
     */

    public void setZScaleOffset(float zScaleOffset) {
        this.zScaleOffset = zScaleOffset;
    }

    /**
     * Sets default color to be used if no colors load from the main config
     * Color.fromRGB(255,255,255) by default
     *
     * @param defaultColor Color
     */

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    /**
     * Gets the falling block entity if the server is on a version before 1.19.4
     *
     * @return FallingBlock
     */
    public FallingBlock getLegacyFallingBlockEntity() {
        return legacyFallingBlockEntity;
    }

    /**
     * Checks if player's highlight toggle is shown as enabled
     * @return boolean
     */
    public boolean getPlayerHighlightToggleEnabled() {
        return playerHighlightToggleEnabled;
    }

    /**
     * Overrides the playerHighlightToggleEnabled value, sets it to a new value regardless of the player's settings
     *
     * @param playerHighlightToggleEnabled boolean
     */
    public void overridePlayerHighlightToggleEnabled(boolean playerHighlightToggleEnabled) {
        this.playerHighlightToggleEnabled = playerHighlightToggleEnabled;
    }
}
