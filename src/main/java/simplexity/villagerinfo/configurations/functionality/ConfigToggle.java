package simplexity.villagerinfo.configurations.functionality;

/**
 * Configuration toggles
 */

public enum ConfigToggle {
    OUTPUT_ENABLED(true),
    DISPLAY_PURPUR_LOBOTOMIZED(false),
    DISPLAY_BABY_VILLAGER_AGE(true),
    DISPLAY_ZOMBIE_VILLAGER_CONVERSION_TIME(true),
    DISPLAY_HEALTH(true),
    DISPLAY_PROFESSION(true),
    DISPLAY_JOB_SITE_LOCATION(true),
    DISPLAY_LAST_WORK_TIME(true),
    DISPLAY_BED_LOCATION(true),
    DISPLAY_LAST_SLEEP_TIME(true),
    DISPLAY_VILLAGER_INVENTORY(true),
    DISPLAY_RESTOCKS_TODAY(true),
    DISPLAY_PLAYER_REPUTATION(true),
    HIGHLIGHT_VILLAGER_WORKSTATION_ON_OUTPUT(true),
    PLAY_SOUND_ON_INFO_DISPLAY(true);
    boolean ConfigToggle;

    ConfigToggle(boolean setting) {
        this.ConfigToggle = setting;
    }

    /**
     * gets whether this specific toggle is enabled or not
     *
     * @return boolean
     */
    public boolean isEnabled() {
        return this.ConfigToggle;
    }

    /**
     * Sets this toggle to be enabled or disabled
     *
     * @param setting boolean
     */
    public void setEnabled(boolean setting) {
        this.ConfigToggle = setting;
    }
}
