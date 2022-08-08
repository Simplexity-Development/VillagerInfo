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
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import adhdmc.villagerinfo.Config.ConfigValidator.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.bukkit.entity.EntityType.SHULKER;

public class VillagerHandler implements Listener {
    MiniMessage mM = MiniMessage.miniMessage();
    Map<Message, String> messages = ConfigValidator.getMapping();
    public static HashMap<UUID, Shulker> workstationShulker = new HashMap<>();
    public static HashMap<UUID, PersistentDataContainer> villagerPDC = new HashMap<>();
    NamespacedKey infoToggle = new NamespacedKey(VillagerInfo.plugin, "infoToggle");
    NamespacedKey isHighlighted = new NamespacedKey(VillagerInfo.plugin, "isHighlighted");
    String enabled = VillagerInfo.isEnabled;
    String disabled = VillagerInfo.isEnabled;

    @EventHandler
    public void onVillagerClick(PlayerInteractEntityEvent event) {

        FileConfiguration config = VillagerInfo.plugin.getConfig();
        Player player = event.getPlayer();
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
        String togglePDC = playerPDC.get(infoToggle, PersistentDataType.STRING);
        if (togglePDC!= null && togglePDC.equals(enabled)) {
            return;
        }
        if (!player.hasPermission("villagerinfo.use")) {
            return;
        }
        event.setCancelled(true);
        Location villagerPOI = villager.getMemory(MemoryKey.JOB_SITE);
        ArrayList<Component> messageList = new ArrayList<>();
        //profession
        if(config.getBoolean("profession")){
            messageList.add(villagerProfession(villager));
        }
        //job-site
        if(config.getBoolean("job-site")){
            messageList.add(villagerJobSite(villager));
        }
        //last-worked
        if(config.getBoolean("last-worked")){
            messageList.add(villagerLastWorked(villager));
        }
        //bed-location
        if(config.getBoolean("bed-location")){
            messageList.add(villagerBed(villager));
        }
        //last-slept
        if(config.getBoolean("last-slept")){
            messageList.add(villagerLastSlept(villager));
        }
        //inventory
        if(config.getBoolean("inventory")){
            messageList.add(villagerInventory(villager));
        }
        //restocks
        if(config.getBoolean("restocks")){
            messageList.add(villagerRestocks(villager));
        }
        //reputation
        if(config.getBoolean("reputation")){
            messageList.add(villagerPlayerReputation(villager, player));
        }
        if(config.getBoolean("highlight-workstation") && villagerPOI != null){
            villagerJobsiteHighlight(villager.getPersistentDataContainer(), villager.getUniqueId(), villagerPOI);
        }
        //Messages
        Component prefix = mM.deserialize(messages.get(Message.PREFIX));
        player.sendMessage(prefix);
        for (Component component : messageList){
            player.sendMessage(component);
        }
    }

    private Component villagerProfession(Villager villager){
        Component professionFinal;
        String villagerProfessionString = villager.getProfession().toString();
        if (villager.getProfession() == Villager.Profession.NONE){
            professionFinal = mM.deserialize(messages.get(Message.VILLAGER_PROFESSION), Placeholder.parsed("profession", messages.get(Message.NONE)));
        } else {
            professionFinal = mM.deserialize(messages.get(Message.VILLAGER_PROFESSION), Placeholder.parsed("profession", villagerProfessionString));
        }
        return professionFinal;
    }
    private Component villagerJobSite(Villager villager){
        Location villagerJobLocation = villager.getMemory(MemoryKey.JOB_SITE);
        Component jobSiteFinal;
        if(villagerJobLocation == null){
            jobSiteFinal = mM.deserialize(messages.get(Message.VILLAGER_JOBSITE),
                    Placeholder.unparsed("jobsitelocation", messages.get(Message.NONE)));
        }else{
            String jobX = String.valueOf(mM.deserialize(messages.get(Message.LOCATION_X),
                    Placeholder.unparsed("int", String.valueOf(villagerJobLocation.getBlockX()))));
            String jobY = String.valueOf(mM.deserialize(messages.get(Message.LOCATION_Y),
                    Placeholder.unparsed("int", String.valueOf(villagerJobLocation.getBlockY()))));
            String jobZ = String.valueOf(mM.deserialize(messages.get(Message.LOCATION_Z),
                    Placeholder.unparsed("int", String.valueOf(villagerJobLocation.getBlockZ()))));
            jobSiteFinal = mM.deserialize(messages.get(Message.VILLAGER_JOBSITE),
                    Placeholder.unparsed("jobsitelocation", jobX + jobY + jobZ));
        }
        return jobSiteFinal;
    }
    private Component villagerLastWorked(Villager villager){
        Component villagerLastWorkedFinal;
        Long lastWorked = villager.getMemory(MemoryKey.LAST_WORKED_AT_POI);
        if(lastWorked == null){
            villagerLastWorkedFinal = mM.deserialize(messages.get(Message.VILLAGER_LAST_WORKED),
                    Placeholder.unparsed("worktime", messages.get(Message.NEVER)));
        } else {
            String formattedTime = TimeFormatting.timeMath(villager.getWorld().getFullTime() - lastWorked);
            villagerLastWorkedFinal = mM.deserialize(messages.get(Message.VILLAGER_LAST_WORKED),
                    Placeholder.unparsed("worktime", formattedTime));
        }
        return villagerLastWorkedFinal;
    }
    private Component villagerBed(Villager villager){
        Component villagerBedFinal;
        Location bedLocation = villager.getMemory(MemoryKey.HOME);
        if(bedLocation == null){
            villagerBedFinal = mM.deserialize(messages.get(Message.VILLAGER_HOME),
                    Placeholder.unparsed("homelocation", messages.get(Message.NONE)));
        } else {
            Component bedX = mM.deserialize(messages.get(Message.LOCATION_X),
                    Placeholder.unparsed("int", String.valueOf(bedLocation.getBlockX())));
            Component bedY = mM.deserialize(messages.get(Message.LOCATION_Y),
                    Placeholder.unparsed("int", String.valueOf(bedLocation.getBlockY())));
            Component bedZ = mM.deserialize(messages.get(Message.LOCATION_Z),
                    Placeholder.unparsed("int", String.valueOf(bedLocation.getBlockZ())));
            villagerBedFinal = mM.deserialize(messages.get(Message.VILLAGER_HOME),
                    Placeholder.unparsed("homelocation", String.valueOf(bedX.append(bedY).append(bedZ))));
        }
        return villagerBedFinal;
    }
    private Component villagerLastSlept(Villager villager){
        Component villagerLastSleptFinal;
        Long lastSlept = villager.getMemory(MemoryKey.LAST_SLEPT);
        if (lastSlept == null){
            villagerLastSleptFinal = mM.deserialize(messages.get(Message.VILLAGER_SLEPT),
                    Placeholder.unparsed("sleeptime", messages.get(Message.NEVER)));
        } else {
            String formattedTime = TimeFormatting.timeMath(villager.getWorld().getFullTime() - lastSlept);
            villagerLastSleptFinal = mM.deserialize(messages.get(Message.VILLAGER_SLEPT),
                    Placeholder.unparsed("sleeptime", formattedTime));
        }
        return villagerLastSleptFinal;
    }
    private Component villagerInventory(Villager villager){
        Component villagerInventoryFinal;
        if(villager.getInventory().isEmpty()) {
            villagerInventoryFinal = mM.deserialize(messages.get(Message.VILLAGER_INVENTORY), Placeholder.unparsed("contents", messages.get(Message.EMPTY)));
        } else {
            String villagerInventory = null;
            ItemStack[] inventoryItems = villager.getInventory().getContents();
            for (ItemStack items : inventoryItems) {
                if(items == null) continue;
                villagerInventory = villagerInventory +
                        items.displayName() + "(" + items.getAmount() + ")\n";
            }
            villagerInventoryFinal = mM.deserialize(messages.get(Message.VILLAGER_INVENTORY), Placeholder.unparsed("contents", villagerInventory));
        }
        return villagerInventoryFinal;
    }
    private Component villagerRestocks(Villager villager){
        return mM.deserialize(messages.get(Message.VILLAGER_RESTOCKS), Placeholder.unparsed("restockcount", String.valueOf(villager.getRestocksToday())));
    }
    private Component villagerPlayerReputation(Villager villager, Player player){
        Component villagerReputationFinal;
        int reputationRawTotal = reputationTotal(villager, player.getUniqueId());
        String playerReputation = ReputationHandler.villagerReputation(reputationRawTotal);
        villagerReputationFinal = mM.deserialize(messages.get(Message.PLAYER_REPUTATION), Placeholder.unparsed("reputation", playerReputation));
        return villagerReputationFinal;

    }

    private void villagerJobsiteHighlight(PersistentDataContainer villPDC, UUID villUUID, Location villPOI){
        String getHighlightPDC = villPDC.get(isHighlighted, PersistentDataType.STRING);
        if (getHighlightPDC == null || getHighlightPDC.equals(disabled)){
                villPDC.set(isHighlighted,PersistentDataType.STRING, enabled);
                villagerPDC.put(villUUID,villPDC);
                spawnShulker(villUUID, villPOI);
                new BukkitRunnable(){
                @Override
                public void run(){
                    killShulker(villUUID);
                    villPDC.set(isHighlighted,PersistentDataType.STRING, disabled);
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
    private void spawnShulker(UUID uuid, Location location){
        Shulker spawnedShulker = (Shulker) location.getWorld().spawnEntity(location, SHULKER, CreatureSpawnEvent.SpawnReason.CUSTOM, (Entity) ->{
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
