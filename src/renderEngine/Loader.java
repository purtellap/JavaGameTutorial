package renderEngine;

import models.RawModel;
import org.lwjgl.opengl.*;
import textures.Texture;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();

    public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices){

        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, textureCoords);
        storeDataInAttributeList(2, 3, normals);
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    public RawModel loadToVAO(float[] positions){
        int vaoID = createVAO();
        this.storeDataInAttributeList(0,2, positions);
        unbindVAO();
        return new RawModel(vaoID, positions.length/2);
    }

    public int loadTexture(String fileName){

        Texture t;
        int textureID;
        BufferedImage img = null;
        try {

            img = ImageIO.read(new FileInputStream("res/" + fileName + ".png"));

        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("File not found, clown");
        }

        t = new Texture(img);

        t.makeTexture();

        textureID = t.getTextureID();
        textures.add(textureID);

        return textureID;

    }


    public void cleanUp(){
        for (int vao:vaos){
            GL30.glDeleteVertexArrays(vao);
        }
        for (int vbo:vbos){
            GL30.glDeleteBuffers(vbo);
        }
        for (int texture:textures){
            GL11.glDeleteTextures(texture);
        }
    }

    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributeList(int attributeNum, int coordinateSize, float[] data){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFB(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNum, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void unbindVAO() {
        GL30.glBindVertexArray(0);
    }

    private void bindIndicesBuffer(int[] indices){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private FloatBuffer storeDataInFB (float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

}

