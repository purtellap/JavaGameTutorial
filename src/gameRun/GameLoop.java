package gameRun;

import entities.*;
import gui.GuiRenderer;
import gui.GuiTexture;
import input.*;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import renderEngine.FileHandler.ModelData;
import renderEngine.FileHandler.OBJFileLoader;
import textures.ModelTexture;
import toolbox.Physics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;

public class GameLoop {

    private static DisplayManager display;
    public static DisplayManager getDisplay() {
        return display;
    }

    private static Player player;
    public static Player getPlayer() {
        return player;
    }

    private static Camera camera;
    public static Camera getCamera() {
        return camera;
    }

    private static Loader loader;
    public static Loader getLoader() { return loader; }

    public static void main(String[] args) {

        System.out.println("Hello LWJGL " + Version.getVersion() + "!" );

        display = new DisplayManager();
        display.createDisplay();
        GL.createCapabilities();
        new GameLoop().run();

    }

    private void run(){
        System.out.println("Running...\n");

        MasterInputHandler.setup();
        loader = new Loader();
        MasterRender renderer = new MasterRender(display);
        GuiRenderer guiRenderer = new GuiRenderer(loader);

        /*player = new Player(
                new TexturedModel(
                        OBJLoader.loadCube(loader),
                        new ModelTexture(loader.loadTexture("cube"))),
                new Vector3f(0,0,0),0,0,0, 1);*/

        ModelData playerModelData = OBJFileLoader.loadOBJ("oldman");
        TexturedModel playerTM = new TexturedModel(loader.loadToVAO(playerModelData.getVertices(), playerModelData.getTextureCoords(),
                playerModelData.getNormals(), playerModelData.getIndices()),
                    new ModelTexture(loader.loadTexture("oldman")));

        player = new Player(playerTM, new Vector3f(0,10,0),0,0,0, 1/16f);

        camera = new Camera(player);
        /*camera.setPosition(new Vector3f(0, 40f, 40f));
        camera.setPitch(45);*/

        /*MAIN LOOP*/
        loop(renderer, guiRenderer);

        /*PACK IT UP AND CLEAR OUT BOYS*/
        loader.cleanUp();
        renderer.cleanUp();
        guiRenderer.cleanUp();

        glfwFreeCallbacks(display.getWindow());
        glfwDestroyWindow(display.getWindow());
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void loop(MasterRender renderer, GuiRenderer guiRenderer){

        List<Light> lights = new ArrayList<>();
        Light sun = new Light(new Vector3f(0,1000,1000), new Vector3f(1f,1f,1f));
        //lights.add(sun);
        //lights.add(new Light(new Vector3f(-20, 10, 20), new Vector3f(0, 2, 2), new Vector3f(1,0.01f, 0.002f)));
        //lights.add(new Light(new Vector3f(-20, 10, -20), new Vector3f(2, 2, 0), new Vector3f(1,0.01f, 0.002f)));

        List<GuiTexture> guis = new ArrayList<>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("test"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
        //guis.add(gui);

        /* we need to put this somewhere else */
        List<Entity> entities = new ArrayList<>();
        entities.add(player);

        /*List<Chunk> chunks = new ArrayList<>();
        chunks.add(new Chunk());*/
        Block bl = new Block(new Vector3f(0,2,0),BlockType.AIR);
        entities.add(new Entity(bl.getBlockType().getTexturedModel(),new Vector3f(0,0,0),0,0,0, 1f ));
        Chunk c = new Chunk();
        List<Block> blocks = c.getChunk();

        ModelData lampData = OBJFileLoader.loadOBJ("lamppost");
        TexturedModel lampTM = new TexturedModel(loader.loadToVAO(lampData.getVertices(), lampData.getTextureCoords(), lampData.getNormals(), lampData.getIndices()),
                new ModelTexture(loader.loadTexture("lamppost")));
        lampTM.getTexture().setHasTransparency(true);
        lampTM.getTexture().setUseFakeLighting(true);
        Entity lamp = new Entity(lampTM, new Vector3f(0,0,-20f),0,0,0, 1/16f);
        entities.add(lamp);
        lights.add(new Light(new Vector3f(0f,15f,-20f), new Vector3f(2,2,1), new Vector3f(1,0.01f, 0.002f)));

        //RawModel cubeRaw = OBJFileLoader.loadOBJ("cube");
        //TexturedModel grassBlockTM = new TexturedModel(cubeRaw, new ModelTexture(loader.loadTexture("16grass")));

        //entities.add(new Entity(new TexturedModel(cubeRaw, new ModelTexture(loader.loadTexture("cube"))), new Vector3f(0, 0, 0), 0, 0, 0, 1f));

        /*
        ModelData stallData = OBJFileLoader.loadOBJ("stall");
        TexturedModel stallTM = new TexturedModel(loader.loadToVAO(stallData.getVertices(), stallData.getTextureCoords(), stallData.getNormals(), stallData.getIndices()),
                new ModelTexture(loader.loadTexture("stallTexture")));

        /*Entity stall = new Entity(stallTM, new Vector3f(0,0,-20f),0,180,0, 1);
        entities.add(stall);

        Entity stall2 = new Entity(stallTM, new Vector3f(0,0,20f),0,0,0, 1);
        entities.add(stall2);*/

        ModelData fernData = OBJFileLoader.loadOBJ("fern");
        ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fernatlas"));
        fernTextureAtlas.setNumberOfRows(2);

        TexturedModel fernTM = new TexturedModel(loader.loadToVAO(fernData.getVertices(), fernData.getTextureCoords(), fernData.getNormals(), fernData.getIndices()),
                fernTextureAtlas);
        fernTM.getTexture().setHasTransparency(true);
        fernTM.getTexture().setUseFakeLighting(true);

        ModelData grassData = OBJFileLoader.loadOBJ("grass");
        TexturedModel grassTM = new TexturedModel(loader.loadToVAO(grassData.getVertices(), grassData.getTextureCoords(), grassData.getNormals(), grassData.getIndices()),
                new ModelTexture(loader.loadTexture("grassT")));
        grassTM.getTexture().setHasTransparency(true);
        grassTM.getTexture().setUseFakeLighting(true);

        ModelData treeData = OBJFileLoader.loadOBJ("lowPolyTree");
        TexturedModel treeTM = new TexturedModel(loader.loadToVAO(treeData.getVertices(), treeData.getTextureCoords(), treeData.getNormals(), treeData.getIndices()),
                new ModelTexture(loader.loadTexture("lptree")));


        /*Entity cube1 = new Entity(
                new TexturedModel(
                        Block.getBlockRawModel(),
                        new ModelTexture(loader.loadTexture("crate"))),
                new Vector3f(50f,2.5f,0),0,0,0, 5f);
        entities.add(cube1);
        Entity cube2 = new Entity(
                new TexturedModel(
                        Block.getBlockRawModel(),
                        new ModelTexture(loader.loadTexture("crate"))),
                new Vector3f(-50f,2.5f,0),0,0,0, 5f);
        entities.add(cube2);*/

        //Random generation
        Random random = new Random(676452);

        for (int i = 0; i < 600; i++){

            if(i % 7 == 0){
                float x = random.nextFloat() * 400 - 200;
                float z = random.nextFloat() * -1000 + 500;
                entities.add(new Entity(grassTM, new Vector3f(x, 0,
                        z),0,0,0,2f));
            }

            if(i % 3 == 0){
                float x = random.nextFloat() * 400 - 200;
                float z = random.nextFloat() * -1000 + 500;
                float x2 = random.nextFloat() * 800 - 400;
                float z2 = random.nextFloat() * -1000 + 500;
                entities.add(new Entity(fernTM,random.nextInt(4), new Vector3f(x, 0,
                        z),0,random.nextFloat() * 360,0,2f));

                entities.add(new Entity(treeTM, new Vector3f(x2, 0,
                        z2),0,random.nextFloat() * 360,0,5f));
            }

        }

        /* LOOP */
        while ( !glfwWindowShouldClose(display.getWindow()) ) {

            /* Checks events and pushes frame*/
            glfwPollEvents();
            display.tryMemoryStack();
            Physics.keepTime();

            /* Update player and camera (before inputHandler) */
            if(org.lwjgl.glfw.GLFW.glfwGetTime()>2){
                player.updatePlayer();
                if(MasterInputHandler.isCursorDisabled()) {
                    camera.move();
                }
            }

            MasterInputHandler.checkContinuous();

            for(Entity tit : entities){
                renderer.processEntity(tit);
                tit.getModel().getTexture().setShineDamper(1f);
                tit.getModel().getTexture().setReflectivity(.1f);
            }

            /*int i = 0;
            for(Chunk c : chunks){
                for(Block b : c.getChunk()){
                    System.out.println(i);
                    i++;
                    //renderer.processBlock(b);
                    *//*b.getModel().getTexture().setShineDamper(1f);
                    b.getModel().getTexture().setReflectivity(.1f);*//*
                }
            }*/
            for(Block b: blocks){
                b.getBlockType().getTexturedModel().getTexture().setShineDamper(1f);
                b.getBlockType().getTexturedModel().getTexture().setReflectivity(.1f);
                renderer.processBlock(b);
            }
            //System.out.println();
            // fps
            //System.out.println(Math.floor(1/Physics.getFrameTimeSeconds()));
            //System.out.println(org.lwjgl.glfw.GLFW.glfwGetTime());

            /*bitchCube.increaseRotation(1,1,1);
            bitchCube2.increaseRotation(1,1,1);
            cube.increaseRotation(1,1,1);*/

            renderer.render(lights, camera);
            guiRenderer.render(guis);

            /* Sets render viewport*/
            GL11.glViewport(0,0, display.winDynamicWidth, display.winDynamicHeight);
        }

    }

}