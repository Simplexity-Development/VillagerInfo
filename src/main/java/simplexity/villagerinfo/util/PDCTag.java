package simplexity.villagerinfo.util;

import org.bukkit.NamespacedKey;

public enum PDCTag {
    VILLAGER_WORKSTATION_CURRENTLY_HIGHLIGHTED(new NamespacedKey("vill-info", "workstation-highlighted")),
    PLAYER_TOGGLE_OUTPUT_ENABLED(new NamespacedKey("vill-info", "vill-info-enabled")),
    PLAYER_TOGGLE_HIGHLIGHT_ENABLED(new NamespacedKey("vill-info", "vill-highlight-enabled")),
    PLAYER_TOGGLE_SOUND_ENABLED(new NamespacedKey("vill-info", "vill-output-sound-enabled")),
    BLOCK_DISPLAY_IS_FROM_VILLAGER_INFO(new NamespacedKey("vill-info", "vill-info-block-display"));
    final NamespacedKey pdcTag;

    PDCTag(NamespacedKey pdcTag) {
        this.pdcTag = pdcTag;
    }

    public NamespacedKey getPdcTag() {
        return pdcTag;
    }
}
