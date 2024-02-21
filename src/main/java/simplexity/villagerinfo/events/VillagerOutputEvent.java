package simplexity.villagerinfo.events;

import com.destroystokyo.paper.entity.villager.Reputation;
import com.destroystokyo.paper.entity.villager.ReputationType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftVillager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import simplexity.villagerinfo.VillagerInfo;
import simplexity.villagerinfo.configurations.functionality.ConfigToggle;
import simplexity.villagerinfo.configurations.locale.MessageInsert;
import simplexity.villagerinfo.configurations.locale.ServerMessage;
import simplexity.villagerinfo.configurations.locale.VillagerMessage;
import simplexity.villagerinfo.util.PDCTag;
import simplexity.villagerinfo.util.Resolvers;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Called when preparing output on a villager. Requires a Bukkit Villager object and Bukkit Player object
 */

public class VillagerOutputEvent extends Event implements Cancellable {
    
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
    @SuppressWarnings("unused")//required by bukkit
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    private final Villager villager;
    private final Player player;
    private boolean cancelled;
    private boolean villHasProfession = true;
    private boolean outputHasInfo = false;
    private boolean playerOutputToggleEnabled;
    private Component outputComponent;
    private final MiniMessage miniMessage = VillagerInfo.getInstance().getMiniMessage();
    
    public VillagerOutputEvent(Villager villager, Player player) {
        this.villager = villager;
        this.player = player;
    }
    
    /**
     * Gets the current game time
     *
     * @return Long
     */
    
    public Long getCurrentGameTime() {
        return villager.getWorld().getGameTime();
    }
    
    /**
     * Gets whether the villager has been lobotomized by Purpur's lobotomize setting
     *
     * @return Boolean
     */
    public Boolean getLobotomized() {
        return villager.isLobotomized();
    }
    
    /**
     * Gets the Component for the Lobotomized Message
     * <br>Uses PURPUR_LOBOTOMIZED from VillagerMessage enum
     * <br>Uses TRUE_MESSAGE_FORMAT and FALSE_MESSAGE_FORMAT from MessageInsert enum
     *
     * @return Component
     */
    
    public Component getLobotomizedMessageComponent() {
        if (!(ConfigToggle.DISPLAY_PURPUR_LOBOTOMIZED.isEnabled() && VillagerInfo.getInstance().isUsingPurpur()))
            return null;
        outputHasInfo = true;
        String status;
        if (getLobotomized()) {
            status = MessageInsert.TRUE_MESSAGE_FORMAT.getMessage();
        } else {
            status = MessageInsert.FALSE_MESSAGE_FORMAT.getMessage();
        }
        return miniMessage.deserialize(VillagerMessage.PURPUR_LOBOTOMIZED.getMessage(), Placeholder.parsed("state", status));
    }
    
    /**
     * Gets how many ticks until the villager becomes an adult
     * <br>Returns 0 if the villager is already an adult
     *
     * @return Long
     */
    
    public Long getChildVillagerAge() {
        if (villager.isAdult()) return 0L;
        long villagerAge = villager.getAge();
        villagerAge = villagerAge * -1;
        return villagerAge;
    }
    
    /**
     * Gets the Component for the Child Villager Age Message
     * <br>Uses BABY_VILLAGER_AGE message from VillagerMessage enum
     *
     * @return Component
     */
    
    public Component getChildVillagerAgeMessageComponent() {
        if (!(ConfigToggle.DISPLAY_BABY_VILLAGER_AGE.isEnabled() && getChildVillagerAge() > 0)) return null;
        outputHasInfo = true;
        villHasProfession = false;
        Long ageInSeconds = getChildVillagerAge() / 20;
        return miniMessage.deserialize(VillagerMessage.BABY_VILLAGER_AGE.getMessage(), Resolvers.getInstance().timeFormatter(ageInSeconds));
    }
    
    /**
     * Gets the GameTime tick when the villager last worked at a workstation
     * <br>Returns 0L if they have never worked at a workstation
     *
     * @return Long
     */
    
    public Long getVillagerLastWorkedGameTime() {
        Long lastWorked = villager.getMemory(MemoryKey.LAST_WORKED_AT_POI);
        return Objects.requireNonNullElse(lastWorked, 0L);
    }
    
    /**
     * Gets the Component for the Villager Last Worked message
     * <br>Uses VILLAGER_LAST_WORKED message from VillagerMessage enum
     *
     * @return Component
     */
    
    public Component getVillagerLastWorkedGameTimeMessageComponent() {
        if (!ConfigToggle.DISPLAY_LAST_WORK_TIME.isEnabled() || !villHasProfession) return null;
        outputHasInfo = true;
        Long lastWorkedGameTimeDifference = (getCurrentGameTime() - getVillagerLastWorkedGameTime()) / 20;
        return miniMessage.deserialize(VillagerMessage.VILLAGER_LAST_WORKED.getMessage(),
                Resolvers.getInstance().timeFormatter(lastWorkedGameTimeDifference));
    }
    
    /**
     * Gets the GameTime tick when the villager last slept
     * <br>Returns 0L if they have never slept
     *
     * @return Long
     */
    
    public Long getVillagerLastSleptGameTime() {
        Long lastSlept = villager.getMemory(MemoryKey.LAST_SLEPT);
        return Objects.requireNonNullElse(lastSlept, 0L);
    }
    
    /**
     * Gets the Component for the Villager Last Slept message
     * <br>Uses VILLAGER_LAST_SLEPT from VillagerMessage enum
     *
     * @return Component
     */
    
    public Component getVillagerLastSleptGameTimeMessageComponent() {
        if (!ConfigToggle.DISPLAY_LAST_SLEEP_TIME.isEnabled()) return null;
        outputHasInfo = true;
        Long lastWorkedGameTimeDifference = (getCurrentGameTime() - getVillagerLastSleptGameTime()) / 20;
        return miniMessage.deserialize(VillagerMessage.VILLAGER_LAST_SLEPT.getMessage(),
                Resolvers.getInstance().timeFormatter(lastWorkedGameTimeDifference));
    }
    
    /**
     * Gets the villager's current health
     *
     * @return Double
     */
    
    public Double getVillagerCurrentHealth() {
        return villager.getHealth();
    }
    
    /**
     * Gets the villager's max health
     *
     * @return Double
     */
    public Double getVillagerMaxHealth() {
        return Objects.requireNonNull(villager.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
    }
    
    /**
     * Gets the Component for the Villager Health message
     * <br>Uses VILLAGER_HEALTH message from VillagerMessage enum
     *
     * @return Component
     */
    
    public Component getVillagerHealthMessageComponent() {
        if (!ConfigToggle.DISPLAY_HEALTH.isEnabled()) return null;
        outputHasInfo = true;
        Double currentHealth = getVillagerCurrentHealth();
        Double maxHealth = getVillagerMaxHealth();
        return miniMessage.deserialize(VillagerMessage.VILLAGER_HEALTH.getMessage(),
                Placeholder.parsed("value", currentHealth.toString()),
                Placeholder.parsed("value2", maxHealth.toString()));
    }
    
    /**
     * Gets the number of times the villager has restocked today
     *
     * @return Integer
     */
    
    public Integer getVillagerRestocksToday() {
        return villager.getRestocksToday();
    }
    
    /**
     * Gets the Component for the Villager Restocks Today message
     * <br>Uses the VILLAGER_RESTOCKS_TODAY message from VillagerMessage enum
     *
     * @return Component
     */
    
    public Component getVillagerRestocksTodayMessageComponent() {
        if (!ConfigToggle.DISPLAY_RESTOCKS_TODAY.isEnabled() || !villHasProfession) return null;
        outputHasInfo = true;
        return miniMessage.deserialize(VillagerMessage.VILLAGER_RESTOCKS_TODAY.getMessage(), Placeholder.parsed("value", getVillagerRestocksToday().toString()));
    }
    
    /**
     * Gets the player's reputation with this villager
     *
     * @return Integer
     */
    
    public Integer getPlayerReputation() {
        if (isLegacyOutput() || isNMSUnsupported()) return getLegacyPlayerReputation();
        net.minecraft.world.entity.npc.Villager nmsVillager = ((CraftVillager) villager).getHandle();
        net.minecraft.world.entity.player.Player nmsPlayer = ((CraftPlayer) player).getHandle();
        return nmsVillager.getPlayerReputation(nmsPlayer);
    }
    
    /**
     * Manually calculates the player's reputation on older versions. May have inaccurate results.
     *
     * @return Integer
     */
    public Integer getLegacyPlayerReputation() {
        Reputation reputation = villager.getReputation(player.getUniqueId());
        if (reputation == null) return 0;
        int reputationMP = reputation.getReputation(ReputationType.MAJOR_POSITIVE);
        int reputationP = reputation.getReputation(ReputationType.MINOR_POSITIVE);
        int reputationMN = reputation.getReputation(ReputationType.MAJOR_NEGATIVE);
        int reputationN = reputation.getReputation(ReputationType.MINOR_NEGATIVE);
        int reputationT = reputation.getReputation(ReputationType.TRADING);
        //5MP+P+T-N-5MN = Total Reputation Score. Maxes at -700, 725
        return (reputationMP * 5) + reputationP + reputationT - reputationN - (reputationMN * 5);
    }
    
    /**
     * Gets the Component for the Player Reputation message
     * <br>Uses REPUTATION_TOTAL_FORMAT message from MessageInsert enum
     * <br>Uses PLAYER_REPUTATION_MESSAGE message from VillagerMessage enum
     *
     * @return Component
     */
    
    public Component getPlayerReputationMessageComponent() {
        if (!ConfigToggle.DISPLAY_PLAYER_REPUTATION.isEnabled()) return null;
        String reputationValue = String.valueOf(getPlayerReputation());
        Component reputationTotalComponent = miniMessage.deserialize(MessageInsert.REPUTATION_TOTAL_FORMAT.getMessage(),
                Placeholder.parsed("value", reputationValue));
        return miniMessage.deserialize(VillagerMessage.PLAYER_REPUTATION_MESSAGE.getMessage(),
                Resolvers.getInstance().playerReputationResolver(getPlayerReputation()),
                Placeholder.component("reputation_number", reputationTotalComponent));
    }
    
    /**
     * Gets this villager's profession
     *
     * @return Villager.Profession
     */
    
    public Villager.Profession getVillagerProfession() {
        return villager.getProfession();
    }
    
    /**
     * Gets the Component for the Villager Profession message
     * <br>Uses VILLAGER_PROFESSION message from VillagerMessage enum
     *
     * @return Component
     */
    
    public Component getVillagerProfessionMessageComponent() {
        if (!ConfigToggle.DISPLAY_PROFESSION.isEnabled() || getChildVillagerAge() != 0) return null;
        outputHasInfo = true;
        Villager.Profession profession = getVillagerProfession();
        if (profession.equals(Villager.Profession.NONE)) villHasProfession = false;
        String professionString = profession.name().toLowerCase();
        return miniMessage.deserialize(VillagerMessage.VILLAGER_PROFESSION.getMessage(),
                Placeholder.parsed("value", professionString));
    }
    
    
    /**
     * Gets this villager's inventory object
     *
     * @return Inventory
     */
    
    public Inventory getVillagerInventory() {
        return villager.getInventory();
    }
    
    /**
     * Gets the Component for an item in a villager's inventory
     * <br> Uses VILLAGER_INVENTORY_ITEM_FORMAT from VillagerMessage enum
     *
     * @param itemStack ItemStack
     *
     * @return Component
     */
    
    public Component villagerInventoryItem(ItemStack itemStack) {
        Component itemComponent;
        Material itemMaterial = itemStack.getType();
        int itemCount = itemStack.getAmount();
        String itemString = String.valueOf(itemMaterial).toLowerCase(Locale.ENGLISH);
        itemComponent = miniMessage.deserialize(
                VillagerMessage.VILLAGER_INVENTORY_ITEM_FORMAT.getMessage(),
                Placeholder.parsed("item", itemString),
                Placeholder.parsed("value", Integer.toString(itemCount)));
        return itemComponent;
        
    }
    
    /**
     * Gets the Component for the Villager Inventory message
     * <br>Uses VILLAGER_INVENTORY from VillagerMessage enum
     * <br>Uses EMPTY_MESSAGE_FORMAT from MessageInsert enum if the inventory is empty
     * <br>Uses villagerInventoryItem() to format item Components
     *
     * @return Component
     */
    
    public Component getVillagerInventoryMessageComponent() {
        if (!ConfigToggle.DISPLAY_VILLAGER_INVENTORY.isEnabled()) return null;
        outputHasInfo = true;
        Component inventoryComponent = Component.empty();
        if (getVillagerInventory().isEmpty()) {
            inventoryComponent = miniMessage.deserialize(MessageInsert.EMPTY_MESSAGE_FORMAT.getMessage());
        } else {
            List<ItemStack> invContents = Arrays.stream(getVillagerInventory().getContents()).toList();
            for (ItemStack item : invContents) {
                if (item == null || item.getType().isAir() || item.getType().isEmpty()) continue;
                inventoryComponent = inventoryComponent.appendNewline().append(villagerInventoryItem(item));
            }
        }
        return miniMessage.deserialize(VillagerMessage.VILLAGER_INVENTORY.getMessage(), Placeholder.component("contents", inventoryComponent));
    }
    
    /**
     * Gets the location of this villager's workstation
     *
     * @return Location
     */
    public Location getVillagerJobsiteLocation() {
        return villager.getMemory(MemoryKey.JOB_SITE);
    }
    
    public Component getVillagerJobsiteLocationMessageComponent() {
        if (!ConfigToggle.DISPLAY_JOB_SITE_LOCATION.isEnabled() || !villHasProfession) return null;
        outputHasInfo = true;
        return miniMessage.deserialize(VillagerMessage.VILLAGER_JOBSITE_LOCATION.getMessage(),
                Resolvers.getInstance().locationBuilder(getVillagerJobsiteLocation()));
    }
    
    /**
     * Gets the location of this villager's bed
     *
     * @return Location
     */
    public Location getVillagerBedLocation() {
        return villager.getMemory(MemoryKey.HOME);
    }
    
    /**
     * Gets the Component for the Villager Bed Location message
     * <br>Uses VILLAGER_BED_LOCATION from VillagerMessage enum
     *
     * @return Component
     */
    public Component getVillagerBedLocationMessageComponent() {
        if (!ConfigToggle.DISPLAY_BED_LOCATION.isEnabled()) return null;
        outputHasInfo = true;
        return miniMessage.deserialize(VillagerMessage.VILLAGER_BED_LOCATION.getMessage(), Resolvers.getInstance().locationBuilder(getVillagerBedLocation()));
    }
    
    /**
     * Uses a legacyNewLine = miniMessage.deserialize("\n"); for older versions, because apparently .appendNewLine() is
     * new
     * <br>Builds Output Component from getLobotomizedMessageComponent(), getChildVillagerAgeMessageComponent(),
     * getVillagerHealthMessageComponent(),
     * getVillagerProfessionMessageComponent(), getVillagerJobsiteLocationMessageComponent(),
     * getVillagerLastWorkedGameTimeMessageComponent(), getVillagerRestocksTodayMessageComponent(),
     * getVillagerBedLocationMessageComponent(), getVillagerLastSleptGameTimeMessageComponent(),
     * getVillagerInventoryMessageComponent(), and getPlayerReputationMessageComponent()
     * <br>Uses PLUGIN_PREFIX from ServerMessage enum
     * <br>If all those methods return null, uses NO_INFORMATION_TO_DISPLAY from VillagerMessage enum
     */
    public void buildLegacyOutputComponent() {
        Component legacyNewLine = miniMessage.deserialize("\n");
        Component tempOutputComponent = miniMessage.deserialize(ServerMessage.PLUGIN_PREFIX.getMessage());
        if (getLobotomizedMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.append(legacyNewLine).append(getLobotomizedMessageComponent());
        }
        if (getChildVillagerAgeMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.append(legacyNewLine).append(getChildVillagerAgeMessageComponent());
        }
        if (getVillagerHealthMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.append(legacyNewLine).append(getVillagerHealthMessageComponent());
        }
        if (getVillagerProfessionMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.append(legacyNewLine).append(getVillagerProfessionMessageComponent());
        }
        if (getVillagerJobsiteLocationMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.append(legacyNewLine).append(getVillagerJobsiteLocationMessageComponent());
        }
        if (getVillagerLastWorkedGameTimeMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.append(legacyNewLine).append(getVillagerLastWorkedGameTimeMessageComponent());
        }
        if (getVillagerRestocksTodayMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.append(legacyNewLine).append(getVillagerRestocksTodayMessageComponent());
        }
        if (getVillagerBedLocationMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.append(legacyNewLine).append(getVillagerBedLocationMessageComponent());
        }
        if (getVillagerLastSleptGameTimeMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.append(legacyNewLine).append(getVillagerLastSleptGameTimeMessageComponent());
        }
        if (getVillagerInventoryMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.append(legacyNewLine).append(getVillagerInventoryMessageComponent());
        }
        if (getPlayerReputationMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.append(legacyNewLine).append(getPlayerReputationMessageComponent());
        }
        if (!outputHasInfo) {
            tempOutputComponent = tempOutputComponent.append(legacyNewLine).append(miniMessage.deserialize(VillagerMessage.NO_INFORMATION_TO_DISPLAY.getMessage()));
        }
        setOutputComponent(tempOutputComponent);
    }
    
    /**
     * Builds Output Component from getLobotomizedMessageComponent(), getChildVillagerAgeMessageComponent(),
     * getVillagerHealthMessageComponent(), getVillagerProfessionMessageComponent(),
     * getVillagerJobsiteLocationMessageComponent(), getVillagerLastWorkedGameTimeMessageComponent(),
     * getVillagerRestocksTodayMessageComponent(), getVillagerBedLocationMessageComponent(),
     * getVillagerLastSleptGameTimeMessageComponent(), getVillagerInventoryMessageComponent(), and
     * getPlayerReputationMessageComponent()
     * <br>Uses PLUGIN_PREFIX from ServerMessage enum
     * <br>If all those methods return null, uses NO_INFORMATION_TO_DISPLAY from VillagerMessage enum
     */
    public void buildOutputComponent() {
        Component tempOutputComponent = miniMessage.deserialize(ServerMessage.PLUGIN_PREFIX.getMessage());
        if (getLobotomizedMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.appendNewline().append(getLobotomizedMessageComponent());
        }
        if (getChildVillagerAgeMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.appendNewline().append(getChildVillagerAgeMessageComponent());
        }
        if (getVillagerHealthMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.appendNewline().append(getVillagerHealthMessageComponent());
        }
        if (getVillagerProfessionMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.appendNewline().append(getVillagerProfessionMessageComponent());
        }
        if (getVillagerJobsiteLocationMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.appendNewline().append(getVillagerJobsiteLocationMessageComponent());
        }
        if (getVillagerLastWorkedGameTimeMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.appendNewline().append(getVillagerLastWorkedGameTimeMessageComponent());
        }
        if (getVillagerRestocksTodayMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.appendNewline().append(getVillagerRestocksTodayMessageComponent());
        }
        if (getVillagerBedLocationMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.appendNewline().append(getVillagerBedLocationMessageComponent());
        }
        if (getVillagerLastSleptGameTimeMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.appendNewline().append(getVillagerLastSleptGameTimeMessageComponent());
        }
        if (getVillagerInventoryMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.appendNewline().append(getVillagerInventoryMessageComponent());
        }
        if (getPlayerReputationMessageComponent() != null) {
            tempOutputComponent = tempOutputComponent.appendNewline().append(getPlayerReputationMessageComponent());
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
        if (isLegacyOutput()) {
            buildLegacyOutputComponent();
        } else {
            buildOutputComponent();
        }
        player.sendMessage(outputComponent);
    }
    
    /**
     * Gets this villager
     *
     * @return org.bukkit.entity.Villager
     */
    
    public Villager getVillager() {
        return villager;
    }
    
    /**
     * Gets the player involved in this event
     *
     * @return org.bukkit.entity.Player
     */
    
    public Player getPlayer() {
        return player;
    }
    
    /**
     * Checks player's output toggle state and sets playerOutputToggleEnabled
     */
    public void checkPlayerPDC() {
        byte outputToggleState = player.getPersistentDataContainer().getOrDefault(PDCTag.PLAYER_TOGGLE_OUTPUT_ENABLED.getPdcTag(), PersistentDataType.BYTE, (byte) 0);
        playerOutputToggleEnabled = outputToggleState != (byte) 1;
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
    
    @SuppressWarnings("unused") //For API usage
    public Component getOutputComponent() {
        return outputComponent;
    }
    
    /**
     * Gets whether to use legacy output or not (pre-1.19.4)
     *
     * @return boolean
     */
    
    public boolean isLegacyOutput() {
        return VillagerInfo.getInstance().isLegacyVersion();
    }
    
    /**
     * Gets whether the server is running the same version that the plugin runs on
     *
     * @return boolean
     */
    public boolean isNMSUnsupported() {
        return VillagerInfo.getInstance().isNmsUnsupported();
    }
}
