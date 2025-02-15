package simplexity.villagerinfo.events;

import org.bukkit.entity.BlockDisplay;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Called when a workstation highlight is to be removed
 */
public class RemoveHighlightEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private UUID villagerUUID;
    private BlockDisplay blockDisplay;

    public RemoveHighlightEvent(UUID villagerUUID, BlockDisplay blockDisplay) {
        this.villagerUUID = villagerUUID;
        this.blockDisplay = blockDisplay;
    }

    /**
     * Gets the UUID of the villager involved in this event
     *
     * @return UUID the UUID of the villager in this event
     */

    public UUID getVillagerUUID() {
        return villagerUUID;
    }

    /**
     * Sets the UUID of the villager associated with this event, used to remove the block display from cache
     * @param villagerUUID UUID of the villager connected to event
     */
    public void setVillagerUUID(UUID villagerUUID) {
        this.villagerUUID = villagerUUID;
    }

    /**
     * Gets the BlockDisplay involved in this event
     *
     * @return BlockDisplay
     */

    public BlockDisplay getBlockDisplay() {
        return blockDisplay;
    }

    /**
     * Sets the Block display that will be removed in this event
     * @param blockDisplay Block Display to be removed
     */
    public void setBlockDisplay(BlockDisplay blockDisplay) {
        this.blockDisplay = blockDisplay;
    }


    /**
     * Gets the handler list for this event
     *
     * @return HandlerList
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Gets the handler list for this event
     *
     * @return HandlerList
     */
    @SuppressWarnings("unused") //Required by bukkit
    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Gets if the event has been cancelled
     *
     * @return boolean
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets whether the event should be cancelled
     *
     * @param cancel true if you wish to cancel this event
     */

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
