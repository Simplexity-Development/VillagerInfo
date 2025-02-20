package simplexity.villagerinfo.events;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when information is to be displayed on a zombie villager
 */

public class ZombieVillagerOutputEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private ZombieVillager zombieVillager;
    private Player outputPlayer;
    private Component outputComponent;


    public ZombieVillagerOutputEvent(ZombieVillager zombieVillager, Player outputPlayer, Component outputComponent) {
        this.zombieVillager = zombieVillager;
        this.outputPlayer = outputPlayer;
        this.outputComponent = outputComponent;
    }


    /**
     * Get the Component that is to be sent to the player
     *
     * @return Component
     */

     //For API usage
    public Component getOutputComponent() {
        return outputComponent;
    }

    /**
     * Gets the zombie villager involved in this event
     *
     * @return ZombieVillager
     */

    public ZombieVillager getZombieVillager() {
        return zombieVillager;
    }

    /**
     * Gets the player this will output to
     *
     * @return Player
     */

    public Player getOutputPlayer() {
        return outputPlayer;
    }

    /**
     * Set the player this will output to
     *
     * @param outputPlayer Player to output to
     */
    public void setOutputPlayer(Player outputPlayer) {
        this.outputPlayer = outputPlayer;
    }

    /**
     * Set the output Component
     *
     * @param outputComponent Component to output
     */

    public void setOutputComponent(Component outputComponent) {
        this.outputComponent = outputComponent;
    }

    /**
     * Set the zombie villager to pull information from
     * @param zombieVillager ZombieVillager
     */
    public void setZombieVillager(ZombieVillager zombieVillager) {
        this.zombieVillager = zombieVillager;
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
     * Gets whether this event should be cancelled
     *
     * @param cancel true if you wish to cancel this event
     */
    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
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
    //Required by bukkit
    public static HandlerList getHandlerList() {
        return handlers;
    }



}
