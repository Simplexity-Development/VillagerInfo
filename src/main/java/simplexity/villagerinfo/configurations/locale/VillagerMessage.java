package simplexity.villagerinfo.configurations.locale;

/**
 * The messages used in the villager information output.
 */
public enum VillagerMessage {
    PURPUR_LOBOTOMIZED("<#05bff7><hover:show_text:'<aqua>Lobotomized: <grey><state>'>[<#c4fff7>Lobotomized</#c4fff7>]"),
    ZOMBIE_VILLAGER_CONVERSION_TIME("<#05bff7><hover:show_text:'<aqua>Conversion Time: <grey><time>'>[<#c4fff7>Time Until Converted</#c4fff7>]</hover>"),
    ZOMBIE_VILLAGER_NOT_CURRENTLY_CONVERTING("<#05bff7><hover:show_text:'<grey>Zombie Villager is Not Currently Converting'>[<#c4fff7>Time Until Converted</#c4fff7>]</hover>"),
    BABY_VILLAGER_AGE("<#05bff7><hover:show_text:'<aqua>Childhood Left: <grey><time>'>[<#c4fff7>Time Until Adult</#c4fff7>]"),
    VILLAGER_PROFESSION("<#05bff7><hover:show_text:'<aqua>Profession: <grey><lang:entity.minecraft.villager.<value>>'>[<#c4fff7>Profession</#c4fff7>]"),
    VILLAGER_HEALTH("<#05bff7><hover:show_text:'<aqua>Health: <grey><value><aqua>/</aqua><value2>'>[<#c4fff7>Health</#c4fff7>]"),
    VILLAGER_JOBSITE_LOCATION("<#05bff7><hover:show_text:'<aqua>POI: <grey><location>'>[<#c4fff7>Job Site</#c4fff7>]"),
    VILLAGER_LAST_WORKED("<#05bff7><hover:show_text:'<aqua>Last Worked: <grey><time>'>[<#c4fff7>Last Worked At Workstation</#c4fff7>]"),
    VILLAGER_RESTOCKS_TODAY("<#05bff7><hover:show_text:'<aqua>Restocks: <grey><value>'>[<#c4fff7>Restocks Today</#c4fff7>]"),
    VILLAGER_BED_LOCATION("<#05bff7><hover:show_text:'<aqua>Bed: <grey><location>'>[<#c4fff7>Home</#c4fff7>]"),
    VILLAGER_LAST_SLEPT("<#05bff7><hover:show_text:'<aqua>Last Slept: <grey><time>'>[<#c4fff7>Last Slept</#c4fff7>]"),
    VILLAGER_INVENTORY("<#05bff7><hover:show_text:'<aqua>Inventory: <grey><contents>'>[<#c4fff7>Villager Inventory</#c4fff7>]"),
    VILLAGER_INVENTORY_ITEM_FORMAT("\n • <lang:item.minecraft.<item>> (<value>)"),
    PLAYER_REPUTATION_MESSAGE("<#05bff7><hover:show_text:'<#05bff7>[<#c4fff7>Player Reputation</#c4fff7>]'><value>"),
    NO_INFORMATION_TO_DISPLAY("<grey>No information to display on this villager");
    String message;

    VillagerMessage(String message) {
        this.message = message;
    }

    /**
     * gets the string associated with this message
     *
     * @return String
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * sets message to the provided string
     *
     * @param message String
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
