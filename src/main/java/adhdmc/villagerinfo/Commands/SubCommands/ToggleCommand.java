package adhdmc.villagerinfo.Commands.SubCommands;

import adhdmc.villagerinfo.Commands.SubCommand;
import adhdmc.villagerinfo.Config.ConfigValidator;
import adhdmc.villagerinfo.VillagerInfo;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.List;

public class ToggleCommand extends SubCommand {
    NamespacedKey infoToggle = new NamespacedKey(VillagerInfo.plugin, "infoToggle");
    String enabled = VillagerInfo.isEnabled;
    String disabled = VillagerInfo.isDisabled;
    MiniMessage mM = MiniMessage.miniMessage();
    HashMap<String, String> messages = ConfigValidator.localeMap;

    public ToggleCommand() {
        super("toggle", "Toggles villager info on and off","/vill toggle");
    }

    @Override
    public void doThing(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(mM.deserialize(messages.get("not-a-player")));
            return;
        }
        if(!(sender.hasPermission(VillagerInfo.toggleCommandPermission))) {
            sender.sendMessage(mM.deserialize(messages.get("no-permission")));
            return;
        }
        if (toggleSetting((Player) sender)) {
            sender.sendMessage(mM.deserialize(messages.get("prefix"))
                    .append(Component.text(messages.get("toggle-on"))));
            return;
        }
        sender.sendMessage(mM.deserialize(messages.get("prefix"))
                .append(Component.text(messages.get("toggle-off"))));
    }

    private boolean toggleSetting(Player p) {
        PersistentDataContainer playerPDC = p.getPersistentDataContainer();
        String togglePDC = playerPDC.get(infoToggle, PersistentDataType.STRING);
        if (togglePDC == null || togglePDC.equals(disabled)){
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
