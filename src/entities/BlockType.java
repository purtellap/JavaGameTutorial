package entities;

import gameRun.GameLoop;
import models.RawModel;
import models.TexturedModel;
import textures.ModelTexture;

public enum BlockType {

    STONE("crate"), DIRT("mud"), GRASS("16grass"), AIR("cube");

    private static final float S = 1f;
    public static float getS() { return S; }
    private RawModel rawModel = getBlockRawModel();
    public RawModel getRawModel() { return rawModel;}
    private TexturedModel texturedModel;

    BlockType(String fileName){
        this.texturedModel = new TexturedModel(rawModel, new ModelTexture(GameLoop.getLoader().loadTexture(fileName)));
    }

    public TexturedModel getTexturedModel() {
        return texturedModel;
    }

    private static RawModel getBlockRawModel() {

        float[] verticesArray = {

                // BACK
                S / 2.0f,  -S / 2.0f,  -S / 2.0f,  // A
                -S / 2.0f,  -S / 2.0f,  -S / 2.0f,  // B
                -S / 2.0f,   S / 2.0f,  -S / 2.0f,  // C
                S / 2.0f,   S / 2.0f,  -S / 2.0f,  // D

                // FRONT
                -S / 2.0f,  -S / 2.0f,   S / 2.0f,  // E
                S / 2.0f,  -S / 2.0f,   S / 2.0f,  // F
                S / 2.0f,   S / 2.0f,   S / 2.0f,  // G
                -S / 2.0f,   S / 2.0f,   S / 2.0f,  // H

                // RIGHT
                S / 2.0f,  -S / 2.0f,   S / 2.0f,  // D
                S / 2.0f,  -S / 2.0f,  -S / 2.0f,  // C
                S / 2.0f,   S / 2.0f,  -S / 2.0f,  // G
                S / 2.0f,   S / 2.0f,   S / 2.0f,  // H

                // LEFT
                -S / 2.0f,  -S / 2.0f,  -S / 2.0f,  // A
                -S / 2.0f,  -S / 2.0f,   S / 2.0f,  // B
                -S / 2.0f,   S / 2.0f,   S / 2.0f,  // F
                -S / 2.0f,   S / 2.0f,  -S / 2.0f,  // E

                // TOP
                -S / 2.0f,   S / 2.0f,   S / 2.0f,  // E
                S / 2.0f,   S / 2.0f,   S / 2.0f,  // A
                S / 2.0f,   S / 2.0f,  -S / 2.0f,  // D
                -S / 2.0f,   S / 2.0f,  -S / 2.0f,  // H

                // BOTTOM
                -S / 2.0f,  -S / 2.0f,  -S / 2.0f,  // F
                S / 2.0f,  -S / 2.0f,  -S / 2.0f,  // B
                S / 2.0f,  -S / 2.0f,   S / 2.0f,  // C
                -S / 2.0f,  -S / 2.0f,   S / 2.0f,  // G

        };


        float[] textureArray = {

                0,1, // A
                1,1, // B
                1,0, // C
                0,0, // D

                0,1, // E
                1,1, // F
                1,0, // G
                0,0, // H

                0,1, // D
                1,1, // C
                1,0, // G
                0,0, // H

                0,1, // A
                1,1, // B
                1,0, // F
                0,0, // E

                0,1, // E
                1,1, // A
                1,0, // D
                0,0, // H

                0,1, // F
                1,1, // B
                1,0, // C
                0,0  // G

        };
        float[] normalsArray = {

                // BACK
                0f,0f,1f,
                0f,0f,1f,
                0f,0f,1f,
                0f,0f,1f,

                // FRONT
                0f,0f,-1f,
                0f,0f,-1f,
                0f,0f,-1f,
                0f,0f,-1f,

                // RIGHT
                1f,0f,0f,
                1f,0f,0f,
                1f,0f,0f,
                1f,0f,0f,

                //LEFT
                -1f,0f,0f,
                -1f,0f,0f,
                -1f,0f,0f,
                -1f,0f,0f,

                // TOP
                0f,1f,0f,
                0f,1f,0f,
                0f,1f,0f,
                0f,1f,0f,

                // BOTTOM
                0f,-1f,0f,
                0f,-1f,0f,
                0f,-1f,0f,
                0f,-1f,0f,

        };
        int[] indicesArray = {

                0,  1,  2,
                2,  3,  0,

                4,  5,  6,
                6,  7,  4,

                8,  9, 10,
                10, 11,  8,

                12, 13, 14,
                14, 15, 12,

                16, 17, 18,
                18, 19, 16,

                20, 21, 22,
                22, 23, 20

        };

        return GameLoop.getLoader().loadToVAO(verticesArray, textureArray, normalsArray, indicesArray);
    }


}