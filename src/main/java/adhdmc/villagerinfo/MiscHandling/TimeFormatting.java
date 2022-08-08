package adhdmc.villagerinfo.MiscHandling;

import adhdmc.villagerinfo.Config.ConfigValidator;
import adhdmc.villagerinfo.Config.ConfigValidator.Message;

import java.util.Map;

public class TimeFormatting {
    public static String timeMath(long mathTime) {
        Map<Message, String> msgs = ConfigValidator.getLocaleMap();
        String mathResult = "";
        //Remainder after dividing by 72,000 (one hour)
        long mathTime2 = mathTime % 72000;
        //Normal number from dividing (hours)
        long mathTimeB = mathTime / 72000;
        //Remainder after dividing by 1200 (1 minute)
        long mathTime3 = mathTime2 % 1200;
        //Normal number from dividing (minutes)
        long mathTimeC = mathTime2 / 1200;
        //Normal number from dividing (seconds)
        long mathTimeD = mathTime3 / 20;
        if (mathTimeB == 1) mathResult += mathTimeB + msgs.get(Message.HOUR);
        if (mathTimeB > 1) mathResult += mathTimeB + msgs.get(Message.HOURS);
        if (mathTimeC == 1) mathResult += mathTimeC + msgs.get(Message.MINUTE);
        if (mathTimeC > 1) mathResult += mathTimeC + msgs.get(Message.MINUTES);
        if (mathTimeD == 1) mathResult += mathTimeD + msgs.get(Message.SECOND_AGO);
        if (mathTimeD > 1 || mathTimeD == 0) mathResult += mathTimeD + msgs.get(Message.SECONDS_AGO);
        return mathResult;
    }
}
