package sayner.sandbox.neuralG.level;

import sayner.sandbox.neuralG.graphics.Shader;
import sayner.sandbox.neuralG.graphics.VertexArray;

public class Level {

    private VertexArray background;

    public Level() {

        // Let's go
        float[] vertices = new float[]{
                -10.0f, -10.0f * 9.0f / 16.0f, 0.0f,
                -10.0f, 10.0f * 9.0f / 16.0f, 0.0f,
                0.0f, 10.0f * 9.0f / 16.0f, 0.0f,
                0.0f, -10.0f * 9.0f / 16.0f, 0.0f,
        };

        // Первый треугольник берёт первые 3 вершины
        // Второй возьмёт вершины 3, 4 и 1
        byte[] indices = new byte[]{
                0, 1, 2,
                2, 3, 0
        };

        float[] textureCoordinates = new float[]{
                0, 1,
                0, 0,
                1, 0,
                1, 1
        };

        this.background = new VertexArray(vertices, indices, textureCoordinates);
    }

    public void render(){

        Shader.BackGround.enable();
        this.background.render();
        Shader.BackGround.disable();
    }
}
