package simplexity.villagerinfo.interaction.logic;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import simplexity.villagerinfo.VillagerInfo;
import simplexity.villagerinfo.commands.villagerinfo.subcommands.toggle.subcommands.OutputToggle;
import simplexity.villagerinfo.configurations.functionality.VillConfig;
import simplexity.villagerinfo.configurations.locale.MessageInsert;
import simplexity.villagerinfo.configurations.locale.VillagerMessage;
import simplexity.villagerinfo.util.Resolvers;

public class ComponentManager {

    private static final MiniMessage miniMessage = VillagerInfo.getInstance().getMiniMessage();

    public static void sendZombieVillagerInfo(Player player, ZombieVillager zombieVillager) {
        if (!PlayerToggle.isPdcToggleEnabled(player, OutputToggle.outputEnabledKey)) return;
        ZombieVillagerData zombieVillagerData = new ZombieVillagerData(zombieVillager);
    }

    public static void sendVillagerInfo(Player player, OfflinePlayer playerToParse, Villager villager) {
        if (!PlayerToggle.isPdcToggleEnabled(player, OutputToggle.outputEnabledKey)) return;
        VillagerData villagerData = new VillagerData(villager, playerToParse);
        Component componentToSend = villagerComponentBuilder(villagerData);
        player.sendMessage(componentToSend);
    }

    private static Component purpurLobotomized(VillagerData villagerData) {
        if (!VillagerInfo.getInstance().isUsingPurpur()) return null;
        if (!VillConfig.getInstance().displayPurpurLobotomized()) return null;
        return miniMessage.deserialize(
                VillagerMessage.PURPUR_LOBOTOMIZED.getMessage(),
                Resolvers.getInstance().booleanStateResolver(villagerData.isLobotomized()));
    }

    private static Component babyVillagerAge(VillagerData villagerData) {
        if (!VillConfig.getInstance().displayBabyVillagerAge()) return null;
        if (villagerData.getBabyVillagerAgeSeconds() == null) return null;
        return miniMessage.deserialize(
                VillagerMessage.BABY_VILLAGER_AGE.getMessage(),
                Resolvers.getInstance().timeFormatter((long) villagerData.getBabyVillagerAgeSeconds())
        );
    }

    private static Component villagerProfession(VillagerData villagerData) {
        if (!VillConfig.getInstance().displayProfession()) return null;
        if (villagerData.getProfession() == null) return miniMessage.deserialize(
                VillagerMessage.VILLAGER_PROFESSION.getMessage(),
                Placeholder.parsed("value", "none")
        );
        return miniMessage.deserialize(
                VillagerMessage.VILLAGER_PROFESSION.getMessage(),
                Placeholder.parsed("value", villagerData.getProfession().toString().toLowerCase())
        );
    }

    private static Component villagerHealth(VillagerData villagerData) {
        if (!VillConfig.getInstance().displayHealth()) return null;
        return miniMessage.deserialize(
                VillagerMessage.VILLAGER_HEALTH.getMessage(),
                Placeholder.parsed("value", villagerData.getVillagerCurrentHealth().toString()),
                Placeholder.parsed("value2", villagerData.getVillagerMaxHealth().toString())
        );
    }

    private static Component villagerJobSiteLocation(VillagerData villagerData) {
        if (!VillConfig.getInstance().displayJobSiteLocation()) return null;
        if (villagerData.getJobSiteLocation() == null) return null;
        return miniMessage.deserialize(
                VillagerMessage.VILLAGER_JOBSITE_LOCATION.getMessage(),
                Resolvers.getInstance().locationBuilder(villagerData.getJobSiteLocation())
        );
    }

    private static Component villagerLastWorked(VillagerData villagerData) {
        if (!VillConfig.getInstance().displayLastWorkTime()) return null;
        if (villagerData.getTimeSinceWorkedSeconds() == null) return null;
        return miniMessage.deserialize(
                VillagerMessage.VILLAGER_LAST_WORKED.getMessage(),
                Resolvers.getInstance().timeFormatter(villagerData.getTimeSinceWorkedSeconds())
        );
    }

    private static Component restocksToday(VillagerData villagerData) {
        if (!VillConfig.getInstance().displayRestocksToday()) return null;
        if (villagerData.getRestocksToday() == null) return null;
        return miniMessage.deserialize(
                VillagerMessage.VILLAGER_RESTOCKS_TODAY.getMessage(),
                Placeholder.parsed("value", villagerData.getRestocksToday().toString())
        );
    }

    private static Component bedLocation(VillagerData villagerData) {
        if (!VillConfig.getInstance().displayBedLocation()) return null;
        if (villagerData.getBedLocation() == null) return null;
        return miniMessage.deserialize(
                VillagerMessage.VILLAGER_BED_LOCATION.getMessage(),
                Resolvers.getInstance().locationBuilder(villagerData.getBedLocation())
        );
    }

    private static Component lastSlept(VillagerData villagerData) {
        if (!VillConfig.getInstance().displayLastSleepTime()) return null;
        if (villagerData.getTimeSinceSleptSeconds() == null) return null;
        return miniMessage.deserialize(
                VillagerMessage.VILLAGER_LAST_SLEPT.getMessage(),
                Resolvers.getInstance().timeFormatter(villagerData.getTimeSinceSleptSeconds())
        );
    }

    private static Component villagerInventory(VillagerData villagerData) {
        if (!VillConfig.getInstance().displayVillagerInventory()) return null;
        if (villagerData.getVillagerInventory() == null) return null;
        Component inventoryContents = inventoryItems(villagerData);
        return miniMessage.deserialize(
                VillagerMessage.VILLAGER_INVENTORY.getMessage(),
                Placeholder.component("contents", inventoryContents)
        );
    }

    private static Component inventoryItems(VillagerData villagerData) {
        Inventory inventory = villagerData.getVillagerInventory();
        ItemStack[] items = inventory.getContents();
        if (items.length == 0) return miniMessage.deserialize(MessageInsert.EMPTY_MESSAGE_FORMAT.getMessage());
        Component itemsComponent = Component.empty();
        for (ItemStack item : items) {
            if (item == null) continue;
            Component itemComponent = miniMessage.deserialize(
                    VillagerMessage.VILLAGER_INVENTORY_ITEM_FORMAT.getMessage(),
                    Placeholder.parsed("item", item.getType().toString().toLowerCase()),
                    Placeholder.parsed("value", String.valueOf(item.getAmount()))
            );
            itemsComponent = itemsComponent.appendNewline().append(itemComponent);
        }
        return itemsComponent;
    }

    private static Component reputationComponent(VillagerData villagerData){
        if (!VillConfig.getInstance().displayPlayerReputation()) return null;
        if (villagerData.getPlayerReputation() == null) return null;
        return miniMessage.deserialize(
                VillagerMessage.PLAYER_REPUTATION_MESSAGE.getMessage(),
                Resolvers.getInstance().playerReputationResolver(villagerData.getPlayerReputation())
        );
    }


    private static Component villagerComponentBuilder(VillagerData villagerData) {
        Component outputComponent = Component.empty();
        Component purpurLobotomizedComponent = purpurLobotomized(villagerData);
        Component babyVillagerAgeComponent = babyVillagerAge(villagerData);
        Component professionComponent = villagerProfession(villagerData);
        Component villagerHealthComponent = villagerHealth(villagerData);
        Component jobSiteComponent = villagerJobSiteLocation(villagerData);
        Component lastWorkedComponent = villagerLastWorked(villagerData);
        Component restocksTodayComponent = restocksToday(villagerData);
        Component bedLocationComponent = bedLocation(villagerData);
        Component lastSleptComponent = lastSlept(villagerData);
        Component inventoryComponent = villagerInventory(villagerData);
        Component reputationComponent = reputationComponent(villagerData);
        if (purpurLobotomizedComponent != null) outputComponent = outputComponent.append(purpurLobotomizedComponent);
        if (babyVillagerAgeComponent != null) outputComponent = outputComponent.appendNewline().append(babyVillagerAgeComponent);
        if (professionComponent != null) outputComponent = outputComponent.appendNewline().append(professionComponent);
        if (villagerHealthComponent != null) outputComponent = outputComponent.appendNewline().append(villagerHealthComponent);
        if (jobSiteComponent != null) outputComponent = outputComponent.appendNewline().append(jobSiteComponent);
        if (lastWorkedComponent != null) outputComponent = outputComponent.appendNewline().append(lastWorkedComponent);
        if (restocksTodayComponent != null) outputComponent = outputComponent.appendNewline().append(restocksTodayComponent);
        if (bedLocationComponent != null) outputComponent = outputComponent.appendNewline().append(bedLocationComponent);
        if (lastSleptComponent != null) outputComponent = outputComponent.appendNewline().append(lastSleptComponent);
        if (inventoryComponent != null) outputComponent = outputComponent.appendNewline().append(inventoryComponent);
        if (reputationComponent != null) outputComponent = outputComponent.appendNewline().append(reputationComponent);
        return outputComponent;
    }


}
