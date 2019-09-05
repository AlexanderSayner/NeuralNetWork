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
    // is player controls the bird?
    private boolean control = true;

    private Pipe[] pipes = new Pipe[10];
    private int index = 0;

    private Random random = new Random();

    private float OFFSET = 5.0f;

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
            this.pipes[i] = new Pipe(this.OFFSET + index * 3.0f, this.random.nextFloat() * 4.0f);
            this.pipes[i + 1] = new Pipe(this.pipes[i].getX(), pipes[i].getY() - 13.0f);
            index += 2;
        }
    }

    public void updatePipes() {

        this.pipes[this.index % 10] = new Pipe(this.OFFSET + index * 3.0f, this.random.nextFloat() * 4.0f);
        this.pipes[(this.index + 1) % 10] = new Pipe(this.pipes[this.index % 10].getX(), pipes[this.index % 10].getY() - 13.0f);
        this.index += 2;
    }

    public void update() {
        if (this.control) {
            // И оно ещё должно будет двигаться
            xScroll--;
            // Map will moving left, that's why negative
            if (-xScroll % 335 == 0) {
                map++;
            }

            if (-xScroll > 250 && -xScroll % 120 == 0) {
                updatePipes();
            }
        }

        this.bird.update();

        if (this.control && collision()) {
            this.bird.fall();
            this.control = false;
        }
    }

    public void renderPipes() {

        Shader.Pipe.enable();
        Shader.Pipe.setUniformMat4f("view_matrix", Matrix4f.translate(new Vector3f(this.xScroll * 0.05f, 0.0f, 0.0f)));
        Pipe.getTexture().bind();
        Pipe.getMesh().bind();

        for (int i = 0; i < 5 * 2; i++) {

            Shader.Pipe.setUniformMat4f("ml_matrix", this.pipes[i].getMlMatrix());
            Shader.Pipe.setUniform1i("top", i % 2 == 0 ? 1 : 0);
            Pipe.getMesh().draw();
        }

        Pipe.getMesh().unbind();
        Pipe.getTexture().unbind();
    }

    private boolean collision() {

        for (int i = 0; i < 5 * 2; i++) {

            float bx = -xScroll * 0.05f;
            float by = this.bird.getY();
            float px = this.pipes[i].getX();
            float py = this.pipes[i].getY();

            float bx0 = bx - this.bird.getSize() / 2.0f;
            float bx1 = bx + this.bird.getSize() / 2.0f;
            float by0 = by - this.bird.getSize() / 2.0f;
            float by1 = by + this.bird.getSize() / 2.0f;

            float px0 = px;
            float px1 = px + Pipe.getWidth();
            float py0 = py;
            float py1 = py + Pipe.getHeight();

            if (bx1 > px0 && bx0 < px1) {
                if (by1 > py0 && by0 < py1) {
                    return true;
                }
            }
        }
        return false;
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

        for (int i = map; i < map + 4; i++) {

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
