package simplexity.villagerinfo.events;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.FallingBlock;
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
public class LegacyWorkstationRemoveHighlightEvent extends Event implements Cancellable {


    private static final HandlerList handlers = new HandlerList();

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
    private final NamespacedKey namespacedKey;
    private final FallingBlock fallingBlock;

    public LegacyWorkstationRemoveHighlightEvent(Villager villager, FallingBlock fallingBlock, NamespacedKey namespacedKey) {
        this.villager = villager;
        this.fallingBlock = fallingBlock;
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
     * Gets the FallingBlock involved in this event
     *
     * @return FallingBlock
     */

    @SuppressWarnings("unused") //For API usage
    public FallingBlock getFallingBlock() {
        return fallingBlock;
    }

    /**
     * Turns off a villager's PDC switch
     */
    public void setVillagerPDCSwitchOff() {
        PersistentDataContainer villagerPDC = villager.getPersistentDataContainer();
        villagerPDC.set(namespacedKey, PersistentDataType.BYTE, (byte) 0);
    }

    /**
     * Removes the linked falling block from the world, turns the villager's PDC switch off, and removes them from the hashmap
     */
    public void killFallingBlock() {
        VillagerInfo.getInstance().getLegacyCurrentlyHighlighted().remove(villager);
        setVillagerPDCSwitchOff();
        fallingBlock.remove();
    }
}
