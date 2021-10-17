package adhdmc.villagerinfo;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageHandler {
    private static final Pattern hexPattern = Pattern.compile("(&#[a-fA-F0-9]{6})");

    public static String prefix;
    public static String toggleOn;
    public static String toggleOff;
    public static String noPermission;
    public static String noCommand;
    public static String configReload;
    public static String helpMain;
    public static String helpToggle;
    public static String helpReload;

    public static String colorParse(String s) {
        Matcher matcher = hexPattern.matcher(s);
        while (matcher.find()) {
            String colorReplace = s.substring(matcher.start(), matcher.end());
            String colorHex = s.substring(matcher.start()+1, matcher.end());
            s = s.replace(colorReplace, "" + net.md_5.bungee.api.ChatColor.of(colorHex));
            matcher = hexPattern.matcher(s);
        }
        s = ChatColor.translateAlternateColorCodes('&', s);
        return s;
    }

    public static void loadConfigMsgs(){
    prefix = colorParse(VillagerInfo.plugin.getConfig().getString("Prefix"));
    toggleOn = colorParse(VillagerInfo.plugin.getConfig().getString("Toggle On"));
    toggleOff = colorParse(VillagerInfo.plugin.getConfig().getString("Toggle Off"));
    noPermission = colorParse(VillagerInfo.plugin.getConfig().getString("No Permission"));
    noCommand = colorParse(VillagerInfo.plugin.getConfig().getString("No Command"));
    configReload = colorParse(VillagerInfo.plugin.getConfig().getString("Config Reloaded"));
    helpMain =  colorParse("&#4dd5ff• How to use Villager Info\n&7Shift-right-click a villager while toggle is on to have a villager's information displayed");
    helpToggle = colorParse("&#4dd5ff•/vill toggle\n&7Toggles the ability to receive villager information on or off.");
    helpReload = colorParse("&#4dd5ff•/vill reload\n&7Reloads the plugin, applies config values");
    }
}
