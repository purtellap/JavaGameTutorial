package textures;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_MAX_LEVEL;
import static org.lwjgl.opengl.GL14.GL_TEXTURE_LOD_BIAS;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {

    private int textureID;
    private BufferedImage b;

    public Texture(BufferedImage image){

        this.b = image;

    }

    public void makeTexture(){

        int[] pixels = new int[b.getWidth() * b.getHeight()];
        b.getRGB(0, 0, b.getWidth(), b.getHeight(), pixels, 0, b.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(b.getWidth() * b.getHeight() * 4);

        for(int y = 0; y < b.getHeight(); y++)
        {
            for(int x = 0; x < b.getWidth(); x++)
            {
                int pixel = pixels[y * b.getWidth() + x];
                buffer.put((byte)((pixel >> 16) & 0xFF));
                buffer.put((byte)((pixel >> 8) & 0xFF));
                buffer.put((byte)((pixel) & 0xFF));
                buffer.put((byte)((pixel >> 24) & 0xFF));
            }
        }

        buffer.flip();

        // ByteBuffer filled with the color data of each pixel.

        this.textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, b.getWidth(), b.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glGenerateMipmap(GL_TEXTURE_2D);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
/*        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);*/
        // smooth out mipmap?
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, 0);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_LOD_BIAS, 0.75f);

        glBindTexture(GL_TEXTURE_2D, 0);

    }

    public int getTextureID(){
        return this.textureID;
    }

}
