package entities;

import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chunk {

    private List<Block> chunk = new ArrayList<>();
    public List<Block> getChunk() {return chunk;}

    final int CHUNK_SIZE = 8;
    private float x;
    private float y;
    private float z;

    public Chunk(){
        createChunk();
    }

    private void createChunk(){

        Random r = new Random();
        // y = (y - CHUNK_SIZE+1)- r.nextInt(4)
        for(int x = -CHUNK_SIZE + 1; x < CHUNK_SIZE; x++){
            for(int y = -CHUNK_SIZE + 1; y < CHUNK_SIZE; y++) {
                for(int z = -CHUNK_SIZE + 1; z < CHUNK_SIZE; z++) {
                    if(r.nextInt(2) == 0) {
                        chunk.add(new Block(new Vector3f(x, (y - CHUNK_SIZE+1)- r.nextInt(4), z), BlockType.GRASS));
                    }
                    else{
                        chunk.add(new Block(new Vector3f(x, (y - CHUNK_SIZE+1)- r.nextInt(4), z), BlockType.DIRT));
                    }
                }
            }
        }
    }
}
