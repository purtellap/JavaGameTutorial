package renderEngine;

import org.lwjgl.glfw.*;
import org.lwjgl.system.*;
import toolbox.Physics;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class DisplayManager {

    /* The window handle and getter*/
    private long window;
    public long getWindow() {return window;}

    /* Initial size of window */
    private final int WIDTH = 1920;
    private final int HEIGHT = 1080;

    /* For fullscreen capabilities */
    private boolean isFullScreen = false;

    /* For centering window */
    private boolean callCenterWin = true;
    public void setCallCenterWin(boolean callCenterWin) {
        this.callCenterWin = callCenterWin;
    }

    /* For render window */
    public int winDynamicWidth;
    public int winDynamicHeight;
    private void setWinDynamicWidth(int winDynamicWidth) {this.winDynamicWidth = winDynamicWidth;}
    private void setWinDynamicHeight(int winDynamicHeight) {this.winDynamicHeight = winDynamicHeight;}
    float getAspectRatio() {
        return (float)this.winDynamicWidth/ (float)this.winDynamicHeight;
    }

    /* Create Display */
    public void createDisplay() {

        final int MIN_WIDTH = WIDTH/6;
        final int MIN_HEIGHT = HEIGHT/6;
        final String WINDOW_TITLE = "TESTES";

        long monitor = GLFW.glfwGetPrimaryMonitor();

        /*Setup an error callback. The default implementation
        Will print the error message in System.err.*/
        GLFWErrorCallback.createPrint(System.err).set();

        /*// Initialize GLFW. Most GLFW functions will not work before doing this.*/
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        /*// Configure GLFW*/
        glfwDefaultWindowHints(); /*// optional, the current window hints are already the default*/
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); /*// the window will stay hidden after creation*/
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); /*// the window will be resizable*/

        /*// Context Attributes */
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        /*// Create the window*/
        window = glfwCreateWindow(WIDTH, HEIGHT, WINDOW_TITLE, (isFullScreen) ? monitor: NULL, window);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        /*// Set window minimum size*/
        glfwSetWindowSizeLimits(window, MIN_WIDTH, MIN_HEIGHT, GLFW_DONT_CARE, GLFW_DONT_CARE);

        //glfwSetWindowIcon(window, 1, windowIcon);

        /*// Made this thing its own method*/
        tryMemoryStack();

        /*// Make the OpenGL context current*/
        glfwMakeContextCurrent(window);
        /*// Enable v-sync*/
        glfwSwapInterval(1);

        /*// Make the window visible*/
        glfwShowWindow(window);

        Physics.setLastFrameTime(GLFW.glfwGetTime());
    }

    public void tryMemoryStack() {
        /*// Get the thread stack and push a new frame*/
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); /*// int**/
            IntBuffer pHeight = stack.mallocInt(1); /*// int**/

            /*// Get the window size passed to glfwCreateWindoW*/
            glfwGetWindowSize(window, pWidth, pHeight);

            /*// Get the resolution of the primary monitor*/
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            /*// Center the window*/
            if(callCenterWin){
                glfwSetWindowPos(
                        window,
                        (vidmode.width() - pWidth.get(0)) / 2,
                        (vidmode.height() - pHeight.get(0)) / 2
                );
                callCenterWin = false;
            }

            setWinDynamicWidth(pWidth.get(0));
            setWinDynamicHeight(pHeight.get(0));

        } /*// the stack frame is popped automatically*/

    }

    /* Doesn't really work right now */
    public void toggleFullScreen(){

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        if(!isFullScreen){
            glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(),0,0, vidmode.width(), vidmode.height(), 0);
            isFullScreen = true;
        }
        else{
            glfwSetWindowMonitor(window, NULL,0,0, WIDTH, HEIGHT, 0);
            callCenterWin = true;
            isFullScreen = false;
        }

    }

}
