package simplexity.villagerinfo.interaction.logic;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import simplexity.villagerinfo.VillagerInfo;
import simplexity.villagerinfo.commands.villagerinfo.subcommands.toggle.subcommands.InteractType;
import simplexity.villagerinfo.commands.villagerinfo.subcommands.toggle.subcommands.InteractTypeToggle;
import simplexity.villagerinfo.configurations.locale.ServerMessage;
import simplexity.villagerinfo.util.Resolvers;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class PlayerToggle {

    public static void setPdcToggleDisabled(Player player, NamespacedKey namespacedKey) {
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        playerPDC.set(namespacedKey, PersistentDataType.BYTE, (byte) 1);
    }

    public static void setPdcToggleEnabled(Player player, NamespacedKey namespacedKey) {
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        playerPDC.set(namespacedKey, PersistentDataType.BYTE, (byte) 0);
    }

    public static boolean isPdcToggleEnabled(Player player, NamespacedKey namespacedKey) {
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        return playerPDC.getOrDefault(namespacedKey, PersistentDataType.BYTE, (byte) 0) == 0;
    }

    /**
     * Checks if this interaction type is allowed based on the player's settings
     *
     * @param player Player
     * @param type   InteractType
     * @return boolean
     */
    public static boolean interactTypeAllowed(Player player, InteractType type) {
        PersistentDataContainer playerPdc = player.getPersistentDataContainer();
        String typeString = playerPdc.getOrDefault(InteractTypeToggle.interactTypeKey, PersistentDataType.STRING, InteractType.RIGHT_CLICK.getCommandName());
        InteractType setType = InteractType.getInteractType(typeString);
        if (setType == null) return false;
        if (setType.equals(InteractType.BOTH)) return true;
        return setType.equals(type);
    }


    /**
     * Sends the player feedback on what the toggle was set to.
     * <br>Uses TOGGLE_COMMAND_FEEDBACK from ServerMessage enum
     * <br>Uses ENABLED_MESSAGE_FORMAT, DISABLED_MESSAGE_FORMAT, TOGGLE_TYPE_HIGHLIGHT, TOGGLE_TYPE_OUTPUT, and TOGGLE_TYPE_SOUND from MessageInsert enum
     *
     * @param state String
     */

    public static void sendPlayerFeedback(boolean state, Player player, String toggleType) {
        player.sendMessage(VillagerInfo.getInstance().getMiniMessage().deserialize(ServerMessage.TOGGLE_COMMAND_FEEDBACK.getMessage(),
                Placeholder.parsed("plugin_prefix", ServerMessage.PLUGIN_PREFIX.getMessage()),
                Placeholder.parsed("value", toggleType),
                Resolvers.getInstance().enabledStateResolver(state)));
    }
}
