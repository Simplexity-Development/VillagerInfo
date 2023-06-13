package simplexity.villagerinfo.commands.util;

import java.util.HashMap;

public class SubCommandMaps {
    private static SubCommandMaps instance;

    private SubCommandMaps() {
    }

    public static SubCommandMaps getInstance() {
        if (instance == null) instance = new SubCommandMaps();
        return instance;
    }

    private final HashMap<String, SubCommand> ToggleSubCommands = new HashMap<>();
    private final HashMap<String, SubCommand> VillagerInfoSubCommands = new HashMap<>();

    public HashMap<String, SubCommand> getToggleSubCommands() {
        return ToggleSubCommands;
    }

    public HashMap<String, SubCommand> getVillagerInfoSubCommands() {
        return VillagerInfoSubCommands;
    }
}
