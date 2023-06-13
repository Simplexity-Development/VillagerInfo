package simplexity.villagerinfo.events;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import simplexity.villagerinfo.configurations.functionality.VillConfig;
import simplexity.villagerinfo.util.PDCTag;

/**
 * Called when a sound effect is going to be sent to the player
 */

public class SoundEffectEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private boolean playerSoundToggleEnabled;
    private final Player player;

    public SoundEffectEvent(Player player) {
        this.player = player;
    }

    /**
     * Gets whether the event has been cancelled
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


    /**
     * Checks player's PersistentDataContainer to see their current sound toggle state
     */

    public void checkPlayerPDC() {
        byte soundToggleState = player.getPersistentDataContainer().getOrDefault(PDCTag.PLAYER_TOGGLE_SOUND_ENABLED.getPdcTag(), PersistentDataType.BYTE, (byte) 0);
        playerSoundToggleEnabled = soundToggleState != (byte) 1;
    }

    /**
     * Gets the sound that has been configured
     *
     * @return org.bukkit.Sound
     */
    public Sound getSound() {
        return VillConfig.getInstance().getConfiguredSound();
    }

    /**
     * Gets the volume that has been configured
     *
     * @return float
     */
    public float getSoundVolume() {
        return VillConfig.getInstance().getConfiguredSoundVolume();
    }

    /**
     * Gets the pitch that has been configured
     *
     * @return float
     */
    public float getSoundPitch() {
        return VillConfig.getInstance().getConfiguredSoundPitch();
    }

    /**
     * Gets the location of the Player this sound will be sent to
     *
     * @return Location
     */
    public Location getPlayerLocation() {
        return player.getLocation();
    }

    /**
     * Plays the sound effect for the player
     * <br>Uses getPlayerLocation(), getSound(), getSoundVolume(), and getSoundPitch()
     */
    public void playSoundEffect() {
        checkPlayerPDC();
        if (!playerSoundToggleEnabled) {
            cancelled = true;
            return;
        }
        player.playSound(getPlayerLocation(), getSound(), getSoundVolume(), getSoundPitch());
    }

    /**
     * Overrides the playerOutputToggleEnabled value, sets it to a new value regardless of the player's settings
     *
     * @param playerSoundToggleEnabled boolean
     */
    @SuppressWarnings("unused") //For API usage
    public void setOverridePlayerSoundToggleEnabled(boolean playerSoundToggleEnabled) {
        this.playerSoundToggleEnabled = playerSoundToggleEnabled;
    }

    /**
     * Gets if the player's sound toggle is enabled
     *
     * @return boolean
     */
    @SuppressWarnings("unused") //For API usage
    public boolean isPlayerSoundToggleEnabled() {
        return playerSoundToggleEnabled;
    }

    /**
     * Gets the player the sound will be played to
     *
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }
}
