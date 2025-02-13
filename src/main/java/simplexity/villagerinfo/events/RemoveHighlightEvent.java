package simplexity.villagerinfo.events;

import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Villager;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a workstation highlight is to be removed
 */
public class RemoveHighlightEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private Villager villager;
    private BlockDisplay blockDisplay;

    public RemoveHighlightEvent(Villager villager, BlockDisplay blockDisplay) {
        this.villager = villager;
        this.blockDisplay = blockDisplay;
    }

    /**
     * Gets the villager involved in this event
     *
     * @return org.bukkit.entity.Villager
     */

    public Villager getVillager() {
        return villager;
    }

    /**
     * Sets the Villager associated with this event, used to remove the block display from cache
     * @param villager Villager connected to event
     */
    public void setVillager(Villager villager) {
        this.villager = villager;
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
