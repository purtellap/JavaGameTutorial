package input;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.*;

public class MouseButtonHandler extends GLFWMouseButtonCallback {

    @Override
    public void invoke(long window, int button, int action, int mods) {

        //System.out.println(action);
        if(action == GLFW_PRESS){
            checkSingle(button);
        }

    }

    private void checkSingle(int button){
        if(button == GLFW_MOUSE_BUTTON_LEFT){
            System.out.println("Left click");
        }
    }

    static void checkContinuous(){

    }
}
