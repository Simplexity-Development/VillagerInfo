package adhdmc.villagerinfo.MiscHandling;

import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.regex.Pattern;

public class MessageHandler {
    public static FileConfiguration config = VillagerInfo.plugin.getConfig();
    private static final Pattern hexPattern = Pattern.compile("(&#[a-fA-F0-9]{6})");

    public static String prefix;
    public static String toggleOn, toggleOff, configReload;
    public static String noPermission, noPermissionToggle, noCommand, timeError, soundError, notAPlayer;
    public static String helpMain, helpToggle, helpReload;
    public static String villagerProfessionMsg, villagerJobsiteMsg, villagerLastWorkedMsg, villagerNumRestocksMsg, villagerHomeMsg, villagerSleptMsg, villagerInventoryMsg, villagerNoneMsg, villagerNeverMsg, villagerSeparatorMsg, playerReputationMsg, villagerEmptyMsg;






}
