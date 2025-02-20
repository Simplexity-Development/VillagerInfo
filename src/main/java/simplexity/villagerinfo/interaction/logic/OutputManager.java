package simplexity.villagerinfo.interaction.logic;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import simplexity.villagerinfo.VillagerInfo;
import simplexity.villagerinfo.configurations.locale.VillagerMessage;

public class OutputManager {

    private static final MiniMessage miniMessage = VillagerInfo.getInstance().getMiniMessage();
    
    public static void zombieVillagerOutput(Player player, ZombieVillager zombieVillager) {
        ZombieVillagerData zombieVillagerData = new ZombieVillagerData(zombieVillager);
    }

    public static void VillagerOutput(Player player, OfflinePlayer playerToProcess, Villager villager) {
        VillagerData villagerData = new VillagerData(villager, playerToProcess);
        DisplayManager.handleBedHighlight(villager);
        DisplayManager.handleWorkstationHighlight(villager);
        Component message = playerComponentBuilder(villagerData);
        player.sendMessage(message);

    }

    private static Component playerComponentBuilder(VillagerData villagerData) {
        String lobotomizedString;
        if (villagerData.isLobotomized() == null) lobotomizedString = "false";
        else lobotomizedString = villagerData.isLobotomized().toString();
        Component lobotomized = miniMessage.deserialize(VillagerMessage.PURPUR_LOBOTOMIZED.getMessage(), Placeholder.parsed("state", lobotomizedString));

        String babyAge;
        if ( villagerData.getBabyVillagerAgeSeconds() == null) babyAge = "adult";
        else babyAge = String.valueOf(villagerData.getBabyVillagerAgeSeconds());
        Component age = miniMessage.deserialize(VillagerMessage.BABY_VILLAGER_AGE.getMessage(), Placeholder.parsed("time", babyAge));


        String professionString = "";
        if (villagerData.getProfession() == null) professionString = "none";
        else professionString = villagerData.getProfession().toString();
        Component profession = miniMessage.deserialize(VillagerMessage.VILLAGER_PROFESSION.getMessage(), Placeholder.parsed("value", villagerData.getProfession().toString()));

        Component testingComponent = Component.empty();
        testingComponent = testingComponent.append(lobotomized).appendNewline().append(age).appendNewline().append(profession);
        return testingComponent;
    }
}
