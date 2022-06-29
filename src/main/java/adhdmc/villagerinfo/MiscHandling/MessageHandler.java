package adhdmc.villagerinfo.MiscHandling;

import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageHandler {
    public static FileConfiguration config = VillagerInfo.plugin.getConfig();
    private static final Pattern hexPattern = Pattern.compile("(&#[a-fA-F0-9]{6})");

    public static String prefix;
    public static String toggleOn, toggleOff, configReload;
    public static String noPermission, noPermissionToggle, noCommand, timeError, soundError, notAPlayer;
    public static String helpMain, helpToggle, helpReload;
    public static String villagerProfessionMsg, villagerJobsiteMsg, villagerLastWorkedMsg, villagerNumRestocksMsg, villagerHomeMsg, villagerSleptMsg, villagerInventoryMsg, villagerNoneMsg, villagerNeverMsg, villagerSeparatorMsg, playerReputationMsg, villagerEmptyMsg;


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
    prefix = colorParse(config.getString("Prefix"));
    toggleOn = colorParse(config.getString("Toggle On"));
    toggleOff = colorParse(config.getString("Toggle Off"));
    noPermission = colorParse(config.getString("No Permission"));
    noPermissionToggle = colorParse(config.getString("No Permission Vill Toggle"));
    noCommand = colorParse(config.getString("No Command"));
    configReload = colorParse(config.getString("Config Reloaded"));
    helpMain =  colorParse("&#4dd5ff• How to use Villager Info\n&7Shift-right-click a villager while toggle is on to have a villager's information displayed");
    helpToggle = colorParse("&#4dd5ff•/vill toggle\n&7Toggles the ability to receive villager information on or off.");
    helpReload = colorParse("&#4dd5ff•/vill reload\n&7Reloads the plugin, applies config values");
    notAPlayer = colorParse("&cSorry, you must be a player to use this command");
    villagerProfessionMsg = colorParse("&aPROFESSION:\n");
    villagerJobsiteMsg = colorParse("&aJOB SITE:\n");
    villagerLastWorkedMsg = colorParse("&aLAST WORKED AT WORKSTATION:\n");
    villagerNumRestocksMsg = colorParse("&aRESTOCKS TODAY:\n");
    villagerHomeMsg = colorParse("&aHOME:\n");
    villagerSleptMsg = colorParse("&aLAST SLEPT:\n");
    villagerInventoryMsg = colorParse("&aVILLAGER INVENTORY:");
    villagerSeparatorMsg = colorParse("&b• ");
    villagerNoneMsg = colorParse("&7NONE");
    villagerNeverMsg = colorParse("&7NEVER");
    villagerEmptyMsg = colorParse("&7EMPTY");
    playerReputationMsg = colorParse("&aPLAYER REPUTATION:\n");
    soundError = soundErrorMsg("");
    timeError = timeErrorMsg("");
    }

    public static String soundErrorMsg(String s){
        Sound configSound = null;
        try {
            configSound = Sound.valueOf(config.getString("Sound"));
        } catch (IllegalArgumentException e) {
            s = ChatColor.RED + "Configuration Error: Invalid Sound. Please choose from these options.\nhttps://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html";
        }
        if (configSound != null) {
            s=null;
        }
        return s;
    }

    public static String timeErrorMsg(String s){
        int configTime = config.getInt("Length of time to highlight workstation");
        if (!(configTime > 0)){
            s = ChatColor.RED + "Configuration Error: Invalid highlight time.\nIf you would like to disable this feature,\nplease set 'highlight workstation' to false.\nOtherwise please use an integer greater than zero";
        }
        return s;
    }
}
