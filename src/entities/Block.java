package entities;

import gameRun.GameLoop;
import models.RawModel;
import org.lwjgl.util.vector.Vector3f;

public class Block {

    private static final float S = 1f;

    private BlockType blockType;
    private Vector3f position;

    public Block(Vector3f position, BlockType blockType) {
        this.position = position;
        this.blockType = blockType;
        //System.out.println(this.blockType);
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public Vector3f getPosition() {
        return position;
    }

}
