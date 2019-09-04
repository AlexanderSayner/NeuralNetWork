package sayner.sandbox.neuralG.level;

import sayner.sandbox.neuralG.graphics.Texture;
import sayner.sandbox.neuralG.graphics.VertexArray;
import sayner.sandbox.neuralG.maths.impl.Matrix4f;
import sayner.sandbox.neuralG.maths.impl.Vector3f;

public class Pipe {

    private Vector3f position = new Vector3f();
    private Matrix4f mlMatrix;

    private static float width = 1.5f, height = 8.0f;
    private static Texture texture;
    private static VertexArray mesh;

    public static void create() {

        // Let's go
        float[] vertices = new float[]{
                0.0f, 0.0f, 0.1f,
                0.0f, height, 0.1f,
                width, height, 0.1f,
                width, 0.0f, 0.1f,
        };

        byte[] indices = new byte[]{
                0, 1, 2,
                2, 3, 0
        };

        float[] textureCoordinates = new float[]{
                0.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
        };

        mesh = new VertexArray(vertices, indices, textureCoordinates);
        texture = new Texture("./src/main/resources/img/pipe.png");
    }

    public Pipe(float x,float y){

        this.position.x=x;
        this.position.y=y;
        this.mlMatrix=Matrix4f.translate(this.position);
    }

    public float getY(){
        return this.position.y;
    }

    public float getX(){
        return this.position.x;
    }

    public Matrix4f getMlMatrix() {
        return mlMatrix;
    }

    public static Texture getTexture() {
        return texture;
    }

    public static VertexArray getMesh() {
        return mesh;
    }

    public static float getWidth() {
        return width;
    }

    public static float getHeight() {
        return height;
    }
}
