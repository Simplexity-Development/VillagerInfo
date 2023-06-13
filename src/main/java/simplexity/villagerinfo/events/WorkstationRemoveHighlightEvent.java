package simplexity.villagerinfo.events;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Villager;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import simplexity.villagerinfo.VillagerInfo;

/**
 * Called when a workstation highlight is  to be removed
 */
public class WorkstationRemoveHighlightEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    /**
     * Gets the handler list for this evene
     *
     * @return HandlerList
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Gets the handler list for this evene
     *
     * @return HandlerList
     */
    @SuppressWarnings("unused") //Required by bukkit
    public static HandlerList getHandlerList() {
        return handlers;
    }

    private boolean cancelled;
    private final Villager villager;
    private final org.bukkit.entity.BlockDisplay blockDisplay;
    private final NamespacedKey namespacedKey;

    public WorkstationRemoveHighlightEvent(Villager villager, org.bukkit.entity.BlockDisplay blockDisplay, NamespacedKey namespacedKey) {
        this.villager = villager;
        this.blockDisplay = blockDisplay;
        this.namespacedKey = namespacedKey;
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

    /**
     * Gets the villager involved in this event
     *
     * @return org.bukkit.entity.Villager
     */

    public Villager getVillager() {
        return villager;
    }

    /**
     * Gets the BlockDisplay involved in this event
     *
     * @return BlockDisplay
     */

    @SuppressWarnings("unused") //For API usage
    public BlockDisplay getBlockDisplay() {
        return blockDisplay;
    }

    /**
     * Turns off a villager's PDC switch
     */
    public void setVillagerPDCSwitchOff() {
        PersistentDataContainer villagerPDC = villager.getPersistentDataContainer();
        villagerPDC.set(namespacedKey, PersistentDataType.BYTE, (byte) 0);
    }

    /**
     * Removes the linked block display from the world, turns the villager's PDC switch off, and removes them from the hashmap
     */
    public void killBlockDisplay() {
        VillagerInfo.getInstance().getCurrentlyHighlighted().remove(villager);
        setVillagerPDCSwitchOff();
        blockDisplay.remove();
    }
}
