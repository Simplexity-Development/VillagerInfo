package adhdmc.villagerinfo.Commands.SubCommands;

import adhdmc.villagerinfo.Commands.SubCommand;
import adhdmc.villagerinfo.Config.ConfigValidator;
import adhdmc.villagerinfo.Config.ConfigValidator.Message;
import adhdmc.villagerinfo.VillagerInfo;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Map;

public class ToggleCommand extends SubCommand {
    NamespacedKey infoToggle = new NamespacedKey(VillagerInfo.plugin, "infoToggle");
    String enabled = VillagerInfo.isEnabled;
    String disabled = VillagerInfo.isDisabled;
    Map<Message, String> msgs = ConfigValidator.getLocaleMap();

    public ToggleCommand() {
        super("toggle", "Toggles villager info on and off", "/vill toggle");
    }

    @Override
    public void doThing(CommandSender sender, String[] args) {
        MiniMessage mM = MiniMessage.miniMessage();
        if (!(sender instanceof Player)) {
            sender.sendMessage(mM.deserialize(msgs.get(Message.NOT_A_PLAYER)));
            return;
        }
        if (!(sender.hasPermission(VillagerInfo.toggleCommandPermission))) {
            sender.sendMessage(mM.deserialize(msgs.get(Message.NO_PERMISSION)));
            return;
        }
        if (toggleSetting((Player) sender)) {
            sender.sendMessage(mM.deserialize(msgs.get(Message.PREFIX))
                    .append(mM.deserialize(msgs.get(Message.TOGGLE_ON))));
            return;
        }
        sender.sendMessage(mM.deserialize(msgs.get(Message.PREFIX))
                .append(mM.deserialize(msgs.get(Message.TOGGLE_OFF))));
    }

    private boolean toggleSetting(Player p) {
        PersistentDataContainer playerPDC = p.getPersistentDataContainer();
        String togglePDC = playerPDC.get(infoToggle, PersistentDataType.STRING);
        if (togglePDC == null || togglePDC.equals(disabled)) {
            playerPDC.set(infoToggle, PersistentDataType.STRING, enabled);
            return false;
        }
        playerPDC.set(infoToggle, PersistentDataType.STRING, disabled);
        return true;
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return null;
    }
}
