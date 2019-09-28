package entities;

import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import toolbox.Physics;

public class Player extends Entity {

    private static final float MAX_RUN_SPEED = 20f;
    private static final float MAX_TURN_SPEED = 200f;
    private static final float GRAVITY = -50f;
    private static final float JUMP_POWER = 30f;

    private static final float RUN_FORCE = 5000f;
    private static final float TURN_FORCE = 1000f;
    private static final float JUMP_FORCE = 50f;
    private static final float FRICTION = .2f;

    private String name;
    private float size = BlockType.getS();

    private Vector3f moveDistance = new Vector3f(0, 0, 0);
    private float runDistance;
    private float turnDistance;

    private float tempVelocity;
    // convert distance to vector
    private float distanceX;
    private float distanceZ;
    private Vector3f force = new Vector3f(0, 0, 0);
    private Vector3f acceleration = new Vector3f(0, 0, 0);
    private Vector3f velocity = new Vector3f(0, 0, 0);

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    private float mass = 1f;

    private float terrainHeight = 0;

    private static boolean isGrounded;

    private float momentum;

    public boolean openUp;
    public boolean openDown;
    public boolean openFront;
    public boolean openBack;
    public boolean openLeft;
    public boolean openRight;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void updatePlayer() {

        turnDistance = Physics.getDistance(MAX_TURN_SPEED);

        obeyGravity();
        terrainCollision();
        obeyFriction();

        movePlayer();

    }

    private void movePlayer() {
        super.getPosition().x += Physics.getDistance(velocity.x);
        super.getPosition().z += Physics.getDistance(velocity.z);
        if((Physics.getDistance(velocity.y) < 0 && openDown) || Physics.getDistance(velocity.y) > 0 && openUp) {
            super.getPosition().y += Physics.getDistance(velocity.y);
        }
        openUp = true;
        openDown = true;
    }

    public void movePlayerForward() {

        //velocity.x -= (float) (MAX_RUN_SPEED * Math.sin(Math.toRadians(super.getRotY()))) * Physics.getFrameTimeSeconds();
        //velocity.z -= (float) (MAX_RUN_SPEED * Math.cos(Math.toRadians(super.getRotY()))) * Physics.getFrameTimeSeconds();

        super.getPosition().x -= (float) (MAX_RUN_SPEED * Math.sin(Math.toRadians(super.getRotY()))) * Physics.getFrameTimeSeconds();
        super.getPosition().z -= (float) (MAX_RUN_SPEED * Math.cos(Math.toRadians(super.getRotY()))) * Physics.getFrameTimeSeconds();

    }

    public void movePlayerLeft() {

        //velocity.x -= (float) (MAX_RUN_SPEED * Math.cos(Math.toRadians(super.getRotY()))) * Physics.getFrameTimeSeconds();
        //velocity.z += (float) (MAX_RUN_SPEED * Math.sin(Math.toRadians(super.getRotY()))) * Physics.getFrameTimeSeconds();

        super.getPosition().x -= (float) (MAX_RUN_SPEED * Math.cos(Math.toRadians(super.getRotY()))) * Physics.getFrameTimeSeconds();
        super.getPosition().z += (float) (MAX_RUN_SPEED * Math.sin(Math.toRadians(super.getRotY()))) * Physics.getFrameTimeSeconds();
    }

    public void movePlayerRight() {

        //velocity.x += (float) (MAX_RUN_SPEED * Math.cos(Math.toRadians(super.getRotY()))) * Physics.getFrameTimeSeconds();
        //velocity.z -= (float) (MAX_RUN_SPEED * Math.sin(Math.toRadians(super.getRotY()))) * Physics.getFrameTimeSeconds();

        super.getPosition().x += (float) (MAX_RUN_SPEED * Math.cos(Math.toRadians(super.getRotY()))) * Physics.getFrameTimeSeconds();
        super.getPosition().z -= (float) (MAX_RUN_SPEED * Math.sin(Math.toRadians(super.getRotY()))) * Physics.getFrameTimeSeconds();
    }

    public void movePlayerBackward() {

        //velocity.x += (float) (MAX_RUN_SPEED * Math.sin(Math.toRadians(super.getRotY()))) * Physics.getFrameTimeSeconds();
        //velocity.z += (float) (MAX_RUN_SPEED * Math.cos(Math.toRadians(super.getRotY()))) * Physics.getFrameTimeSeconds();

        super.getPosition().x += (float) (MAX_RUN_SPEED * Math.sin(Math.toRadians(super.getRotY()))) * Physics.getFrameTimeSeconds();
        super.getPosition().z += (float) (MAX_RUN_SPEED * Math.cos(Math.toRadians(super.getRotY()))) * Physics.getFrameTimeSeconds();

    }

    public void turnPlayerLeft() {
        super.increaseRotation(0, turnDistance, 0);
    }

    public void turnPlayerRight() {
        super.increaseRotation(0, -turnDistance, 0);
    }


    public void jump() {

        velocity.y = JUMP_POWER;

    }

    private void obeyGravity(){
        velocity.y += (GRAVITY / mass) * Physics.getFrameTimeSeconds();
    }

    private void terrainCollision(){
        float h = size / 2f;
        if (super.getPosition().y < 0 && openDown) {
            openDown = false;
            velocity.y = 0;
            super.getPosition().y = 0;
        }
    }

    private void obeyFriction(){
        /*if(velocity.x > 0.1f){
            velocity.x -= (frictionForce / mass) * Physics.getFrameTimeSeconds();
        }
        else if (velocity.x < 0.1f){
            velocity.x += (frictionForce / mass) * Physics.getFrameTimeSeconds();
        }
        else{
            velocity.x = 0;
        }

        if(velocity.z > 0.1f){
            velocity.z -= (frictionForce / mass) * Physics.getFrameTimeSeconds();
        }
        else if (velocity.z < 0.1f){
            velocity.z += (frictionForce / mass) * Physics.getFrameTimeSeconds();
        }
        else{
            velocity.z = 0;
        }*/
    }

}