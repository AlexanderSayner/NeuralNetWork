package sayner.sandbox.neuralG.level;

import sayner.sandbox.neuralG.graphics.Shader;
import sayner.sandbox.neuralG.graphics.Texture;
import sayner.sandbox.neuralG.graphics.VertexArray;

public class Level {

    private VertexArray background;
    private Texture bgTexture;

    public Level() {
/*

        // Let's go
        float[] vertices = new float[] {
                -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f,   0.0f, 1.0f, // Bottom Left
                -1.0f,  1.0f, 0.0f, 1.0f, 1.0f, 0.0f,   0.0f, 0.0f,  // Top Left
                0.0f,  1.0f, 0.0f, 1.0f, 0.0f, 0.0f,   1.0f, 0.0f, // Top Right
                0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f,   1.0f, 1.0f, // Bottom Right
        };
*/

        // Let's go
        float[] vertices = new float[] {
                -10.0f, -10.0f * 9.0f / 16.0f, 0.0f,    // Bottom Left
                -10.0f,  10.0f * 9.0f / 16.0f, 0.0f,    // Top Left
                  0.0f,  10.0f * 9.0f / 16.0f, 0.0f,    // Top Right
                  0.0f, -10.0f * 9.0f / 16.0f, 0.0f,    // Bottom Right
        };

        byte[] indices = new byte[] {
                0, 1, 2,
                2, 3, 0
        };

        float[] textureCoordinates = new float[] {
                0.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
        };

        this.background = new VertexArray(vertices, indices, textureCoordinates);
        this.bgTexture = new Texture("./src/main/resources/img/bg.jpeg");
//        this.bgTexture = new Texture("./src/main/resources/img/one.png");
    }

    public void render() {

        // Текстурки он
        this.bgTexture.bind();
        // Надо надеяться, что эта штука инициализирована
        Shader.BackGround.enable();
        // Теперь его надо "завести"
        this.background.render();
        // И убить
        this.background.unbind();
        Shader.BackGround.disable();
        this.bgTexture.unbind();
    }
}
