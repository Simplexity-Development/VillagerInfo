package adhdmc.villagerinfo.VillagerHandling;

import adhdmc.villagerinfo.Config.VIMessage;
import adhdmc.villagerinfo.VillagerInfo;
import com.destroystokyo.paper.entity.villager.Reputation;
import com.destroystokyo.paper.entity.villager.ReputationType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class ComponentHandler {
    private static final MiniMessage miniMessage = VillagerInfo.getMiniMessage();
    private static final boolean usingPurpur = VillagerInfo.usingPurpur;

    /**
     * Checks and returns formatted 'time till adult' message component
     * @param ageable Clicked Villager
     * @return Formatted Time Component
     */
    public static Component timeTillAdult(Ageable ageable) {
        Component timeTillAdultFinal;
        long age = ageable.getAge();
        age = age * -1;
        if (age == 0) {
            return null;
        }
        String timeCalc = timeMath(age);
        timeTillAdultFinal = miniMessage.deserialize(VIMessage.VILLAGER_AGE.getMessage(), Placeholder.unparsed("age", timeCalc));
        return timeTillAdultFinal;
    }

    public static Component villagerHealth(LivingEntity entity) {
        Component villagerHealthFinal;
        double maxHealth = Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
        double currentHealth = entity.getHealth();
        villagerHealthFinal = miniMessage.deserialize(VIMessage.VILLAGER_HEALTH.getMessage(),
                Placeholder.unparsed("current", String.valueOf(currentHealth)),
                Placeholder.unparsed("total", String.valueOf(maxHealth)));
        return villagerHealthFinal;
    }

    public static Component villagerLobotomized(Villager villager) {
        Component villagerLobotomizedFinal;
        if(usingPurpur) {
            try {
                villager.isLobotomized();
            } catch (NoSuchMethodError e) {
                VillagerInfo.usingPurpur = false;
                return null;
            }
            String state = "";
            if (villager.isLobotomized()) state = VIMessage.TRUE.getMessage();
            if (!villager.isLobotomized()) state = VIMessage.FALSE.getMessage();
            villagerLobotomizedFinal = miniMessage.deserialize(VIMessage.PURPUR_LOBOTOMIZED.getMessage(),
                    Placeholder.parsed("state", state));
            return villagerLobotomizedFinal;
        }
        return null;
    }

    /**
     * Checks and returns profession component
     * @param profession profession
     * @return Profession Component
     */
    public static Component villagerProfession(Villager.Profession profession) {
        Component professionFinal;
        if (profession == Villager.Profession.NONE) {
            professionFinal = miniMessage.deserialize(VIMessage.VILLAGER_PROFESSION.getMessage(), Placeholder.parsed("profession", VIMessage.NONE.getMessage()));
        } else {
            professionFinal = miniMessage.deserialize(VIMessage.VILLAGER_PROFESSION.getMessage(), Placeholder.parsed("profession", profession.toString()));
        }
        return professionFinal;
    }

    /**
     * Checks and returns formatted jobsite location component
     * @param villager clicked villager
     * @return Jobsite Location Component
     */
    public static Component villagerJobSite(Villager villager) {
        Location villagerJobLocation = villager.getMemory(MemoryKey.JOB_SITE);
        Component jobSiteFinal;
        if (villagerJobLocation == null) {
            jobSiteFinal = miniMessage.deserialize(VIMessage.VILLAGER_JOBSITE.getMessage(),
                    Placeholder.parsed("jobsitelocation", VIMessage.NONE.getMessage()));
        } else {
            Component jobX = miniMessage.deserialize(VIMessage.LOCATION_X.getMessage(),
                    Placeholder.unparsed("int", String.valueOf(villagerJobLocation.getBlockX())));
            Component jobY = miniMessage.deserialize(VIMessage.LOCATION_Y.getMessage(),
                    Placeholder.unparsed("int", String.valueOf(villagerJobLocation.getBlockY())));
            Component jobZ = miniMessage.deserialize(VIMessage.LOCATION_Z.getMessage(),
                    Placeholder.unparsed("int", String.valueOf(villagerJobLocation.getBlockZ())));
            jobSiteFinal = miniMessage.deserialize(VIMessage.VILLAGER_JOBSITE.getMessage(),
                    Placeholder.component("jobsitelocation", jobX.append(jobY).append(jobZ)));
        }
        return jobSiteFinal;
    }

    /**
     * Checks and returns formatted 'villager last worked' message component
     * @param villager Clicked Villager
     * @return Formatted Time Component
     */
    public static Component villagerLastWorked(Villager villager) {
        Component villagerLastWorkedFinal;
        Long lastWorked = villager.getMemory(MemoryKey.LAST_WORKED_AT_POI);
        if (lastWorked == null) {
            villagerLastWorkedFinal = miniMessage.deserialize(VIMessage.VILLAGER_LAST_WORKED.getMessage(),
                    Placeholder.parsed("worktime", VIMessage.NEVER.getMessage()));
        } else {
            Long timeSinceWorked = villager.getWorld().getGameTime() - lastWorked;
            String formattedTime = timeMath(timeSinceWorked) + VIMessage.AGO.getMessage();
            villagerLastWorkedFinal = miniMessage.deserialize(VIMessage.VILLAGER_LAST_WORKED.getMessage(),
                    Placeholder.unparsed("worktime", formattedTime));
        }
        return villagerLastWorkedFinal;
    }

    /**
     * Checks and returns formatted home location component
     * @param villager clicked villager
     * @return Home Location Component
     */
    public static Component villagerBed(Villager villager) {
        Component villagerBedFinal;
        Location bedLocation = villager.getMemory(MemoryKey.HOME);
        if (bedLocation == null) {
            villagerBedFinal = miniMessage.deserialize(VIMessage.VILLAGER_HOME.getMessage(),
                    Placeholder.parsed("homelocation", VIMessage.NONE.getMessage()));
        } else {
            Component bedX = miniMessage.deserialize(VIMessage.LOCATION_X.getMessage(),
                    Placeholder.unparsed("int", String.valueOf(bedLocation.getBlockX())));
            Component bedY = miniMessage.deserialize(VIMessage.LOCATION_Y.getMessage(),
                    Placeholder.unparsed("int", String.valueOf(bedLocation.getBlockY())));
            Component bedZ = miniMessage.deserialize(VIMessage.LOCATION_Z.getMessage(),
                    Placeholder.unparsed("int", String.valueOf(bedLocation.getBlockZ())));
            villagerBedFinal = miniMessage.deserialize(VIMessage.VILLAGER_HOME.getMessage(),
                    Placeholder.component("homelocation", bedX.append(bedY).append(bedZ)));
        }
        return villagerBedFinal;
    }

    /**
     * Checks and returns formatted 'villager last slept' message component
     * @param villager Clicked Villager
     * @return Formatted Time Component
     */
    public static Component villagerLastSlept(Villager villager) {
        Component villagerLastSleptFinal;
        Long lastSlept = villager.getMemory(MemoryKey.LAST_SLEPT);
        if (lastSlept == null) {
            villagerLastSleptFinal = miniMessage.deserialize(VIMessage.VILLAGER_SLEPT.getMessage(),
                    Placeholder.parsed("sleeptime", VIMessage.NEVER.getMessage()));
        } else {
            Long timeSinceSlept = villager.getWorld().getGameTime() - lastSlept;
            String formattedTime = timeMath(timeSinceSlept) + VIMessage.AGO.getMessage() ;
            villagerLastSleptFinal = miniMessage.deserialize(VIMessage.VILLAGER_SLEPT.getMessage(),
                    Placeholder.unparsed("sleeptime", formattedTime));
        }
        return villagerLastSleptFinal;
    }

    /**
     * Checks and lists all the items in a villager's inventory
     * @param villager Clicked Villager
     * @return Inventory List Component
     */
    public static Component villagerInventory(Villager villager) {
        Component villagerInventoryFinal;
        if (villager.getInventory().isEmpty()) {
            villagerInventoryFinal = miniMessage.deserialize(VIMessage.VILLAGER_INVENTORY.getMessage(), Placeholder.parsed("contents", VIMessage.EMPTY.getMessage()));
        } else {
            Component villagerInventory = Component.text("");
            String inventoryOutput = VIMessage.INVENTORY_CONTENTS.getMessage();
            ItemStack[] inventoryItems = villager.getInventory().getContents();
            for (ItemStack items : inventoryItems) {
                if (items == null) continue;
                villagerInventory = villagerInventory.append(miniMessage.deserialize(inventoryOutput, Placeholder.parsed("item", items.getType().toString()), Placeholder.parsed("amount", String.valueOf(items.getAmount()))));
            }
            villagerInventoryFinal = miniMessage.deserialize(VIMessage.VILLAGER_INVENTORY.getMessage(), Placeholder.component("contents", villagerInventory));
        }
        return villagerInventoryFinal;
    }

    /**
     * Gets the number of times a villager has restocked that day and returns a component
     * @param villager Clicked Villager
     * @return Restock Count Component
     */
    public static Component villagerRestocks(Villager villager) {
        if (villager.getRestocksToday() == 0) {
            return miniMessage.deserialize(VIMessage.VILLAGER_RESTOCKS.getMessage(), Placeholder.parsed("restockcount", VIMessage.NONE.getMessage()));
        } else {
            return miniMessage.deserialize(VIMessage.VILLAGER_RESTOCKS.getMessage(), Placeholder.parsed("restockcount", String.valueOf(villager.getRestocksToday())));
        }
    }

    /**
     * Gets a player's reputation and returns a component
     * @param reputation Reputation to evaluate
     * @return Reputation Component
     */
    public static Component villagerPlayerReputation(Reputation reputation) {
        Component villagerReputationFinal;
        int reputationRawTotal = reputationTotal(reputation);
        String playerReputation = ReputationHandler.villagerReputation(reputationRawTotal);
        villagerReputationFinal = miniMessage.deserialize(VIMessage.PLAYER_REPUTATION.getMessage(), Placeholder.unparsed("reputation", playerReputation));
        return villagerReputationFinal;

    }

    /**
     * Checks and returns formatted 'time till converted' message component
     * @param zombieVillager Clicked Zombie Villager
     * @return Formatted Time Component
     */

    public static Component timeTillConverted(ZombieVillager zombieVillager){
        long conversionTime = zombieVillager.getConversionTime();
        String timeCalc = ComponentHandler.timeMath(conversionTime);
        return miniMessage.deserialize(VIMessage.ZOMBIE_VILLAGER_CONVERSION_TIME.getMessage(), Placeholder.unparsed("time", timeCalc));
    }

    /**
     * Calculates the total reputation of a player, using all reputation types
     * @param reputation reputation to calculate
     * @return Total reputation score
     */
    private static int reputationTotal(Reputation reputation) {
        if (reputation == null) return 0;
        int reputationMP = reputation.getReputation(ReputationType.MAJOR_POSITIVE);
        int reputationP = reputation.getReputation(ReputationType.MINOR_POSITIVE);
        int reputationMN = reputation.getReputation(ReputationType.MAJOR_NEGATIVE);
        int reputationN = reputation.getReputation(ReputationType.MINOR_NEGATIVE);
        int reputationT = reputation.getReputation(ReputationType.TRADING);
        //5MP+P+T-N-5MN = Total Reputation Score. Maxes at -700, 725
        return (reputationMP * 5) + reputationP + reputationT - reputationN - (reputationMN * 5);
    }

    /**
     * Formats a tick-based-time into a human-readable format
     * @param mathTime Time in Ticks
     * @return Formatted String
     */
    private static String timeMath(Long mathTime) {
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
        if (mathTimeB > 0) mathResult += mathTimeB + VIMessage.HOUR.getMessage();
        if (mathTimeC > 0) mathResult += mathTimeC + VIMessage.MINUTE.getMessage();
        if (mathTimeD > 0) mathResult += mathTimeD + VIMessage.SECOND.getMessage();
        if (mathResult.isEmpty()) {
            mathResult += "0" + VIMessage.SECOND.getMessage();
        }
        return mathResult;
    }
}
