package simplexity.villagerinfo.interaction.logic;

import com.destroystokyo.paper.entity.villager.Reputation;
import com.destroystokyo.paper.entity.villager.ReputationType;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.inventory.Inventory;
import simplexity.villagerinfo.VillagerInfo;

@SuppressWarnings("FieldMayBeFinal")
public class VillagerData {
    private Boolean purpurLobotomized;
    private Boolean golemDetectedRecently;
    private Integer babyVillagerAge;
    private Integer restocksToday;
    private Long lastWorkTime;
    private Long lastSleepTime;
    private final Long currentGameTime;
    private Double villagerCurrentHealth;
    private Double villagerMaxHealth;
    private Location jobSiteLocation;
    private Location bedLocation;
    private Location meetupLocation;
    private Villager.Profession profession;
    private Inventory villagerInventory;
    private Reputation playerReputation;


    public VillagerData(Villager villager, Player player) {
        AttributeInstance maxHealth = villager.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        Boolean golemDetected = villager.getMemory(MemoryKey.GOLEM_DETECTED_RECENTLY);
        Long lastWorked = villager.getMemory(MemoryKey.LAST_WORKED_AT_POI);
        Long lastSlept = villager.getMemory(MemoryKey.LAST_SLEPT);
        Location homeLocation = villager.getMemory(MemoryKey.HOME);
        Location villageMeetLocation = villager.getMemory(MemoryKey.MEETING_POINT);
        Location jobLocation = villager.getMemory(MemoryKey.JOB_SITE);
        if (VillagerInfo.getInstance().isUsingPurpur()) purpurLobotomized = villager.isLobotomized();
        if (!villager.isAdult()) babyVillagerAge = villager.getAge();
        if (golemDetected != null) golemDetectedRecently = golemDetected;
        if (maxHealth != null) villagerMaxHealth = maxHealth.getValue();
        if (jobLocation != null) jobSiteLocation = jobLocation;
        if (lastWorked != null) lastWorkTime = lastWorked;
        if (homeLocation != null) bedLocation = homeLocation;
        if (lastSlept != null) lastSleepTime = lastSlept;
        if (villageMeetLocation != null) meetupLocation = villageMeetLocation;
        villagerCurrentHealth = villager.getHealth();
        profession = villager.getProfession();
        villagerInventory = villager.getInventory();
        restocksToday = villager.getRestocksToday();
        playerReputation = villager.getReputation(player.getUniqueId());
        currentGameTime = villager.getWorld().getGameTime();
    }

    public Integer getPlayerReputation() {
        if (playerReputation == null) return 0;
        int reputationMP = playerReputation.getReputation(ReputationType.MAJOR_POSITIVE);
        int reputationP = playerReputation.getReputation(ReputationType.MINOR_POSITIVE);
        int reputationMN = playerReputation.getReputation(ReputationType.MAJOR_NEGATIVE);
        int reputationN = playerReputation.getReputation(ReputationType.MINOR_NEGATIVE);
        int reputationT = playerReputation.getReputation(ReputationType.TRADING);
        //5MP+P+T-N-5MN = Total Reputation Score. Maxes at -700, 725
        return (reputationMP * 5) + reputationP + reputationT - reputationN - (reputationMN * 5);
    }

    public Integer getBabyVillagerAgeSeconds(){
        if (babyVillagerAge == null) return null;
        return babyVillagerAge / 20;
    }

    public Long getTimeSinceSleptSeconds(){
        if (lastSleepTime == null) return null;
        long timeDifference = currentGameTime - lastSleepTime;
        return timeDifference / 20;
    }

    public Long getTimeSinceWorkedSeconds(){
        if (lastWorkTime == null) return null;
        long timeDifference = currentGameTime - lastWorkTime;
        return timeDifference / 20;
    }

    public Boolean isLobotomized() {
        return purpurLobotomized;
    }

    public Boolean wasGolemDetectedRecently() {
        return golemDetectedRecently;
    }

    public Integer getRestocksToday() {
        return restocksToday;
    }

    public Double getVillagerCurrentHealth() {
        return villagerCurrentHealth;
    }

    public Double getVillagerMaxHealth() {
        return villagerMaxHealth;
    }

    public Location getJobSiteLocation() {
        return jobSiteLocation;
    }

    public Location getBedLocation() {
        return bedLocation;
    }

    public Location getMeetupLocation() {
        return meetupLocation;
    }

    public Villager.Profession getProfession() {
        return profession;
    }

    public Inventory getVillagerInventory() {
        return villagerInventory;
    }

}
