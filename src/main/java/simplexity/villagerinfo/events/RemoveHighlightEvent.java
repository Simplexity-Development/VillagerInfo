package simplexity.villagerinfo.events;

import org.bukkit.entity.BlockDisplay;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Called when a workstation highlight is to be removed
 */
public class RemoveHighlightEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final UUID villagerUUID;
    private final BlockDisplay blockDisplay;

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
     * Gets the BlockDisplay involved in this event
     *
     * @return BlockDisplay
     */

    public BlockDisplay getBlockDisplay() {
        return blockDisplay;
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

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
