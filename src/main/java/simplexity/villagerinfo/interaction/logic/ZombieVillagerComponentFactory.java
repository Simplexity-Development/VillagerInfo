package simplexity.villagerinfo.interaction.logic;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import simplexity.villagerinfo.VillagerInfo;
import simplexity.villagerinfo.configurations.functionality.VillConfig;
import simplexity.villagerinfo.configurations.locale.ServerMessage;
import simplexity.villagerinfo.configurations.locale.VillagerMessage;
import simplexity.villagerinfo.util.Resolvers;

public class ZombieVillagerComponentFactory {

    private static final MiniMessage miniMessage = VillagerInfo.getInstance().getMiniMessage();

    public static Component zombieVillagerComponentBuilder(ZombieVillagerData zombieVillagerData) {
        Component outputComponent = Component.empty();
        Component conversionTimeComponent = conversionTime(zombieVillagerData);
        Component healthComponent = health(zombieVillagerData);
        Component professionComponent = profession(zombieVillagerData);
        if (conversionTimeComponent != null) outputComponent = outputComponent.append(conversionTimeComponent);
        if (healthComponent != null) outputComponent = outputComponent.append(healthComponent);
        if (professionComponent != null) outputComponent = outputComponent.append(professionComponent);
        if (outputComponent.equals(Component.empty())) outputComponent = miniMessage.deserialize(VillagerMessage.NO_INFORMATION_TO_DISPLAY.getMessage());
        return outputWithPrefix(outputComponent);
    }

    private static Component conversionTime(ZombieVillagerData zombieVillagerData) {
        if (!VillConfig.getInstance().displayZombieVillagerConversionTime()) return null;
        if (zombieVillagerData.isConverting() == null) return null;
        if (!zombieVillagerData.isConverting()) {
            return miniMessage.deserialize(VillagerMessage.ZOMBIE_VILLAGER_NOT_CURRENTLY_CONVERTING.getMessage());
        }
        return miniMessage.deserialize(
                VillagerMessage.ZOMBIE_VILLAGER_CONVERSION_TIME.getMessage(),
                Resolvers.getInstance().timeFormatter((long) zombieVillagerData.getConversionTimeLeftSeconds()));
    }

    private static Component health(ZombieVillagerData zombieVillagerData) {
        if (!VillConfig.getInstance().displayHealth()) return null;
        return miniMessage.deserialize(
                VillagerMessage.VILLAGER_HEALTH.getMessage(),
                Placeholder.parsed("value", zombieVillagerData.getCurrentHealth().toString()),
                Placeholder.parsed("value2", zombieVillagerData.getMaxHealth().toString())
        );
    }

    private static Component profession(ZombieVillagerData zombieVillagerData) {
        if (!VillConfig.getInstance().displayProfession()) return null;
        return miniMessage.deserialize(
                VillagerMessage.VILLAGER_PROFESSION.getMessage(),
                Placeholder.parsed("value", zombieVillagerData.getProfession().toString().toLowerCase())
        );
    }

    private static Component outputWithPrefix(Component component) {
        Component prefixComponent = miniMessage.deserialize(ServerMessage.PLUGIN_PREFIX.getMessage());
        prefixComponent = prefixComponent.append(component);
        return prefixComponent;
    }
}
