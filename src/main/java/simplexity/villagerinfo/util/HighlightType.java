package simplexity.villagerinfo.util;

/**
 * Distinguishes which kind of villager highlight a tracked/tagged {@link org.bukkit.entity.BlockDisplay}
 * represents, since a single villager can have both a workstation highlight and a bed highlight active
 * at once, backed by separate tracking maps in {@code DisplayManager}.
 */
public enum HighlightType {
    WORKSTATION,
    BED
}
