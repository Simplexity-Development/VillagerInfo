package adhdmc.villagerinfo.VillagerHandling;

import adhdmc.villagerinfo.Config.ConfigValidator;
import adhdmc.villagerinfo.MiscHandling.TimeFormatting;
import adhdmc.villagerinfo.VillagerInfo;
import com.destroystokyo.paper.entity.villager.Reputation;
import com.destroystokyo.paper.entity.villager.ReputationType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static org.bukkit.entity.EntityType.SHULKER;

public class VillagerHandler implements Listener {
    public static HashMap<UUID, Shulker> workstationShulker = new HashMap<>();
    public static HashMap<UUID, PersistentDataContainer> villagerPDC = new HashMap<>();
    public static Location villagerJobsiteLocation;
    NamespacedKey infoToggle = new NamespacedKey(VillagerInfo.plugin, "infoToggle");
    NamespacedKey isHighlighted = new NamespacedKey(VillagerInfo.plugin, "isHighlighted");

    byte disabled = 0;
    byte enabled = 1;

    @EventHandler
    public void onVillagerClick(PlayerInteractEntityEvent event) {
        MiniMessage mM = MiniMessage.miniMessage();
        HashMap<String, String> messages = ConfigValidator.localeMap;
        FileConfiguration config = VillagerInfo.plugin.getConfig();
        Player player = event.getPlayer();
        UUID pUUID = player.getUniqueId();
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        if (event.getHand().equals(EquipmentSlot.OFF_HAND)) {
            return;
        }
        if (!event.getPlayer().isSneaking()) {
            return;
        }
        if (!(event.getRightClicked() instanceof Villager villager)) {
            return;
        }
        Byte togglePDC = playerPDC.get(infoToggle, PersistentDataType.BYTE);
        if (togglePDC != null && togglePDC.equals(enabled)) {
            return;
        }
        if (!player.hasPermission("villagerinfo.use")) {
            return;
        }
        event.setCancelled(true);
        long worldTimeTotal = villager.getWorld().getFullTime();
        UUID villagerUUID = villager.getUniqueId();
        PersistentDataContainer villagerPDC = villager.getPersistentDataContainer();
        //profession
        String villagerProfessionString;
        Villager.Profession villagerProfession = villager.getProfession();
        if (villagerProfession == Villager.Profession.NONE) {
            villagerProfessionString = messages.get("noneString");
        } else {
            villagerProfessionString = villagerProfession.toString();
        }
        //job-site
        Location villagerJobLocation = villager.getMemory(MemoryKey.JOB_SITE);
        String villagerJobSite = null;
        int villagerJobSiteX = 0;
        int villagerJobSiteY = 0;
        int villagerJobSiteZ = 0;
        if (villagerJobLocation != null){
         villagerJobSiteX = villagerJobLocation.getBlockX();
         villagerJobSiteY = villagerJobLocation.getBlockY();
         villagerJobSiteZ = villagerJobLocation.getBlockZ();
        } else {
            //villagerJobSite = messages.get("noneString");
            villagerJobSite = "debug: none";
        }
        //last-worked
        Long lastWorked = villager.getMemory(MemoryKey.LAST_WORKED_AT_POI);
        String villagerLastWorked;
        if ( lastWorked != null) {
            villagerLastWorked = TimeFormatting.timeMath(worldTimeTotal - lastWorked);
        } else {
            //villagerLastWorked = messages.get("neverString");
            villagerLastWorked = "debug: never";
        }
        //bed-location
        Location bedLocation = villager.getMemory(MemoryKey.HOME);
        String villagerBed = null;
        int villagerBedX = 0;
        int villagerBedY = 0;
        int villagerBedZ = 0;
        if (bedLocation != null){
            villagerJobsiteHighlight(villagerPDC, villagerUUID);
            villagerBedX = bedLocation.getBlockX();
            villagerBedY = bedLocation.getBlockY();
            villagerBedZ = bedLocation.getBlockZ();
        } else {
            //villagerBed = messages.get("noneString");
            villagerBed = "debug: none";
        }
        //last-slept
        Long lastSlept = villager.getMemory(MemoryKey.LAST_SLEPT);
        String villagerLastSlept;
        if ( lastSlept != null) {
            villagerLastSlept = TimeFormatting.timeMath(worldTimeTotal - lastSlept);
        } else {
            //villagerLastSlept = messages.get("neverString");
            villagerLastSlept = "debug: never";
        }
        //inventory
        Inventory villInventory = villager.getInventory();
        String villagerInventory = null;
        if (villInventory.isEmpty()){
            //villagerInventory = messages.get("empty-msg");
            villagerInventory = "debug: empty";
        } else {
            ItemStack[] inventoryItems = villInventory.getContents();
            for (ItemStack items : inventoryItems) {
                if(items == null) continue;
                villagerInventory = villagerInventory +
                        items.displayName() + "(" + items.getAmount() + ")\n";
            }
        }
        //restocks
        int villagerRestocks = villager.getRestocksToday();
        //reputation
        int reputationRawTotal = reputationTotal(villager, pUUID);
        String playerVillagerReputation = ReputationHandler.villagerReputation(reputationRawTotal);
        //Messages
        ArrayList<Component> messageList = new ArrayList<>();
        if(config.getBoolean("profession")){
            Component professionMessage;
            if (villagerProfession == Villager.Profession.NONE){
                professionMessage = mM.deserialize(villagerProfessionString);
            } else {
                professionMessage = mM.deserialize(messages.get("villager-profession"), Placeholder.parsed("profession", villagerProfessionString));
            }
            messageList.add(professionMessage);
        }
        if(config.getBoolean("job-site")) {
            Component jobLocationMessage;
        if(villagerJobLocation == null){
            jobLocationMessage = mM.deserialize(villagerJobSite);
        }else{
            Component jobX = mM.deserialize(messages.get("location-x"), Placeholder.unparsed("int", String.valueOf(villagerJobSiteX)));
            Component jobY = mM.deserialize(messages.get("location-y"), Placeholder.unparsed("int", String.valueOf(villagerJobSiteY)));
            Component jobZ = mM.deserialize(messages.get("location-z"), Placeholder.unparsed("int", String.valueOf(villagerJobSiteZ)));
            jobLocationMessage = mM.deserialize(messages.get("villager-jobsite-msg"),
                    Placeholder.unparsed("jobsitelocation", String.valueOf(jobX.append(jobY).append(jobZ))));
        }
            messageList.add(jobLocationMessage);
        }
        if(config.getBoolean("last-worked")){
            Component lastWorkedMessage;
            if(lastWorked == null){
                lastWorkedMessage = mM.deserialize(villagerLastWorked);
            } else {
                lastWorkedMessage = mM.deserialize(messages.get("villager-last-worked-msg"), Placeholder.unparsed("worktime", villagerLastWorked));
            }
            messageList.add(lastWorkedMessage);
        }
        if(config.getBoolean("bed-location")){
            Component bedLocationMessage;
            if(bedLocation == null){
                bedLocationMessage = mM.deserialize(villagerBed);

            } else {
                Component bedX = mM.deserialize(messages.get("location-x"), Placeholder.unparsed("int", String.valueOf(villagerBedX)));
                Component bedY = mM.deserialize(messages.get("location-y"), Placeholder.unparsed("int", String.valueOf(villagerBedY)));
                Component bedZ = mM.deserialize(messages.get("location-z"), Placeholder.unparsed("int", String.valueOf(villagerBedZ)));
                bedLocationMessage = mM.deserialize(messages.get("villager-home-msg"), Placeholder.unparsed("homelocation", String.valueOf(bedX.append(bedY).append(bedZ))));
            }
            messageList.add(bedLocationMessage);
        }
        if(config.getBoolean("last-slept")){
            Component lastSleptMessage;
            if (lastSlept == null){
                lastSleptMessage = mM.deserialize(villagerLastSlept);
            } else {
                lastSleptMessage = mM.deserialize(messages.get("villager-last-slept"), Placeholder.unparsed("sleeptime", villagerLastSlept));
            }
            messageList.add(lastSleptMessage);
        }
        if(config.getBoolean("inventory")){
            Component villagerInventoryMessage = null;
            if(villagerInventory == null) {
                mM.deserialize(messages.get("villager-inventory-msg"), Placeholder.unparsed("contents", messages.get("empty-message")));
            } else {
                villagerInventoryMessage = mM.deserialize(messages.get("villager-inventory-msg"), Placeholder.unparsed("contents", villagerInventory));
            }
            messageList.add(villagerInventoryMessage);
        }
        if(config.getBoolean("restocks")){
            Component villagerRestocksMessage = mM.deserialize(messages.get("villager-num-restocks-msg"), Placeholder.unparsed("restockcount", String.valueOf(villagerRestocks)));
            messageList.add(villagerRestocksMessage);
        }
        if (config.getBoolean("reputation")){
            Component playerVillagerReputationMessage = mM.deserialize(messages.get("player-reputation-msg"), Placeholder.unparsed("reputation", playerVillagerReputation));
            messageList.add(playerVillagerReputationMessage);
        }
        Component prefix = mM.deserialize(messages.get("prefix"));
        player.sendMessage(prefix);
        for (Component component : messageList){
            player.sendMessage(component);
        }
    }

    private void villagerJobsiteHighlight(PersistentDataContainer villPDC, UUID villUUID){
        Byte getHighlightPDC = villPDC.get(isHighlighted, PersistentDataType.BYTE);
        if (getHighlightPDC == null || getHighlightPDC.equals(disabled)){
                villPDC.set(isHighlighted,PersistentDataType.BYTE, enabled);
                villagerPDC.put(villUUID,villPDC);
                spawnShulker(villUUID);
                new BukkitRunnable(){
                @Override
                public void run(){
                    killShulker(villUUID);
                    villPDC.set(isHighlighted,PersistentDataType.BYTE, disabled);
                    villagerPDC.put(villUUID,villPDC);
                }
            }.runTaskLater(VillagerInfo.plugin,20L* ConfigValidator.configTime);
        }
    }
    private int reputationTotal(Villager vil, UUID p){
        Reputation playerReputation = vil.getReputation(p);
        if(playerReputation == null) return 0;
        int playerReputationMP = playerReputation.getReputation(ReputationType.MAJOR_POSITIVE);
        int playerReputationP = playerReputation.getReputation(ReputationType.MINOR_POSITIVE);
        int playerReputationMN = playerReputation.getReputation(ReputationType.MAJOR_NEGATIVE);
        int playerReputationN = playerReputation.getReputation(ReputationType.MINOR_NEGATIVE);
        int playerReputationT = playerReputation.getReputation(ReputationType.TRADING);
        //5MP+P+T-N-5MN = Total Reputation Score. Maxes at -700, 725
        return (playerReputationMP * 5) + playerReputationP + playerReputationT - playerReputationN - (playerReputationMN * 5);
    }
    private void spawnShulker(UUID uuid){
        Shulker spawnedShulker = (Shulker) villagerJobsiteLocation.getWorld().spawnEntity(villagerJobsiteLocation, SHULKER, CreatureSpawnEvent.SpawnReason.CUSTOM, (Entity) ->{
            Shulker highlightbox = (Shulker) Entity;
            highlightbox.setAI(false);
            highlightbox.setAware(false);
            highlightbox.setCollidable(false);
            highlightbox.setGlowing(true);
            highlightbox.setInvisible(true);
            highlightbox.setInvulnerable(true);
        });
        workstationShulker.put(uuid, spawnedShulker);
    }
    public static void killShulker(UUID uuid){
        workstationShulker.get(uuid).remove();
        workstationShulker.remove(uuid);
    }
}
