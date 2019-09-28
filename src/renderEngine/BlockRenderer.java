package renderEngine;

import entities.Block;
import entities.BlockType;
import models.RawModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import shaders.StaticShader;
import textures.ModelTexture;
import toolbox.Maths;

import java.util.List;
import java.util.Map;

class BlockRenderer {

    private StaticShader shader;

    BlockRenderer(StaticShader shader, Matrix4f projectionMatrix){

        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    void render(Map<BlockType, List<Block>> blocks){
        for(BlockType blockType: blocks.keySet()){
            prepareTexturedModel(blockType);
            List<Block> batch = blocks.get(blockType);

            for(Block block : batch){
                prepareInstance(block);
                /*glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, texcoords_data); // texcoords_data is a float*, 2 per vertex, representing UV coordinates.
                glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, normals_data); // normals_data is a float*, 3 per vertex, representing normal vectors.
                glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, vertex_data); // vertex_data is a float*, 3 per vertex, representing the position of each vertex*/

                GL11.glDrawElements(GL11.GL_TRIANGLES, blockType.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }

            unbindTexturedModel();
        }
    }

    private void prepareTexturedModel(BlockType blockType){

        RawModel rawModel = blockType.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        ModelTexture texture = blockType.getTexturedModel().getTexture();

        shader.loadNumberOfRows(texture.getNumberOfRows());

        if(texture.isHasTransparency()){
            MasterRender.disableCulling();
        }
        shader.loadFakeLightingVariable(texture.isUseFakeLighting());
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());

    }

    private void unbindTexturedModel(){

        MasterRender.enableCulling();
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);

    }

    private void prepareInstance(Block block){

        Matrix4f transformationMatrix = Maths.createTransformationMatrix(block.getPosition(), 0,
                0, 0, 1);
        shader.loadTransformationMatrix(transformationMatrix);
        //shader.loadOffset(block.getTextureXOffset(), block.getTextureYOffset());
    }

}
