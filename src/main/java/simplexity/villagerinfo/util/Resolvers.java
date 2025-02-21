package simplexity.villagerinfo.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Location;
import simplexity.villagerinfo.VillagerInfo;
import simplexity.villagerinfo.configurations.locale.MessageInsert;
import simplexity.villagerinfo.configurations.locale.ServerMessage;

public class Resolvers {
    MiniMessage miniMessage = VillagerInfo.getInstance().getMiniMessage();
    private static Resolvers instance;

    private Resolvers() {
    }

    public static Resolvers getInstance() {
        if (instance == null) instance = new Resolvers();
        return instance;
    }

    public Component prefixResolver(String message) {
        return miniMessage.deserialize(message, Placeholder.parsed("plugin_prefix", ServerMessage.PLUGIN_PREFIX.getMessage()));
    }

    public TagResolver locationBuilder(Location location) {
        Component locationComponent;
        if (location == null) {
            locationComponent = miniMessage.deserialize(MessageInsert.NONE_MESSAGE_FORMAT.getMessage());
        } else {
            String x = String.valueOf(location.getBlockX());
            String y = String.valueOf(location.getBlockY());
            String z = String.valueOf(location.getBlockZ());
            Component compX = miniMessage.deserialize(MessageInsert.LOCATION_X_FORMAT.getMessage(), Placeholder.parsed("value", x));
            Component compY = miniMessage.deserialize(MessageInsert.LOCATION_Y_FORMAT.getMessage(), Placeholder.parsed("value", y));
            Component compZ = miniMessage.deserialize(MessageInsert.LOCATION_Z_FORMAT.getMessage(), Placeholder.parsed("value", z));
            locationComponent = compX.append(compY).append(compZ);
        }
        return TagResolver.resolver(Placeholder.component("location", locationComponent));
    }

    public TagResolver playerReputationResolver(int repNum) {
        Component reputationComponent = Component.empty();
        int minVal = -14;
        int maxVal = 15;
        int devNum = repNum / 50;
        boolean isPos = Math.abs(devNum) == devNum;
        for (int i = minVal; i <= maxVal; i++) {
            if (i == 0) {
                reputationComponent = reputationComponent.append(miniMessage.deserialize(MessageInsert.REPUTATION_TOTAL_FORMAT.getMessage(), Placeholder.parsed("value", String.valueOf(repNum))));
                continue;
            }
            if (Math.abs(i) != i) {
                if (!isPos && i < 0 && i >= devNum) {
                    reputationComponent = reputationComponent.append(miniMessage.deserialize(MessageInsert.NEGATIVE_REPUTATION_BAR_FORMAT.getMessage()));
                    continue;
                }
                reputationComponent = reputationComponent.append(miniMessage.deserialize(MessageInsert.NEUTRAL_REPUTATION_BAR_FORMAT.getMessage()));
            }
            if (Math.abs(i) == i) {
                if (isPos && i > 0 && i <= devNum) {
                    reputationComponent = reputationComponent.append(miniMessage.deserialize(MessageInsert.POSITIVE_REPUTATION_BAR_FORMAT.getMessage()));
                    continue;
                }
                reputationComponent = reputationComponent.append(miniMessage.deserialize(MessageInsert.NEUTRAL_REPUTATION_BAR_FORMAT.getMessage()));
            }
        }
        return TagResolver.resolver(Placeholder.component("reputation_bar", reputationComponent));
    }


    public TagResolver timeFormatterPast(Long timeDifferenceInSeconds) {
        Component finalTimeComponent;
        if (timeDifferenceInSeconds == null) {
            finalTimeComponent = miniMessage.deserialize(MessageInsert.NEVER_MESSAGE_FORMAT.getMessage());
        } else {
            long s = timeDifferenceInSeconds % 60;
            long m = (timeDifferenceInSeconds / 60) % 60;
            long h = (timeDifferenceInSeconds / (60 * 60)) % 24;
            finalTimeComponent = Component.empty();
            boolean componentEmpty = true;
            if (h > 0) {
                componentEmpty = false;
                String hours = String.valueOf(h);
                finalTimeComponent = finalTimeComponent.append(miniMessage.deserialize(MessageInsert.HOUR_MESSAGE_FORMAT.getMessage(), Placeholder.parsed("value", hours)));
            }
            if (m > 0) {
                componentEmpty = false;
                String minutes = String.valueOf(m);
                finalTimeComponent = finalTimeComponent.append(miniMessage.deserialize(MessageInsert.MINUTE_MESSAGE_FORMAT.getMessage(), Placeholder.parsed("value", minutes)));
            }
            if (s > 0) {
                componentEmpty = false;
                String seconds = String.valueOf(s);
                finalTimeComponent = finalTimeComponent.append(miniMessage.deserialize(MessageInsert.SECOND_MESSAGE_FORMAT.getMessage(), Placeholder.parsed("value", seconds)));
            }
            if (componentEmpty) {
                finalTimeComponent = finalTimeComponent.append(miniMessage.deserialize(MessageInsert.JUST_NOW_FORMAT.getMessage()));
            } else {
                finalTimeComponent = finalTimeComponent.append(miniMessage.deserialize(MessageInsert.AGO_MESSAGE_FORMAT.getMessage()));
            }
        }
        return TagResolver.resolver(Placeholder.component("time", finalTimeComponent));
    }

    public TagResolver timeFormatter(Long timeDifferenceInSeconds) {
        Component finalTimeComponent;
        if (timeDifferenceInSeconds == null) {
            finalTimeComponent = miniMessage.deserialize(MessageInsert.NEVER_MESSAGE_FORMAT.getMessage());
        } else {
            long s = timeDifferenceInSeconds % 60;
            long m = (timeDifferenceInSeconds / 60) % 60;
            long h = (timeDifferenceInSeconds / (60 * 60)) % 24;
            finalTimeComponent = Component.empty();
            boolean componentEmpty = true;
            if (h > 0) {
                componentEmpty = false;
                String hours = String.valueOf(h);
                finalTimeComponent = finalTimeComponent.append(miniMessage.deserialize(MessageInsert.HOUR_MESSAGE_FORMAT.getMessage(), Placeholder.parsed("value", hours)));
            }
            if (m > 0) {
                componentEmpty = false;
                String minutes = String.valueOf(m);
                finalTimeComponent = finalTimeComponent.append(miniMessage.deserialize(MessageInsert.MINUTE_MESSAGE_FORMAT.getMessage(), Placeholder.parsed("value", minutes)));
            }
            if (s > 0) {
                componentEmpty = false;
                String seconds = String.valueOf(s);
                finalTimeComponent = finalTimeComponent.append(miniMessage.deserialize(MessageInsert.SECOND_MESSAGE_FORMAT.getMessage(), Placeholder.parsed("value", seconds)));
            }
            if (componentEmpty) {
                finalTimeComponent = finalTimeComponent.append(miniMessage.deserialize(MessageInsert.JUST_NOW_FORMAT.getMessage()));
            }
        }
        return TagResolver.resolver(Placeholder.component("time", finalTimeComponent));
    }

    public TagResolver booleanStateResolver(boolean state){
        if (state) {
            return TagResolver.resolver(Placeholder.parsed("state", MessageInsert.TRUE_MESSAGE_FORMAT.getMessage()));
        } else {
            return TagResolver.resolver(Placeholder.parsed("state", MessageInsert.FALSE_MESSAGE_FORMAT.getMessage()));
        }
    }

    public TagResolver enabledStateResolver(boolean state){
        if (state) {
            return TagResolver.resolver(Placeholder.parsed("state", MessageInsert.ENABLED_MESSAGE_FORMAT.getMessage()));
        } else {
            return TagResolver.resolver(Placeholder.parsed("state", MessageInsert.DISABLED_MESSAGE_FORMAT.getMessage()));
        }
    }

}
