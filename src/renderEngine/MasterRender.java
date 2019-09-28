package renderEngine;

import entities.*;
import models.TexturedModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import shaders.StaticShader;
import shaders.TerrainShader;
import terrain.Terrain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

public class MasterRender {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000.0f;
    private static final Vector3f backgroundColor = new Vector3f(0.25f, 0.2f, 0.25f);

    private float aspectRatio;
    private Matrix4f projectionMatrix;

    private DisplayManager display;

    private EntityRenderer entityRenderer;
    private StaticShader shader;

    private TerrainRenderer terrainRenderer;
    private TerrainShader terrainShader;

    private BlockRenderer blockRenderer;
    private StaticShader blockShader;

    private Map<TexturedModel, List<Entity>> entities = new HashMap<>();
    private List<Terrain> terrains = new ArrayList<>();
    private Map<BlockType, List<Block>> blocks = new HashMap<>();

    public MasterRender(DisplayManager d){

        enableCulling();

        this.display = d;
        this.aspectRatio = display.getAspectRatio();
        this.shader = new StaticShader();
        this.blockShader = new StaticShader();
        this.terrainShader = new TerrainShader();
        createProjectionMatrix();
        this.entityRenderer = new EntityRenderer(shader, projectionMatrix);
        this.terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
        this.blockRenderer = new BlockRenderer(blockShader, projectionMatrix);

    }

    public static void enableCulling(){
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public static void disableCulling(){
        GL11.glDisable(GL11.GL_CULL_FACE);
    }

    private void prepare(DisplayManager display){

        /*Swaps buffers, keep in front*/
        glfwSwapBuffers(display.getWindow());
        GL11.glEnable(GL_DEPTH_TEST);

        /*Clearage*/
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(backgroundColor.x, backgroundColor.y,backgroundColor.z,1.0f);
    }

    public void render(List<Light> lights, Camera camera){
        prepare(display);

        blockShader.start();
        blockShader.loadSkyColor(backgroundColor);
        blockShader.loadLights(lights);
        blockShader.loadViewMatrix(camera);
        blockRenderer.render(blocks);
        blockShader.stop();

        shader.start();
        shader.loadSkyColor(backgroundColor);
        shader.loadLights(lights);
        shader.loadViewMatrix(camera);
        entityRenderer.render(entities);
        shader.stop();


        terrainShader.start();
        terrainShader.loadSkyColor(backgroundColor);
        terrainShader.loadLights(lights);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();

        terrains.clear();
        entities.clear();
        blocks.clear();
    }

    public void processTerrain(Terrain terrain){
        terrains.add(terrain);
    }

    public void processEntity(Entity entity){
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if(batch != null){
            batch.add(entity);
        }
        else{
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

    public void processBlock(Block block){
        BlockType blockType = block.getBlockType();
        List<Block> batch = blocks.get(blockType);
        if(batch != null){
            batch.add(block);
        }
        else{
            List<Block> newBatch = new ArrayList<>();
            newBatch.add(block);
            blocks.put(blockType, newBatch);
        }
    }

    private void createProjectionMatrix(){

        float y_scale = (float)((1f / Math.tan(Math.toRadians(FOV / 2f)))* this.aspectRatio);
        float x_scale = y_scale / this.aspectRatio;
        float frustrum_length = FAR_PLANE - NEAR_PLANE;
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustrum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -(( 2 * NEAR_PLANE * FAR_PLANE) / frustrum_length);
        projectionMatrix.m33 = 0;
    }

    public void cleanUp(){
        shader.cleanUp();
        terrainShader.cleanUp();
        blockShader.cleanUp();
    }

}
