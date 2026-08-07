package simplexity.villagerinfo.interaction.logic;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import simplexity.villagerinfo.configurations.functionality.VillConfig;
import simplexity.villagerinfo.util.HighlightType;

import java.util.UUID;

public class DisplayFactory {

    /**
     * PDC key holding the string form of the villager's UUID a highlight entity was spawned for. Used by
     * {@code OrphanedHighlightListener} to identify a stale highlight entity purely from world data (e.g.
     * after a server restart wipes the in-memory tracking maps in {@code DisplayManager}).
     */
    public static final NamespacedKey HIGHLIGHT_VILLAGER_KEY = new NamespacedKey("vill-info", "highlight-villager");

    /**
     * PDC key holding the name of the {@link HighlightType} (workstation or bed) a highlight entity represents.
     */
    public static final NamespacedKey HIGHLIGHT_TYPE_KEY = new NamespacedKey("vill-info", "highlight-type");

    /**
     * Summons the block display
     * Takes in color to highlight and block to highlight
     *
     * @param highlightColor Color to highlight the block
     * @param highlightBlock Block to highlight
     * @param villagerUuid   UUID of the villager this highlight belongs to, tagged onto the entity for later orphan cleanup
     */
    public static BlockDisplay summonBlockDisplayEntity(Color highlightColor, Block highlightBlock, UUID villagerUuid) {
        BlockDisplay blockDisplay = (BlockDisplay) highlightBlock.getLocation().getWorld().spawnEntity(highlightBlock.getLocation(), EntityType.BLOCK_DISPLAY);
        modifyDisplayEntity(blockDisplay, highlightColor, highlightBlock);
        tagHighlightEntity(blockDisplay, villagerUuid, HighlightType.WORKSTATION);
        return blockDisplay;
    }

    /**
     * Summons a display entity to highlight the provided bed block
     *
     * @param highlightColor color to highlight the block with
     * @param bedBlock       bed block to highlight
     * @param villagerUuid   UUID of the villager this highlight belongs to, tagged onto the entity for later orphan cleanup
     * @return Block Display that is produced
     */

    public static BlockDisplay summonBedDisplayEntity(Color highlightColor, Block bedBlock, UUID villagerUuid) {
        if (!(bedBlock.getBlockData() instanceof Bed bedData)) {
            return null;
        }
        BlockFace facing = bedData.getFacing();
        Location bedLocation = bedBlock.getLocation();
        BlockDisplay blockDisplay = (BlockDisplay) bedLocation.getWorld().spawnEntity(bedLocation, EntityType.BLOCK_DISPLAY);
        rotateDisplay(facing, blockDisplay);
        modifyDisplayEntity(blockDisplay, highlightColor, bedBlock);
        tagHighlightEntity(blockDisplay, villagerUuid, HighlightType.BED);
        return blockDisplay;
    }

    /**
     * Tags a spawned highlight entity with the villager UUID and {@link HighlightType} it represents, so it
     * can be identified as one of ours (and matched back to its tracking map entry, or recognized as an
     * orphan) purely from world/entity data, independent of the in-memory tracking maps in
     * {@code DisplayManager}.
     *
     * @param blockDisplay the spawned highlight entity to tag
     * @param villagerUuid UUID of the villager this highlight belongs to
     * @param highlightType whether this is a workstation or bed highlight
     */
    private static void tagHighlightEntity(BlockDisplay blockDisplay, UUID villagerUuid, HighlightType highlightType) {
        PersistentDataContainer pdc = blockDisplay.getPersistentDataContainer();
        pdc.set(HIGHLIGHT_VILLAGER_KEY, PersistentDataType.STRING, villagerUuid.toString());
        pdc.set(HIGHLIGHT_TYPE_KEY, PersistentDataType.STRING, highlightType.name());
    }

    /**
     * Modifies the summoned entity to fit the specifications of the default config
     *
     * @param blockDisplay BlockDisplay that was summoned
     * @param glowColor    Color that this display will be highlighted as
     * @param block        Block that this display is basing its information off of
     */
    private static void modifyDisplayEntity(BlockDisplay blockDisplay, Color glowColor, Block block) {
        blockDisplay.setBlock(block.getBlockData());
        blockDisplay.getTransformation().getScale().add(
                VillConfig.getInstance().getXOffset(),
                VillConfig.getInstance().getYOffset(),
                VillConfig.getInstance().getZOffset());
        blockDisplay.setBrightness(new Display.Brightness(
                VillConfig.getInstance().getBlockLight(),
                VillConfig.getInstance().getSkyLight()));
        blockDisplay.setGlowing(true);
        blockDisplay.setGlowColorOverride(glowColor);
    }

    /**
     * ROTATES THE DISPLAY BECAUSE FOR SOME
     * REASON THE STUPID BED BLOCK DOES NOT HOLD THE INFORMATION ON WHICH
     * <p>
     * WAY
     * <p>
     * IT IS FACING WHAT ON EARTH
     *
     * @param facing       BlockFace of the bed block
     * @param blockDisplay Block display to rotate
     */
    private static void rotateDisplay(BlockFace facing, BlockDisplay blockDisplay) {
        Transformation transformation = null;
        switch (facing) {
            case SOUTH -> {
                return;
            }
            case WEST -> {
                float radianEastRotation = (float) java.lang.Math.toRadians(-90);
                transformation = bedDisplayTransformation(1, 0, 0, radianEastRotation);
            }
            case NORTH -> {
                float radianSouthRotation = (float) java.lang.Math.toRadians(180);
                transformation = bedDisplayTransformation(1, 0, 1, radianSouthRotation);
            }
            case EAST -> {
                float radianWestRotation = (float) java.lang.Math.toRadians(90);
                transformation = bedDisplayTransformation(0, 0, 1, radianWestRotation);
            }
        }
        if (transformation == null) return;
        blockDisplay.setTransformation(transformation);
    }

    /**
     * The math for the transformation, it works and I'm not touching it.
     *
     * @param headX         Number of blocks on the x-axis the bed needs to move
     * @param headY         Number of blocks on the y-axis the bed needs to move
     * @param headZ         Number of blocks on the z-axis the bed needs to move
     * @param rotationAngle The rotation angle IN RADIANS!!!!!!!!!! that the bed needs to rotate. Pro tip, use java.lang.Math.toRadians(number)
     * @return Transformation magic to adjust the bed
     */

    public static Transformation bedDisplayTransformation(int headX, int headY, int headZ, float rotationAngle) {
        Vector3f translationToOrigin = new Vector3f().add(headX, headY, headZ);
        Quaternionf rotation = new Quaternionf().rotateY(rotationAngle);
        Quaternionf noRotation = new Quaternionf().rotateY(0);
        Vector3f scale = new Vector3f(1, 1, 1);
        return new Transformation(translationToOrigin, rotation, scale, noRotation);
    }


}
