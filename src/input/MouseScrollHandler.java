package input;

import org.lwjgl.glfw.GLFWScrollCallback;

public class MouseScrollHandler extends GLFWScrollCallback {

    private static float dY;

    @Override public void invoke (long win, double dx, double dy) {
        MouseScrollHandler.dY = (float) dy;
    }

    static void checkContinuous(){
        //System.out.println(scrollLevel);
        dY = 0;
    }

    public static float getDY(){
        return dY;
    }
}
