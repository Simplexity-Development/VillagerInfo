package adhdmc.villagerinfo.VillagerHandling;

import org.bukkit.ChatColor;

public class ReputationHandler {

    public static String villagerReputation(int repNum) {
        StringBuilder output = new StringBuilder();
        int minVal = -14;
        int maxVal = 15;
        int devNum = repNum/50;
        boolean isPos = Math.abs(devNum) == devNum;
        for (int i = minVal; i <=maxVal; i++){
            if (i == 0){
                output.append(ChatColor.WHITE + "[").append(repNum).append("]");
                continue;
            }
            if (Math.abs(i) != i){
                if (!isPos && i < 0 && i >=devNum){
                    output.append(ChatColor.RED + "▬");
                    continue;
                }
                output.append(ChatColor.GRAY + "•");
            }
            if (Math.abs(i) == i) {
                if (isPos && i > 0 && i <= devNum) {
                    output.append(ChatColor.GREEN + "▬");
                    continue;
                }
                output.append(ChatColor.GRAY + "•");
            }
        }
        return output.toString();
    }

}
