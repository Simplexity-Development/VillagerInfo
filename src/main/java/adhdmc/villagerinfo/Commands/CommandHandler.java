package adhdmc.villagerinfo.Commands;

import adhdmc.villagerinfo.Config.ConfigValidator;
import adhdmc.villagerinfo.Config.Message;
import adhdmc.villagerinfo.VillagerInfo;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.*;

public class CommandHandler implements CommandExecutor, TabExecutor {

    public static HashMap<String, SubCommand> subcommandList = new HashMap<>();
    MiniMessage miniMessage = VillagerInfo.getMiniMessage();

    //TY Peashooter101
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> subCommands1 = new ArrayList<>(Arrays.asList("help", "toggle", "reload"));
        if (args.length == 1) {
            return subCommands1;
        }
        return null;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Checking for arguments
        if (args.length == 0) {
            String url = VillagerInfo.getInstance().getDescription().getWebsite();
            String version = VillagerInfo.getInstance().getDescription().getVersion();
            List<String> authors = new ArrayList<>();
            for (String authorName : VillagerInfo.getInstance().getDescription().getAuthors()) {
                authors.add(String.format("<gold> %s </gold>", authorName));
            }
            String authorsString = String.join(" | ", authors);
            sender.sendMessage(miniMessage.deserialize(
                    "<green><click:open_url:'<url>'><hover:show_text:'<gray>Click here to visit the GitHub!'>VillagerInfo | Version:<version> </hover></click>\nAuthors: <authors>",
                    Placeholder.parsed("version", version),
                    Placeholder.parsed("authors", authorsString),
                    Placeholder.unparsed("url", url)
                    )
            );
            return true;
        }
        //if has an argument, check to see if it's contained in the list of arguments
        String command = args[0].toLowerCase();
        if (subcommandList.containsKey(command)) {
            subcommandList.get(command).execute(sender, Arrays.copyOfRange(args, 1, args.length));
        } else {
            sender.sendMessage(miniMessage.deserialize(Message.NO_COMMAND.getMessage()));
        }
        return true;
    }
}
