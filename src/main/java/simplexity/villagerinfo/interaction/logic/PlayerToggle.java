package simplexity.villagerinfo.interaction.logic;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import simplexity.villagerinfo.VillagerInfo;
import simplexity.villagerinfo.configurations.locale.ServerMessage;

public class PlayerToggle {

    private static final MiniMessage miniMessage = VillagerInfo.getInstance().getMiniMessage();

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
     * Sends the player feedback on what the toggle was set to.
     * <br>Uses TOGGLE_COMMAND_FEEDBACK from ServerMessage enum
     * <br>Uses ENABLED_MESSAGE_FORMAT, DISABLED_MESSAGE_FORMAT, TOGGLE_TYPE_HIGHLIGHT, TOGGLE_TYPE_OUTPUT, and TOGGLE_TYPE_SOUND from MessageInsert enum
     *
     * @param state String
     */

    private static void sendPlayerFeedback(String state, Player player, String toggleType) {
        player.sendMessage(VillagerInfo.getInstance().getMiniMessage().deserialize(ServerMessage.TOGGLE_COMMAND_FEEDBACK.getMessage(),
                Placeholder.parsed("plugin_prefix", ServerMessage.PLUGIN_PREFIX.getMessage()),
                Placeholder.parsed("value", toggleType),
                Placeholder.parsed("state", state)));
    }
}
