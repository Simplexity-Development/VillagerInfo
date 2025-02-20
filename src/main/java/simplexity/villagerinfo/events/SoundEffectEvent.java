package simplexity.villagerinfo.events;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a sound effect is going to be sent to the player
 */

public class SoundEffectEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private Sound sound;
    private Player player;
    private float volume;
    private float pitch;
    private Location location;

    public SoundEffectEvent(Player player, Sound sound, float volume, float pitch, Location location) {
        this.sound = sound;
        this.player = player;
        this.volume = volume;
        this.pitch = pitch;
    }

    /**
     * Gets the sound that is tied to this event
     *
     * @return Sound
     */
    public Sound getSound() {
        return sound;
    }

    /**
     * Gets the volume of the sound for this event
     *
     * @return float
     */
    public float getVolume() {
        return volume;
    }

    /**
     * Gets the pitch of the sound for this event
     *
     * @return float
     */
    public float getPitch() {
        return pitch;
    }

    /**
     * Gets the player the sound will be played to
     *
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the sound to be used in this event
     *
     * @param sound Sound to be used in this event
     */

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    /**
     * Sets the volume of the sound in this event
     *
     * @param volume float between 0.0 and 2.0
     */
    public void setVolume(float volume) {
        this.volume = volume;
    }

    /**
     * Sets the pitch of the sound in this event
     *
     * @param pitch float between 0.0 and 2.0
     */

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    /**
     * Sets the player that this sound will be played to
     *
     * @param player Player to play sound to
     */

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Get the location this sound will be played at.
     *
     * @return Location the sound will be played at
     */
    public Location getLocation() {
        return location;
    }


    /**
     * Set the location for this sound to be played at
     *
     * @param location Location the sound will be played at
     */
    public void setLocation(Location location) {
        this.location = location;
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
