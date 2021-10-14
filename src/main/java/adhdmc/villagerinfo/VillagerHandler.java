package adhdmc.villagerinfo;

import com.destroystokyo.paper.entity.villager.Reputation;
import com.destroystokyo.paper.entity.villager.ReputationType;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

import static org.bukkit.ChatColor.*;

public class VillagerHandler implements Listener {
    public static HashMap<UUID, Boolean> villagerCheck = new HashMap<UUID,Boolean>();

    @EventHandler
    public void onVillagerClick(PlayerInteractEntityEvent event) {
        //DECLARATION OF INDEPENDENCE
        //Player
        Player player = event.getPlayer();
        //Player UUID
        UUID pUUID = player.getUniqueId();
        //Get Entity
        Entity clickedEntity = event.getRightClicked();
        //Villager
        Villager villagerClicked = (Villager) clickedEntity;
        //Profession
        String villagerProfession = villagerClicked.getProfession().toString();
        //Restocks
        int villagerRestocks = villagerClicked.getRestocksToday();
        //Job Site
        Location villagerJobSite = villagerClicked.getMemory(MemoryKey.JOB_SITE);
        //Worked
        Long villagerWorked = villagerClicked.getMemory(MemoryKey.LAST_WORKED_AT_POI);
        //Home
        Location villagerHome = villagerClicked.getMemory(MemoryKey.HOME);
        //Slept
        Long villagerSlept = villagerClicked.getMemory(MemoryKey.LAST_SLEPT);
        //Inventory
        ItemStack[] villagerInventoryContents = villagerClicked.getInventory().getContents();
        //REPUTATION STUFFFFFFFFFFFFFFF
        Reputation playerReputation = villagerClicked.getReputation(pUUID);
        assert playerReputation != null;
        int playerReputationMP = playerReputation.getReputation(ReputationType.MAJOR_POSITIVE);
        int playerReputationP = playerReputation.getReputation(ReputationType.MINOR_POSITIVE);
        int playerReputationMN = playerReputation.getReputation(ReputationType.MAJOR_NEGATIVE);
        int playerReputationN = playerReputation.getReputation(ReputationType.MINOR_NEGATIVE);
        int playerReputationT = playerReputation.getReputation(ReputationType.TRADING);
        //5MP+P+T-N-5MN = Total Reputation Score. Maxes at -700, 725
        int playerReputationTotal = (playerReputationMP * 5) + playerReputationP + playerReputationT - playerReputationN - (playerReputationMN * 5);
        //
        //
        //
        //Stops the stuff from running twice, cause, 2 hands
        if(event.getHand().equals(EquipmentSlot.OFF_HAND)) {
            return;
        }
        //Checks if they're shift-clicking or not
        if(!player.isSneaking()) {
            return;
        }
        //Checks if they've toggled it off *HOPEFULLY*
        if(villagerCheck.containsKey(pUUID)){
            if (!villagerCheck.get(pUUID)){
                return;
            }
        }
        //Checks if the thing is a villager
        if (clickedEntity.getType() != EntityType.VILLAGER) {
            player.sendMessage(RED + "Sorry, you must click a villager.\nYou clicked a " + clickedEntity.getType() + "!");
            return;
        }
        //Cancels the event cause, opening trade window
        event.setCancelled(true);
        //Villager Inventory
        StringBuilder villagerInventoryString = new StringBuilder(GREEN + "VILLAGER INVENTORY:");
        for (int i = 0; i < villagerInventoryContents.length; i++) {
            ItemStack villagerInventoryItem = villagerClicked.getInventory().getItem(i);
            if (villagerInventoryItem != null) {
                villagerInventoryString.append("\n  ").append(AQUA).append("• ").append(villagerInventoryItem.getType()).append(GRAY).append(" (").append(villagerInventoryItem.getAmount()).append(")");
            }
        }
        //Profession
        player.sendMessage(GREEN + "PROFESSION:\n  " + AQUA  + "• " + villagerProfession);
        if (villagerJobSite != null) {
            player.sendMessage(GREEN + "JOB SITE:\n  " + AQUA + "• " + villagerJobSite.getBlockX() + "x, " + villagerJobSite.getBlockY() + "y, " + villagerJobSite.getBlockZ() + "z");
            villagerJobSite.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, (villagerJobSite.getX() + 0.5), (villagerJobSite.getY() + 1.5), (villagerJobSite.getZ() + 0.5), 10);
        } else {
            player.sendMessage(GREEN + "JOB SITE:\n  " + AQUA + "• NONE");
        }
        //Worked
        if(villagerWorked != null){
            String villagerWorkedString = GREEN + "LAST WORKED AT WORKSTATION:\n  " + AQUA + "• ";
            long mathTime = villagerClicked.getWorld().getGameTime() - villagerWorked;
            player.sendMessage(villagerWorkedString + timeMath(mathTime));
        } else {
            player.sendMessage(GREEN + "LAST WORKED AT WORKSTATION:\n  " + AQUA + "• NEVER");
        }
        //Restocks
        player.sendMessage(GREEN + "RESTOCKS TODAY:\n  " + AQUA + "• " + villagerRestocks);
        //Home
        if (villagerHome != null) {
            player.sendMessage(GREEN + "HOME:\n  " + AQUA + "• " + villagerHome.getBlockX() + "x, " + villagerHome.getBlockY() + "y, " + villagerHome.getBlockZ() + "z");
        } else {
            player.sendMessage(GREEN + "HOME:\n  " + AQUA + "• NONE");
        }
        //Slept
        if (villagerSlept != null) {
            String villagerSleptString = GREEN + "LAST SLEPT:\n  " + AQUA + "• ";
            long mathTime = villagerClicked.getWorld().getGameTime() - villagerSlept;
            player.sendMessage(villagerSleptString + timeMath(mathTime));
        } else {
            player.sendMessage(GREEN + "LAST SLEPT:\n  " + AQUA + "• NEVER");
        }
        //Inventory
        if(villagerInventoryString.toString().equals(GREEN + "VILLAGER INVENTORY:")){
            player.sendMessage(villagerInventoryString.toString() + AQUA + "\n  • EMPTY");
        } else {
            player.sendMessage(villagerInventoryString.toString());
        }
        //Reputation
        // [|||||I|||||I|||||I|||||I|||||["+ playerReputationTotal +"]|||||I|||||I|||||I|||||I|||||]
        String playerReputationString = GREEN + "YOUR REPUTATION\n";
        //FUTURE REPUTATION STUFF YEET
        player.sendMessage(playerReputationString);
    }
    //Math
    private String timeMath(long mathTime){
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
