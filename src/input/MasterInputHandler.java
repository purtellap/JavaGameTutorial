package input;

import gameRun.GameLoop;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import renderEngine.DisplayManager;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;

public class MasterInputHandler {

    private static GLFWKeyCallback keyCallback;
    private static GLFWCursorPosCallback mousePosCallback;
    private static GLFWMouseButtonCallback mouseButtonCallback;
    private static GLFWScrollCallback scrollCallback;

    private static boolean cursorDisabled = true;

    public static void setup(){

        DisplayManager display = GameLoop.getDisplay();

        glfwSetInputMode(display.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        glfwSetKeyCallback(display.getWindow(), keyCallback = new KeyboardHandler());
        glfwSetCursorPosCallback(display.getWindow(), mousePosCallback = new MousePosHandler());
        glfwSetMouseButtonCallback(display.getWindow(), mouseButtonCallback = new MouseButtonHandler());
        glfwSetScrollCallback(display.getWindow(), scrollCallback = new MouseScrollHandler());
    }

    public static void checkContinuous(){
        KeyboardHandler.checkContinuous();
        MousePosHandler.checkContinuous();
        MouseButtonHandler.checkContinuous();
        MouseScrollHandler.checkContinuous();



    }

    public static boolean isCursorDisabled() {
        return cursorDisabled;
    }

    public static void setCursorDisabled(boolean cursorD) {
        MasterInputHandler.cursorDisabled = cursorD;
        if(cursorDisabled) {
            glfwSetInputMode(GameLoop.getDisplay().getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        }
        else {
            glfwSetInputMode(GameLoop.getDisplay().getWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        }
    }
}
