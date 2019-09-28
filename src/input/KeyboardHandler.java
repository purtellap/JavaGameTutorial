package input;

import entities.Player;
import gameRun.GameLoop;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardHandler extends GLFWKeyCallback {

    private static boolean[] keysDown = new boolean[65536];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keysDown[key] = (action != GLFW_RELEASE);
        if(action == GLFW_PRESS){
            checkSingle(key, window);
        }
    }

    private static boolean isKeyDown(int key) {
        return keysDown[key];
    }

    private void checkSingle(int key, long window){
        if(key == GLFW_KEY_C){
            GameLoop.getPlayer().setPosition(new Vector3f(0,50,0));
            GameLoop.getPlayer().setRotY(0);
            GameLoop.getPlayer().setVelocity(new Vector3f(0,0,0));
        }
        if(key == GLFW_KEY_X){
            glfwSetWindowShouldClose(window, true);
            System.out.println("Closing Program.");
        }
        if(key == GLFW_KEY_ESCAPE){

            GameLoop.getDisplay().toggleFullScreen();
            System.out.println("FullScreen Toggled.");
        }

        if(key == GLFW_KEY_LEFT_ALT){

            MasterInputHandler.setCursorDisabled(!MasterInputHandler.isCursorDisabled());
        }
    }

    static void checkContinuous(){

        Player player = GameLoop.getPlayer();
        /* Check continuous keyboard input*/
        /* WASD movement*/

        if(isKeyDown(GLFW_KEY_W)){

            player.movePlayerForward();

        }
        if(isKeyDown(GLFW_KEY_A)){

            player.movePlayerLeft();

        }
        if(isKeyDown(GLFW_KEY_S)){

            player.movePlayerBackward();

        }
        if(isKeyDown(GLFW_KEY_D)){

            player.movePlayerRight();
        }

        if(isKeyDown(GLFW_KEY_SPACE)){

            player.jump();

        }

        if(isKeyDown(GLFW_KEY_LEFT_SHIFT)){


        }

        if(isKeyDown(GLFW_KEY_Q)){

            player.turnPlayerLeft();

        }

        if(isKeyDown(GLFW_KEY_E)){

            player.turnPlayerRight();

        }

        if(isKeyDown(GLFW_KEY_UP)){

        }

        if(isKeyDown(GLFW_KEY_DOWN)){

        }

        if(isKeyDown(GLFW_KEY_LEFT)){

        }

        if(isKeyDown(GLFW_KEY_RIGHT)){

        }

        if(isKeyDown(GLFW_KEY_3)){


        }

    }

}
