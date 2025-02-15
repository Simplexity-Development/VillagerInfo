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

public class ShowDisplay {

    /**
     * Summons the block display
     * Takes in color to highlight and block to highlight
     *
     * @param highlightColor Color to highlight the block
     * @param highlightBlock Block to highlight
     */
    public static BlockDisplay summonBlockDisplayEntity(Color highlightColor, Block highlightBlock) {
        if (highlightBlock.getBlockData() instanceof Bed) {
            return summonBedDisplayEntity(highlightColor, highlightBlock);
        }
        BlockDisplay blockDisplay = (BlockDisplay) highlightBlock.getLocation().getWorld().spawnEntity(highlightBlock.getLocation(), EntityType.BLOCK_DISPLAY);
        modifyDisplayEntity(blockDisplay, highlightColor, highlightBlock);
        return blockDisplay;
    }

    private static BlockDisplay summonBedDisplayEntity(Color highlightColor, Block bedBlock) {
        if (!(bedBlock.getBlockData() instanceof Bed bedData)) {
            return null;
        }
        BlockFace facing = bedData.getFacing();
        Location bedLocation = bedBlock.getLocation();
        if (bedData.getPart().equals(Bed.Part.FOOT)) {
            bedLocation = getHeadLocation(bedLocation, facing);
        }
        if (bedLocation == null) return null;
        BlockDisplay blockDisplay = (BlockDisplay) bedLocation.getWorld().spawnEntity(bedLocation, EntityType.BLOCK_DISPLAY);
        rotateDisplay(facing, blockDisplay);
        modifyDisplayEntity(blockDisplay, highlightColor, bedBlock);
        return blockDisplay;
    }

    /**
     * Modifies the summoned entity to fit the specifications of the default config and
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


    private static Location getHeadLocation(Location location, BlockFace facing) {
        return switch (facing) {
            case NORTH -> location.add(0, 0, -1);
            case EAST -> location.add(1, 0, 0);
            case SOUTH -> location.add(0, 0, 1);
            case WEST -> location.add(-1, 0, 0);
            default -> null;
        };
    }

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

    public static Transformation bedDisplayTransformation(int headX, int headY, int headZ, float rotationAngle) {
        Vector3f translationToOrigin = new Vector3f().add(headX, headY, headZ);
        Quaternionf rotation = new Quaternionf().rotateY(rotationAngle);
        Quaternionf noRotation = new Quaternionf().rotateY(0);
        Vector3f scale = new Vector3f(1, 1, 1);
        return new Transformation(translationToOrigin, rotation, scale, noRotation);
    }


}
