package adhdmc.villagerinfo.MiscHandling;

import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
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
    prefix = colorParse(config.getString("prefix"));
    toggleOn = colorParse(config.getString("toggleOn"));
    toggleOff = colorParse(config.getString("toggleOff"));
    noPermission = colorParse(config.getString("noPermission"));
    noPermissionToggle = colorParse(config.getString("noPermissionToggle"));
    noCommand = colorParse(config.getString("noCommand"));
    configReload = colorParse(config.getString("configReload"));
    helpMain =  colorParse(config.getString("helpMain"));
    helpToggle = colorParse(config.getString("helpToggle"));
    helpReload = colorParse(config.getString("helpReload"));
    notAPlayer = colorParse(config.getString("notAPlayer"));
    villagerProfessionMsg = colorParse(config.getString("VillagerProfession"));
    villagerJobsiteMsg = colorParse(config.getString("villagerJobsiteMsg"));
    villagerLastWorkedMsg = colorParse(config.getString("villagerLastWorkedMsg"));
    villagerNumRestocksMsg = colorParse(config.getString("villagerNumRestocksMsg"));
    villagerHomeMsg = colorParse(config.getString("villagerHomeMsg"));
    villagerSleptMsg = colorParse(config.getString("villagerSleptMsg"));
    villagerInventoryMsg = colorParse(config.getString("villagerInventoryMsg"));
    villagerSeparatorMsg = colorParse(config.getString("villagerSeparatorMsg"));
    villagerNoneMsg = colorParse(config.getString("villagerNoneMsg"));
    villagerNeverMsg = colorParse(config.getString("villagerNeverMsg"));
    villagerEmptyMsg = colorParse(config.getString("villagerEmptyMsg"));
    playerReputationMsg = colorParse(config.getString("playerReputationMsg"));
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
    public enum MESSAGE_TYPE {
        PREFIX,
        PROFESSION_MSG,
        JOBSITE_MSG,
        LAST_WORKED_MSG,
        RESTOCK_NUMBER_MSG,
        HOME_MSG,
        LAST_SLEPT_MSG,
        INVENTORY_MSG,
        SEPARATOR_MSG,
        NONE_MSG,
        NEVER_MSG,
        EMPTY_MSG,
        REPUTATION_MSG
    }

    public static HashMap<MESSAGE_TYPE, String> getMessages(){
        HashMap<MESSAGE_TYPE, String> msgMap = new HashMap<>();
        msgMap.put(MESSAGE_TYPE.PREFIX, prefix);
        msgMap.put(MESSAGE_TYPE.PROFESSION_MSG, villagerProfessionMsg);
        msgMap.put(MESSAGE_TYPE.JOBSITE_MSG, villagerJobsiteMsg);
        msgMap.put(MESSAGE_TYPE.LAST_WORKED_MSG, villagerLastWorkedMsg);
        msgMap.put(MESSAGE_TYPE.RESTOCK_NUMBER_MSG, villagerNumRestocksMsg);
        msgMap.put(MESSAGE_TYPE.HOME_MSG, villagerHomeMsg);
        msgMap.put(MESSAGE_TYPE.LAST_SLEPT_MSG, villagerSleptMsg);
        msgMap.put(MESSAGE_TYPE.INVENTORY_MSG, villagerInventoryMsg);
        msgMap.put(MESSAGE_TYPE.SEPARATOR_MSG, villagerSeparatorMsg);
        msgMap.put(MESSAGE_TYPE.NONE_MSG, villagerNoneMsg);
        msgMap.put(MESSAGE_TYPE.NEVER_MSG, villagerNeverMsg);
        msgMap.put(MESSAGE_TYPE.EMPTY_MSG, villagerEmptyMsg);
        msgMap.put(MESSAGE_TYPE.REPUTATION_MSG, playerReputationMsg);
        return msgMap;
    }



}
