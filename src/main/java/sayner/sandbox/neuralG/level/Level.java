package sayner.sandbox.neuralG.level;

import sayner.sandbox.neuralG.graphics.Shader;
import sayner.sandbox.neuralG.graphics.Texture;
import sayner.sandbox.neuralG.graphics.VertexArray;
import sayner.sandbox.neuralG.maths.impl.Matrix4f;
import sayner.sandbox.neuralG.maths.impl.Vector3f;

/**
 * Класс отрисовки фона уровня
 */
public class Level {

    private VertexArray background;
    private Texture bgTexture;

    // Horizontal scroll
    private int xScroll = 0;
    private int map = 0;

    /**
     * Создание полигона фона и заполнение текстурой
     */
    public Level() {

        // Let's go
        float[] vertices = new float[]{
                -10.0f, -10.0f * 9.0f / 16.0f, 0.0f,    // Bottom Left
                -10.0f, 10.0f * 9.0f / 16.0f, 0.0f,    // Top Left
                0.0f, 10.0f * 9.0f / 16.0f, 0.0f,    // Top Right
                0.0f, -10.0f * 9.0f / 16.0f, 0.0f,    // Bottom Right
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

        this.background = new VertexArray(vertices, indices, textureCoordinates);
        this.bgTexture = new Texture("./src/main/resources/img/bg.jpeg");
    }

    public void update() {
        // И оно ещё должно будет двигаться
        xScroll--;
        // Map will moving left, that's why negative
        if (-xScroll % 335 == 0) {
            map++;
        }
    }

    /**
     * Цикл отрисовки картиночки на фоне
     */
    public void render() {

        // Текстурки он
        this.bgTexture.bind();
        // Надо надеяться, что эта штука инициализирована
        Shader.BackGround.enable();

        // Связать
        this.background.bind();

        for (int i = map; i < map + 3; i++) {

            Shader.BackGround.setUniformMat4f("view_matrix", Matrix4f.translate(new Vector3f(i * 10 + xScroll*0.03f, 0.0f, 0.0f)));            // Теперь его надо "завести"
            this.background.draw();
        }

        // И убить
        this.background.unbind();
        Shader.BackGround.disable();
        this.bgTexture.unbind();
    }
}
