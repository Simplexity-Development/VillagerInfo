package adhdmc.villagerclasses;

import com.destroystokyo.paper.entity.villager.Reputation;
import com.destroystokyo.paper.entity.villager.ReputationType;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.ChatColor.*;

public class VillagerHandler implements Listener {
    @EventHandler
    public void onVillagerClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity clickedEntity = event.getRightClicked();
        if (clickedEntity.getType() != EntityType.VILLAGER) {
            player.sendMessage("Sorry, you must click a villager!");
            return;
        }
        event.setCancelled(true);
        Villager villagerClicked = (Villager) clickedEntity;
        String villagerProfession = villagerClicked.getProfession().toString();
        Integer villagerRestocks = villagerClicked.getRestocksToday();
        Location villagerJobSite = villagerClicked.getMemory(MemoryKey.JOB_SITE);
        Long villagerWorked = villagerClicked.getMemory(MemoryKey.LAST_WORKED_AT_POI);
        Location villagerHome = villagerClicked.getMemory(MemoryKey.HOME);
        Long villagerSlept = villagerClicked.getMemory(MemoryKey.LAST_SLEPT);
        Reputation playerReputation = villagerClicked.getReputation(player.getUniqueId());
        assert playerReputation != null;
        int playerReputationMP = playerReputation.getReputation(ReputationType.MAJOR_POSITIVE);
        int playerReputationP = playerReputation.getReputation(ReputationType.MINOR_POSITIVE);
        int playerReputationMN = playerReputation.getReputation(ReputationType.MAJOR_NEGATIVE);
        int playerReputationN = playerReputation.getReputation(ReputationType.MINOR_NEGATIVE);
        int playerReputationT = playerReputation.getReputation(ReputationType.TRADING);
        //5MP+P+T-N-5MN maxes at -700, 725
        int playerReputationTotal = (playerReputationMP * 5) + playerReputationP + playerReputationT - playerReputationN - (playerReputationMN * 5);
        // [|||||I|||||I|||||I|||||I|||||["+ playerReputationTotal +"]|||||I|||||I|||||I|||||I|||||]
        String playerReputationString = GREEN + "YOUR REPUTATION\n";
        if (-550> playerReputationTotal && playerReputationTotal>=-700) {
            playerReputationString += DARK_RED + "[|||||I|||||I|||||I|||||I|||||" + DARK_GRAY + "[" + RED + playerReputationTotal + DARK_GRAY + "]" + GRAY + "|||||I|||||I|||||I|||||I|||||]";
        }
        if (-350> playerReputationTotal && playerReputationTotal>=-550) {
            playerReputationString += RED + "[|||||I|||||I" + DARK_RED + "|||||I|||||I|||||" + DARK_GRAY + "[" + RED + playerReputationTotal + DARK_GRAY + "]" + GRAY + "|||||I|||||I|||||I|||||I|||||]";
        }
        if (-150> playerReputationTotal && playerReputationTotal>=-350) {
            playerReputationString += RED + "[|||||I|||||I|||||I|||||I|||||" + DARK_GRAY + "[" + RED + playerReputationTotal + DARK_GRAY + "]" + GRAY + "|||||I|||||I|||||I|||||I|||||]";
        }
        if (0> playerReputationTotal && playerReputationTotal>=-150) {
            playerReputationString += GRAY + "[|||||I|||||I" + RED + "|||||I|||||I|||||" + DARK_GRAY + "[" + RED + playerReputationTotal + DARK_GRAY + "]" + GRAY +"|||||I|||||I|||||I|||||I|||||]";
        }
        if (playerReputationTotal == 0){
            playerReputationString += GRAY + "[|||||I|||||I|||||I|||||I|||||" + DARK_GRAY + "[" + GRAY + playerReputationTotal + DARK_GRAY + "]" + GRAY + "|||||I|||||I|||||I|||||I|||||]";
        }
        if (0< playerReputationTotal && playerReputationTotal<=150) {
            playerReputationString += GRAY + "[|||||I|||||I|||||I|||||I|||||" + DARK_GRAY + "[" + GREEN + playerReputationTotal + DARK_GRAY +"]" + GREEN + "|||||I|||||I|||||" + GRAY + "I|||||I|||||]";
        }
        if (150<playerReputationTotal && playerReputationTotal<=350){
            playerReputationString += GRAY + "[|||||I|||||I|||||I|||||I|||||" + DARK_GRAY + "[" + GREEN + playerReputationTotal + DARK_GRAY +"]" + GREEN + "|||||I|||||I|||||I|||||I|||||]";
        }
        if (350< playerReputationTotal && playerReputationTotal<=550) {
            playerReputationString += GRAY + "[|||||I|||||I|||||I|||||I|||||" + DARK_GRAY + "[" + GREEN + playerReputationTotal + DARK_GRAY +"]" + DARK_GREEN + "|||||I|||||I|||||" + GREEN + "I|||||I|||||]";
        }
        if (550<playerReputationTotal && playerReputationTotal<=700){
            playerReputationString += GRAY + "[|||||I|||||I|||||I|||||I|||||" + DARK_GRAY + "[" + GREEN + playerReputationTotal + DARK_GRAY +"]" + DARK_GREEN + "|||||I|||||I|||||I|||||I|||||]";
        }
        //Villager Inventory
        ItemStack[] villagerInventoryContents = villagerClicked.getInventory().getContents();
        String villagerInventoryString = GREEN + "VILLAGER INVENTORY:";
        for (int i = 0; i < villagerInventoryContents.length; i++) {
            ItemStack villagerInventoryItem = villagerClicked.getInventory().getItem(i);
            if (villagerInventoryItem != null) {
                villagerInventoryString += "\n  " + AQUA + villagerInventoryItem.getType() + GRAY + " (" + villagerInventoryItem.getAmount() + ")";
            }
        }
        player.sendMessage(GREEN + "PROFESSION:\n  " + AQUA + villagerProfession);
        if(villagerWorked != null){
            String villagerWorkedString = GREEN + "LAST WORKED AT WORKSTATION:\n  " + AQUA;
            Long mathTime = villagerClicked.getWorld().getGameTime() - villagerWorked;
            //Remainder after dividing by 72,000 (one hour)
            Long mathTime2 = mathTime % 72000;
            //Normal number from dividing (hours)
            Long mathTimeB = mathTime / 72000;
            //Remainder after dividing by 1200 (1 minute)
            Long mathTime3 = mathTime2 % 1200;
            //Normal number from dividing (minutes)
            Long mathTimeC = mathTime2 / 1200;
            //Normal number from dividing (seconds)
            Long mathTimeD = mathTime3 / 20;
            if(mathTimeB == 1) villagerWorkedString += mathTimeB + " Hour, ";
            if(mathTimeB > 1) villagerWorkedString += mathTimeB + " Hours, ";
            if(mathTimeC == 1) villagerWorkedString += mathTimeC + " Minute, ";
            if(mathTimeC > 1) villagerWorkedString += mathTimeC + " Minutes, ";
            if(mathTimeD == 1) villagerWorkedString += mathTimeD + " Second Ago";
            if(mathTimeD > 1 || mathTimeD == 0) villagerWorkedString += mathTimeD + " Seconds Ago";
            player.sendMessage(villagerWorkedString);
        } else {
            player.sendMessage(GREEN + "LAST WORKED AT WORKSTATION:\n  " + AQUA + "NEVER");
        }
        player.sendMessage(GREEN + "RESTOCKS TODAY:\n  " + AQUA + villagerRestocks);
            //Raw Time in ticks since sleeping
        if (villagerSlept != null) {
            String villagerSleptString = GREEN + "LAST SLEPT:\n  " + AQUA;
            Long mathTime = villagerClicked.getWorld().getGameTime() - villagerSlept;
            //Remainder after dividing by 72,000 (one hour)
            Long mathTime2 = mathTime % 72000;
            //Normal number from dividing (hours)
            Long mathTimeB = mathTime / 72000;
            //Remainder after dividing by 1200 (1 minute)
            Long mathTime3 = mathTime2 % 1200;
            //Normal number from dividing (minutes)
            Long mathTimeC = mathTime2 / 1200;
            //Normal number from dividing (seconds)
            Long mathTimeD = mathTime3 / 20;
            if(mathTimeB == 1) villagerSleptString += mathTimeB + " Hour, ";
            if(mathTimeB > 1) villagerSleptString += mathTimeB + " Hours, ";
            if(mathTimeC == 1) villagerSleptString += mathTimeC + " Minute, ";
            if(mathTimeC > 1) villagerSleptString += mathTimeC + " Minutes, ";
            if(mathTimeD == 1) villagerSleptString += mathTimeD + " Second Ago";
            if(mathTimeD > 1 || mathTimeD == 0) villagerSleptString += mathTimeD + " Seconds Ago";
            player.sendMessage(villagerSleptString);
        } else {
            player.sendMessage(GREEN + "LAST SLEPT:\n  " + AQUA + "NEVER");
        }
        if (villagerJobSite != null) {
            player.sendMessage(GREEN + "JOB SITE:\n  " + AQUA + villagerJobSite.getBlockX() + "x, " + villagerJobSite.getBlockY() + "y, " + villagerJobSite.getBlockZ() + "z");
            villagerJobSite.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, (villagerJobSite.getX() + 0.5), (villagerJobSite.getY() + 1.5), (villagerJobSite.getZ() + 0.5), 10);
        } else {
            player.sendMessage(GREEN + "JOB SITE:\n  " + AQUA + "NONE");
        }
        if (villagerHome != null) {
            player.sendMessage(GREEN + "HOME:\n  " + AQUA + villagerHome.getBlockX() + "x, " + villagerHome.getBlockY() + "y, " + villagerHome.getBlockZ() + "z");
        } else {
            player.sendMessage(GREEN + "HOME:\n  " + AQUA + "NONE");
        }
        if(villagerInventoryString.equals(GREEN + "VILLAGER INVENTORY:")){
            player.sendMessage(villagerInventoryString + AQUA + "\n  EMPTY");
        } else {
            player.sendMessage(villagerInventoryString);
        }
        player.sendMessage(playerReputationString);
    }
}
