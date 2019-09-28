package input;

import entities.Camera;
import gameRun.GameLoop;
import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MousePosHandler extends GLFWCursorPosCallback {

    /*private static int w = GameLoop.getDisplay().winDynamicWidth;
    private static int h = GameLoop.getDisplay().winDynamicHeight;

    private static double centerX = w / 2.0;
    private static double centerY = h / 2.0;

    private static double xPos = centerX;
    private static double yPos = centerY;*/

    private static double xPos;
    private static double yPos;

    private static double oldX = 0;
    private static double oldY = 0;

    private static double deltaX;
    private static double deltaY;

    private static int hasMovedMouse = 0;

    @Override
    public void invoke(long window, double xpos, double ypos) {

        oldX = xPos;
        oldY = yPos;

        xPos = xpos;
        yPos = ypos;

        deltaX = xPos - oldX;
        deltaY = oldY - yPos;

        hasMovedMouse = 1;
    }

    static void checkContinuous(){
        hasMovedMouse = 0;
    }

    public static int getDX(){
        return (int)deltaX * hasMovedMouse;
    }

    public static int getDY(){
        return (int)deltaY * hasMovedMouse;
    }

}
