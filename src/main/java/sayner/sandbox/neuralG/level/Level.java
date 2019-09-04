package sayner.sandbox.neuralG.level;

import sayner.sandbox.neuralG.graphics.Shader;
import sayner.sandbox.neuralG.graphics.Texture;
import sayner.sandbox.neuralG.graphics.VertexArray;
import sayner.sandbox.neuralG.maths.impl.Matrix4f;
import sayner.sandbox.neuralG.maths.impl.Vector3f;

import java.util.Random;

/**
 * Класс отрисовки фона уровня
 */
public class Level {

    private VertexArray background;
    private Texture bgTexture;

    private Bird bird;

    private Pipe[] pipes = new Pipe[10];
    private int index = 0;

    private Random random = new Random();

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

        this.bird = new Bird();

        createPipes();
    }

    private void createPipes() {

        Pipe.create();

        for (int i = 0; i < 5 * 2; i += 2) {
            this.pipes[i] = new Pipe(index * 3.0f, this.random.nextFloat() * 4.0f);
            this.pipes[i + 1] = new Pipe(this.pipes[i].getX(), pipes[i].getY() - 13.0f);
            index += 2;
        }
    }

    public void updatePipes() {

//        this.pipes
    }

    public void update() {
        // И оно ещё должно будет двигаться
        xScroll--;
        // Map will moving left, that's why negative
        if (-xScroll % 335 == 0) {
            map++;
        }

        this.bird.update();
    }

    public void renderPipes() {

        Shader.Pipe.enable();
        Shader.Pipe.setUniformMat4f("view_matrix", Matrix4f.translate(new Vector3f(this.xScroll * 0.03f, 0.0f, 0.0f)));
        Pipe.getTexture().bind();
        Pipe.getMesh().bind();

        for (int i = 0; i < 5 * 2; i++) {

            Shader.Pipe.setUniformMat4f("ml_matrix", this.pipes[i].getMlMatrix());
            Shader.Pipe.setUniform1i("top",i%2==0?1:0);
            Pipe.getMesh().draw();
        }

        Pipe.getMesh().unbind();
        Pipe.getTexture().unbind();
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

            Shader.BackGround.setUniformMat4f("view_matrix", Matrix4f.translate(new Vector3f(i * 10 + xScroll * 0.03f, 0.0f, 0.0f)));
            // Теперь его надо "завести"
            this.background.draw();
        }

        // И убить
        this.background.unbind();
        Shader.BackGround.disable();
        this.bgTexture.unbind();

        renderPipes();
        this.bird.render();
    }
}
