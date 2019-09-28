package toolbox;

import entities.Entity;
import entities.Player;
import gameRun.GameLoop;
import org.lwjgl.glfw.GLFW;

public class Physics {

    private static final float GRAVITATIONAL_CONSTANT = (float)(6.67 * Math.pow(10,-11)); // N * m^2 / kg^2

    private static double lastFrameTime;
    private static double deltaTime;

    public static void setLastFrameTime(double lastFTime) {
        Physics.lastFrameTime = lastFTime;
    }

    public static void keepTime(){
        double currentFrameTime = GLFW.glfwGetTime();
        deltaTime = (currentFrameTime - lastFrameTime);
        lastFrameTime = currentFrameTime;
    }

    public static float getFrameTimeSeconds(){
        return (float)deltaTime;
    }

    public static float getVelocity(float distance){
        return (float)(distance / getFrameTimeSeconds());
    }
    public static float getDistance(float velocity){
        return (float)(velocity * getFrameTimeSeconds());
    }

    public static float getMomentum(float mass, float velocity){
        return mass * velocity;
    }

    public static float getForce(float mass, float acceleration){
        return mass * acceleration;
    }

    /* Change Gravitational constant to adjust for smaller values of m1 and m2 */
    public static float getGravitationalForce(float mass1, float mass2, float distanceR){
        return (float)((GRAVITATIONAL_CONSTANT * mass1 * mass2) / Math.pow(distanceR, 2));
    }

    public static void move(){

        float dT = getFrameTimeSeconds();
        float velocity = 0;
        float position = 0;
        float force = 10f;
        float mass = 1f;




    }

    public static float getVectorSin(float magnitude, float theta){
        return (float)(magnitude * Math.sin(Math.toRadians(theta)));
    }
    public static float getVectorCos(float magnitude, float theta){
        return (float)(magnitude * Math.cos(Math.toRadians(theta)));
    }

}
