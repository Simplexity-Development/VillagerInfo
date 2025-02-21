package simplexity.villagerinfo.interaction.logic;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import simplexity.villagerinfo.VillagerInfo;
import simplexity.villagerinfo.configurations.functionality.VillConfig;
import simplexity.villagerinfo.configurations.locale.MessageInsert;
import simplexity.villagerinfo.configurations.locale.ServerMessage;
import simplexity.villagerinfo.configurations.locale.VillagerMessage;
import simplexity.villagerinfo.util.Resolvers;

public class VillagerComponentFactory {

    private static final MiniMessage miniMessage = VillagerInfo.getInstance().getMiniMessage();

    public static Component villagerComponentBuilder(VillagerData villagerData) {
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
        boolean isAdult = false;
        if (purpurLobotomizedComponent != null) outputComponent = outputComponent.append(purpurLobotomizedComponent);
        if (villagerHealthComponent != null)
            outputComponent = outputComponent.append(villagerHealthComponent);
        if (babyVillagerAgeComponent != null) {
            outputComponent = outputComponent.append(babyVillagerAgeComponent);
        } else {
            isAdult = true;
        }
        if (professionComponent != null && isAdult)
            outputComponent = outputComponent.append(professionComponent);
        if (jobSiteComponent != null && isAdult)
            outputComponent = outputComponent.append(jobSiteComponent);
        if (lastWorkedComponent != null && isAdult)
            outputComponent = outputComponent.append(lastWorkedComponent);
        if (restocksTodayComponent != null && isAdult)
            outputComponent = outputComponent.append(restocksTodayComponent);
        if (bedLocationComponent != null)
            outputComponent = outputComponent.append(bedLocationComponent);
        if (lastSleptComponent != null) outputComponent = outputComponent.append(lastSleptComponent);
        if (inventoryComponent != null) outputComponent = outputComponent.append(inventoryComponent);
        if (reputationComponent != null) outputComponent = outputComponent.append(reputationComponent);
        if (outputComponent.equals(Component.empty())) outputComponent = miniMessage.deserialize(VillagerMessage.NO_INFORMATION_TO_DISPLAY.getMessage());
        return outputWithPrefix(outputComponent);
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
        long age = villagerData.getBabyVillagerAgeSeconds() * -1L;
        return miniMessage.deserialize(
                VillagerMessage.BABY_VILLAGER_AGE.getMessage(),
                Resolvers.getInstance().timeFormatter(age)
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
                Resolvers.getInstance().timeFormatterPast(villagerData.getTimeSinceWorkedSeconds())
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
                Resolvers.getInstance().timeFormatterPast(villagerData.getTimeSinceSleptSeconds())
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
        Component itemsComponent = Component.empty();
        for (ItemStack item : items) {
            if (item == null) continue;
            Component itemComponent = miniMessage.deserialize(
                    VillagerMessage.VILLAGER_INVENTORY_ITEM_FORMAT.getMessage(),
                    Placeholder.parsed("item", item.getType().toString().toLowerCase()),
                    Placeholder.parsed("value", String.valueOf(item.getAmount()))
            );
            itemsComponent = itemsComponent.append(itemComponent);
        }
        if (itemsComponent.equals(Component.empty()))
            return miniMessage.deserialize(MessageInsert.EMPTY_MESSAGE_FORMAT.getMessage());
        return itemsComponent;
    }

    private static Component reputationComponent(VillagerData villagerData) {
        if (!VillConfig.getInstance().displayPlayerReputation()) return null;
        if (villagerData.getPlayerReputation() == null) return null;
        return miniMessage.deserialize(
                VillagerMessage.PLAYER_REPUTATION_MESSAGE.getMessage(),
                Placeholder.parsed("reputation_number", String.valueOf(villagerData.getPlayerReputation())),
                Resolvers.getInstance().playerReputationBarResolver(villagerData.getPlayerReputation())
        );
    }

    private static Component outputWithPrefix(Component component) {
        Component prefixComponent = miniMessage.deserialize(ServerMessage.PLUGIN_PREFIX.getMessage());
        prefixComponent = prefixComponent.append(component);
        return prefixComponent;
    }


}
