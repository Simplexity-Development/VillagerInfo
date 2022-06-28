package adhdmc.villagerinfo.MiscHandling;

public class TimeFormatting {
    public static String timeMath(long mathTime){
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
        if(mathTimeB == 1) mathResult += mathTimeB + " Hour, ";
        if(mathTimeB > 1) mathResult += mathTimeB + " Hours, ";
        if(mathTimeC == 1) mathResult += mathTimeC + " Minute, ";
        if(mathTimeC > 1) mathResult += mathTimeC + " Minutes, ";
        if(mathTimeD == 1) mathResult += mathTimeD + " Second Ago";
        if(mathTimeD > 1 || mathTimeD == 0) mathResult += mathTimeD + " Seconds Ago";
        return mathResult;
    }
}
