package simplexity.villagerinfo.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import simplexity.villagerinfo.VillagerInfo;
import simplexity.villagerinfo.configurations.functionality.VillConfig;
import simplexity.villagerinfo.configurations.locale.ServerMessage;
import simplexity.villagerinfo.configurations.locale.VillagerMessage;
import simplexity.villagerinfo.util.PDCTag;
import simplexity.villagerinfo.util.Resolvers;

import java.util.Objects;

/**
 * Called when information is to be displayed on a zombie villager
 */
public class ZombieVillagerOutputEvent extends Event implements Cancellable {
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

    private final ZombieVillager zombieVillager;
    private final Player player;
    private boolean cancelled;
    private boolean outputHasInfo = false;
    private boolean playerOutputToggleEnabled;
    private Component outputComponent;
    private final MiniMessage miniMessage = VillagerInfo.getInstance().getMiniMessage();

    public ZombieVillagerOutputEvent(ZombieVillager zombieVillager, Player player) {
        this.zombieVillager = zombieVillager;
        this.player = player;
    }

    public Long getCurrentGameTime() {
        return zombieVillager.getWorld().getGameTime();
    }

    /**
     * Gets whether this zombie villager is currently converting
     *
     * @return boolean
     */
    public boolean isConverting() {
        return zombieVillager.isConverting();
    }

    /**
     * Gets zombie villager's current health
     *
     * @return Double
     */

    public Double getZombieVillagerCurrentHealth() {
        return zombieVillager.getHealth();
    }

    /**
     * Gets zombie villager's max health
     *
     * @return Double
     */

    public Double getZombieVillagerMaxHealth() {
        return Objects.requireNonNull(zombieVillager.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
    }

    /**
     * Gets the component for the Zombie Villager Health Message
     * <br>Uses VILLAGER_HEALTH message from VillagerMessage enum
     * <br>Returns null if ConfigToggle.DISPLAY_HEALTH.isEnabled() is false
     *
     * @return Component
     */

    public Component getZombieVillagerHealthMessageComponent() {
        if (!VillConfig.getInstance().displayHealth()) return null;
        outputHasInfo = true;
        Double currentHealth = getZombieVillagerCurrentHealth();
        Double maxHealth = getZombieVillagerMaxHealth();
        return miniMessage.deserialize(VillagerMessage.VILLAGER_HEALTH.getMessage(),
                Placeholder.parsed("value", currentHealth.toString()),
                Placeholder.parsed("value2", maxHealth.toString()));
    }

    /**
     * Gets zombie villager's profession
     *
     * @return Villager.Profession
     */

    public Villager.Profession getZombieVillagerProfession() {
        return zombieVillager.getVillagerProfession();
    }

    /**
     * Gets the Component for the Zombie Villager Profession Message
     * <br>Uses VILLAGER_PROFESSION message from VillagerMessage enum
     * <br>Returns null if ConfigToggle.DISPLAY_PROFESSION.isEnabled() is false
     *
     * @return Component
     */
    public Component getZombieVillagerProfessionMessageComponent() {
        if (!VillConfig.getInstance().displayProfession()) return null;
        outputHasInfo = true;
        Villager.Profession profession = getZombieVillagerProfession();
        String professionString = profession.name().toLowerCase();
        return miniMessage.deserialize(VillagerMessage.VILLAGER_PROFESSION.getMessage(),
                Placeholder.parsed("value", professionString));
    }

    /**
     * Gets the number of ticks until converted
     *
     * @return Integer
     */
    public Integer getTimeUntilConverted() {
        return zombieVillager.getConversionTime();
    }

    /**
     * Gets the Component for the Time Until Converted message
     * <br>Uses ZOMBIE_VILLAGER_NOT_CURRENTLY_CONVERTING and ZOMBIE_VILLAGER_CONVERSION_TIME messages from VillagerMessage enum
     * <br>Returns null if ConfigToggle.DISPLAY_ZOMBIE_VILLAGER_CONVERSION_TIME.isEnabled() is false
     *
     * @return Component
     */

    public Component getTimeUntilConvertedMessageComponent() {
        if (!VillConfig.getInstance().displayZombieVillagerConversionTime()) return null;
        outputHasInfo = true;
        if (!isConverting())
            return miniMessage.deserialize(VillagerMessage.ZOMBIE_VILLAGER_NOT_CURRENTLY_CONVERTING.getMessage());
        long conversionTimeDiff = getCurrentGameTime() - getTimeUntilConverted();
        Long conversionTimeInSeconds = conversionTimeDiff / 20;
        return miniMessage.deserialize(VillagerMessage.ZOMBIE_VILLAGER_CONVERSION_TIME.getMessage(),
                Resolvers.getInstance().timeFormatter(conversionTimeInSeconds));
    }

    /**
     * Builds the Component to send to the player
     * <br>Uses getTimeUntilConvertedMessageComponent(), getZombieVillagerHealthMessageComponent(), getZombieVillagerProfessionMessageComponent() if not null
     * <br>If all messages are null, uses NO_INFORMATION_TO_DISPLAY message from VillagerMessage enum
     * <br>Uses PLUGIN_PREFIX message from ServerMessage enum
     */

    public void buildOutputComponent() {
        Component tempOutputComponent = miniMessage.deserialize(ServerMessage.PLUGIN_PREFIX.getMessage());
        if (getTimeUntilConvertedMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.appendNewline().append(getTimeUntilConvertedMessageComponent());
        }
        if (getZombieVillagerHealthMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.appendNewline().append(getZombieVillagerHealthMessageComponent());
        }
        if (getZombieVillagerProfessionMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.appendNewline().append(getZombieVillagerProfessionMessageComponent());
        }
        if (!outputHasInfo) {
            tempOutputComponent = tempOutputComponent.appendNewline().append(miniMessage.deserialize(VillagerMessage.NO_INFORMATION_TO_DISPLAY.getMessage()));
        }
        setOutputComponent(tempOutputComponent);
    }

    /**
     * Sends outputComponent to player if the player's toggle is enabled
     */
    public void sendOutputToPlayer() {
        checkPlayerPDC();
        if (!playerOutputToggleEnabled) {
            cancelled = true;
            return;
        }
        buildOutputComponent();
        player.sendMessage(outputComponent);
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

    @SuppressWarnings("unused") //For API usage
    public Component getOutputComponent() {
        return outputComponent;
    }

    /**
     * Checks player's output toggle state and sets playerOutputToggleEnabled
     */
    public void checkPlayerPDC() {
        byte outputToggleState = player.getPersistentDataContainer().getOrDefault(PDCTag.PLAYER_TOGGLE_OUTPUT_ENABLED.getPdcTag(), PersistentDataType.BYTE, (byte) 0);
        playerOutputToggleEnabled = outputToggleState != (byte) 1;
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
     * Gets the zombie villager involved in this event
     *
     * @return ZombieVillager
     */

    @SuppressWarnings("unused") //For API usage
    public ZombieVillager getZombieVillager() {
        return zombieVillager;
    }

    /**
     * Gets the player involved in this event
     *
     * @return Player
     */

    public Player getPlayer() {
        return player;
    }

    /**
     * Gets playerOutputToggleEnabled
     *
     * @return boolean
     */
    @SuppressWarnings("unused") //For API usage
    public boolean isPlayerOutputToggleEnabled() {
        return playerOutputToggleEnabled;
    }

    /**
     * Overrides the playerOutputToggleEnabled value, sets it to a new value regardless of the player's settings
     *
     * @param playerOutputToggleEnabled boolean
     */
    @SuppressWarnings("unused") //For API usage
    public void setOverridePlayerOutputToggleEnabled(boolean playerOutputToggleEnabled) {
        this.playerOutputToggleEnabled = playerOutputToggleEnabled;
    }
}
