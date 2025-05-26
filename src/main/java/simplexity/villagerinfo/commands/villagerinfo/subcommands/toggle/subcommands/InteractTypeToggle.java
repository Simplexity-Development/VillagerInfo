package simplexity.villagerinfo.commands.villagerinfo.subcommands.toggle.subcommands;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import simplexity.villagerinfo.commands.util.SubCommand;
import simplexity.villagerinfo.configurations.locale.ServerMessage;
import simplexity.villagerinfo.util.Perm;

import java.util.ArrayList;
import java.util.List;

public class InteractTypeToggle extends SubCommand {

    public InteractTypeToggle() {
        super(Perm.VILL_COMMAND_TOGGLE_INTERACT_TYPE.getPerm(), ServerMessage.HELP_TOGGLE_INTERACT_TYPE.getMessage());
    }

    public static final NamespacedKey interactTypeKey = new NamespacedKey("vill-info", "interact-type");

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(ServerMessage.NOT_A_PLAYER.getMessage());
            return;
        }
        if (args.length < 3) {
            sender.sendRichMessage(ServerMessage.NOT_ENOUGH_ARGUMENTS.getMessage());
            return;
        }
        String interactTypeString = args[2].toLowerCase();
        InteractType type = InteractType.getInteractType(interactTypeString);
        if (type == null) {
            sender.sendRichMessage(ServerMessage.ERROR_INVALID_ARGUMENT.getMessage());
            return;
        }
        PersistentDataContainer playerPdc = player.getPersistentDataContainer();
        playerPdc.set(interactTypeKey, PersistentDataType.STRING, type.getCommandName());
        player.sendRichMessage(ServerMessage.TOGGLE_INTERACT_FEEDBACK.getMessage(),
                Placeholder.parsed("plugin_prefix", ServerMessage.PLUGIN_PREFIX.getMessage()),
                Placeholder.parsed("state", type.getMessageInsert().getMessage()));
    }

    @Override
    public List<String> subCommandTabCompletions(CommandSender sender, String[] args) {
        List<String> subcommands = new ArrayList<>();
        for (InteractType type : InteractType.values()) {
            subcommands.add(type.getCommandName());
        }
        return subcommands;
    }
}
