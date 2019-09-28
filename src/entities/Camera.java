package entities;

import input.MousePosHandler;
import input.MouseScrollHandler;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private Vector3f position = new Vector3f(0,0,0);
    private float pitch = 20;
    private float yaw;
    private float roll;

    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 180;
    private Player player;

    private static final float MAX_ZOOM = 100;
    private static final float MIN_ZOOM = 2;

    public Camera(Player player){
        this.player = player;

    }

    public void move(){
        setZoom();
        setPitchAngle();
        calculateAngleAroundPlayer();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
        this.yaw = 180 - (player.getRotY() + angleAroundPlayer);

        //System.out.println(distanceFromPlayer);

    }

    private void setZoom(){
        float distance = MouseScrollHandler.getDY() * 4;

        if(distanceFromPlayer > MAX_ZOOM){
            distanceFromPlayer = MAX_ZOOM;
        }
        else if(distanceFromPlayer < MIN_ZOOM){
            distanceFromPlayer = MIN_ZOOM;
        }
        else if(distanceFromPlayer == MIN_ZOOM) {
            if(distance > 0){
                distanceFromPlayer += distance;
            }
        }
        else if(distanceFromPlayer == MAX_ZOOM) {
            if(distance < 0){
                distanceFromPlayer += distance;
            }
        }
        else {
            distanceFromPlayer += distance;
        }

    }

    private void setPitchAngle(){
        float deltaPitch = MousePosHandler.getDY();
        if(pitch > 180){
            pitch = 180;
        }
        else if(pitch < 0){
            pitch = 0;
        }
        else if(pitch == 0) {
            if(deltaPitch < 0){
                pitch -= deltaPitch;
            }
        }
        else if(pitch == 180) {
            if(deltaPitch > 0){
                pitch -= deltaPitch;
            }
        }
        else {
            pitch -= deltaPitch;
        }
    }

    private void calculateAngleAroundPlayer(){
        float deltaAngle = MousePosHandler.getDX();
        angleAroundPlayer -= deltaAngle;
    }

    private float calculateHorizontalDistance(){
        return (float)(distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance(){
        return (float)(distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateCameraPosition(float horizDistance, float vertDistance){
        float theta = player.getRotY() + angleAroundPlayer;
        float offsetX = (float)(horizDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float)(horizDistance * Math.cos(Math.toRadians(theta)));
        this.position.x = player.getPosition().x - offsetX;
        this.position.z = player.getPosition().z - offsetZ;
        this.position.y = player.getPosition().y + vertDistance;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void changePosition(Vector3f change) {
        this.position.x += change.x;
        this.position.y += change.y;
        this.position.z += change.z;
    }

    public void changePitch(float pitch) {
        this.pitch += pitch;
    }
    public void changeYaw(float yaw) {
        this.yaw += yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
    public void setRoll(float roll) {
        this.roll = roll;
    }
}
