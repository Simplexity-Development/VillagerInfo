package adhdmc.villagerinfo.MiscHandling;

import adhdmc.villagerinfo.Config.ConfigValidator;

import java.util.HashMap;

public class TimeFormatting {
    public static String timeMath(long mathTime){
        HashMap<String, String> msgs = ConfigValidator.localeMap;
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
        if(mathTimeB == 1) mathResult += mathTimeB + msgs.get("hour");
        if(mathTimeB > 1) mathResult += mathTimeB + msgs.get("hours");
        if(mathTimeC == 1) mathResult += mathTimeC + msgs.get("minute");
        if(mathTimeC > 1) mathResult += mathTimeC + msgs.get("minutes");
        if(mathTimeD == 1) mathResult += mathTimeD + msgs.get("second-ago");
        if(mathTimeD > 1 || mathTimeD == 0) mathResult += mathTimeD + msgs.get("seconds-ago");
        return mathResult;
    }
}
