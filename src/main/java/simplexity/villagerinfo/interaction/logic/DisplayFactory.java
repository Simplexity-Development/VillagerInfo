package simplexity.villagerinfo.interaction.logic;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import simplexity.villagerinfo.configurations.functionality.VillConfig;

public class DisplayFactory {

    /**
     * Summons the block display
     * Takes in color to highlight and block to highlight
     *
     * @param highlightColor Color to highlight the block
     * @param highlightBlock Block to highlight
     */
    public static BlockDisplay summonBlockDisplayEntity(Color highlightColor, Block highlightBlock) {
        BlockDisplay blockDisplay = (BlockDisplay) highlightBlock.getLocation().getWorld().spawnEntity(highlightBlock.getLocation(), EntityType.BLOCK_DISPLAY);
        modifyDisplayEntity(blockDisplay, highlightColor, highlightBlock);
        return blockDisplay;
    }

    /**
     * Summons a display entity to highlight the provided bed block
     *
     * @param highlightColor color to highlight the block with
     * @param bedBlock       bed block to highlight
     * @return Block Display that is produced
     */

    public static BlockDisplay summonBedDisplayEntity(Color highlightColor, Block bedBlock) {
        if (!(bedBlock.getBlockData() instanceof Bed bedData)) {
            return null;
        }
        BlockFace facing = bedData.getFacing();
        Location bedLocation = bedBlock.getLocation();
        BlockDisplay blockDisplay = (BlockDisplay) bedLocation.getWorld().spawnEntity(bedLocation, EntityType.BLOCK_DISPLAY);
        rotateDisplay(facing, blockDisplay);
        modifyDisplayEntity(blockDisplay, highlightColor, bedBlock);
        return blockDisplay;
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
