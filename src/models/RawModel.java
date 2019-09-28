package models;

public class RawModel {

    private int vaoID;
    private int vertexCount;

    public RawModel(int vID, int vCount){
        this.vaoID = vID;
        this.vertexCount = vCount;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
