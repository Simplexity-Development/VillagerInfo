package adhdmc.villagerinfo.Config;

import adhdmc.villagerinfo.VillagerInfo;
import org.bukkit.configuration.file.FileConfiguration;

public enum ToggleSetting {
    BABY_AGE(true),
    ZOMBIE_CONVERSION(true),
    PROFESSION(true),
    JOB_SITE(true),
    LAST_WORKED(true),
    BED_LOCATION(true),
    LAST_SLEPT(true),
    INVENTORY(true),
    RESTOCKS(true),
    REPUTATION(true),
    HIGHLIGHT_WORKSTATION(true),
    SOUND_TOGGLE(true);
    boolean ToggleSetting;
    ToggleSetting(boolean setting) {
        this.ToggleSetting = setting;
    }
    public boolean isEnabled() { return this.ToggleSetting; }
    private void setEnabled(boolean setting) { this.ToggleSetting = setting; }

    public static void reloadToggles() {
        FileConfiguration config = VillagerInfo.getInstance().getConfig();
        BABY_AGE.setEnabled(config.getBoolean("baby-age", true));
        ZOMBIE_CONVERSION.setEnabled(config.getBoolean("zombie-conversion", true));
        PROFESSION.setEnabled(config.getBoolean("profession", true));
        JOB_SITE.setEnabled(config.getBoolean("job-site", true));
        LAST_WORKED.setEnabled(config.getBoolean("last-worked", true));
        BED_LOCATION.setEnabled(config.getBoolean("bed-location", true));
        LAST_SLEPT.setEnabled(config.getBoolean("last-slept", true));
        INVENTORY.setEnabled(config.getBoolean("inventory", true));
        RESTOCKS.setEnabled(config.getBoolean("restocks", true));
        REPUTATION.setEnabled(config.getBoolean("reputation", true));
        HIGHLIGHT_WORKSTATION.setEnabled(config.getBoolean("highlight-workstation", true));
        SOUND_TOGGLE.setEnabled(config.getBoolean("sound-toggle", true));
    }
}
