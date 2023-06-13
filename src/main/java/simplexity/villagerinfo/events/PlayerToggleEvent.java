package simplexity.villagerinfo.events;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import simplexity.villagerinfo.VillagerInfo;
import simplexity.villagerinfo.configurations.locale.MessageInsert;
import simplexity.villagerinfo.configurations.locale.ServerMessage;

/**
 * Called when a player's info output is toggled
 */

public class PlayerToggleEvent extends Event implements Cancellable {
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

    @SuppressWarnings("unused") //Required by bukkit
    public static HandlerList getHandlerList() {
        return handlers;
    }

    private boolean cancelled;
    private final NamespacedKey namespacedKey;
    private final Player player;
    private final String toggleType;

    public PlayerToggleEvent(Player player, NamespacedKey namespacedKey, String toggleType) {
        this.player = player;
        this.namespacedKey = namespacedKey;
        this.toggleType = toggleType;
    }

    /**
     * Gets if the event is cancelled
     *
     * @return boolean
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets if the event should be cancelled
     *
     * @param cancel true if you wish to cancel this event
     */

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    /**
     * Sets the toggle to be disabled in the player's PDC
     * <br>Sets value to 1b
     */

    public void setDisabled() {
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        playerPDC.set(namespacedKey, PersistentDataType.BYTE, (byte) 1);
        sendPlayerFeedback(MessageInsert.DISABLED_MESSAGE_FORMAT.getMessage());
    }

    /**
     * Sets the toggle to be enabled in the player's PDC
     * <br>Sets value to 0b
     * <br>Calls sendPlayerFeedback() with the ENABLED_MESSAGE_FORMAT message
     */

    public void setEnabled() {
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        playerPDC.set(namespacedKey, PersistentDataType.BYTE, (byte) 0);
        sendPlayerFeedback(MessageInsert.ENABLED_MESSAGE_FORMAT.getMessage());
    }

    /**
     * Gets the current toggle state of the player
     * <br>Returns a default of 0b (Enabled) if there was no information on the player
     *
     * @return byte
     */

    public byte getCurrentToggleState() {
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        return playerPDC.getOrDefault(namespacedKey, PersistentDataType.BYTE, (byte) 0);
    }

    /**
     * Gets the toggle that was used
     * <br>Uses the TOGGLE_TYPE_HIGHLIGHT, TOGGLE_TYPE_OUTPUT, and TOGGLE_TYPE_SOUND messages from the MessageInsert enum
     *
     * @return String
     */
    @SuppressWarnings("unused") //For API usage
    public String getToggleType() {
        return toggleType;
    }

    /**
     * Sends the player feedback on what the toggle was set to.
     * <br>Uses TOGGLE_COMMAND_FEEDBACK from ServerMessage enum
     * <br>Uses ENABLED_MESSAGE_FORMAT, DISABLED_MESSAGE_FORMAT, TOGGLE_TYPE_HIGHLIGHT, TOGGLE_TYPE_OUTPUT, and TOGGLE_TYPE_SOUND from MessageInsert enum
     *
     * @param state String
     */

    public void sendPlayerFeedback(String state) {
        player.sendMessage(VillagerInfo.getInstance().getMiniMessage().deserialize(ServerMessage.TOGGLE_COMMAND_FEEDBACK.getMessage(),
                Placeholder.parsed("plugin_prefix", ServerMessage.PLUGIN_PREFIX.getMessage()),
                Placeholder.parsed("value", toggleType),
                Placeholder.parsed("state", state)));
    }

    /**
     * Gets the player whose settings were toggled
     *
     * @return org.bukkit.entity.Player
     */
    public Player getPlayer() {
        return player;
    }
}
