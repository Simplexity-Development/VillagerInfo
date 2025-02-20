package simplexity.villagerinfo.events;

import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when preparing output on a villager. Requires a Bukkit Villager object and Bukkit Player object
 */

public class VillagerOutputEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private Villager villager;
    private Player outputPlayer;
    private OfflinePlayer playerToProcess;
    private boolean cancelled;
    private Component outputComponent;

    public VillagerOutputEvent(Villager villager, Player outputPlayer, OfflinePlayer playerToProcess, Component outputComponent) {
        this.villager = villager;
        this.outputPlayer = outputPlayer;
        this.playerToProcess = playerToProcess;
        this.outputComponent = outputComponent;
    }


    /**
     * Gets the villager that the information is being obtained from
     *
     * @return Villager
     */
    public Villager getVillager() {
        return villager;
    }

    /**
     * Sets the villager to get information from
     *
     * @param villager Villager
     */
    public void setVillager(Villager villager) {
        this.villager = villager;
    }

    /**
     * Gets the player who is associated with this event. I.e. The player to output to.
     *
     * @return Player Online Player
     */
    public Player getOutputPlayer() {
        return outputPlayer;
    }

    /**
     * Sets the player who will have the information output to them
     *
     * @param outputPlayer Online Player
     */

    public void setOutputPlayer(Player outputPlayer) {
        this.outputPlayer = outputPlayer;
    }

    /**
     * Gets the player that will be processed for things like player reputation.
     *
     * @return OfflinePlayer that will be processed
     */
    public OfflinePlayer getPlayerToProcess() {
        return playerToProcess;
    }

    /**
     * Sets the player that will be processed for things like player reputation
     *
     * @param playerToProcess OfflinePlayer - player to process.
     */
    public void setPlayerToProcess(OfflinePlayer playerToProcess) {
        this.playerToProcess = playerToProcess;
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

    /**
     * Set the component that should be sent to the player
     *
     * @param outputComponent Component
     */
    public void setOutputComponent(Component outputComponent) {
        this.outputComponent = outputComponent;
    }

    /**
     * Get the Component that is to be sent to the player
     *
     * @return Component
     */

    public Component getOutputComponent() {
        return outputComponent;
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
    //required by bukkit
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
