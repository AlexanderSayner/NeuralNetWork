package sayner.sandbox.neuralG.scene;

import sayner.sandbox.neuralG.graphics.Shader;
import sayner.sandbox.neuralG.graphics.Texture;
import sayner.sandbox.neuralG.graphics.VertexArray;
import sayner.sandbox.neuralG.input.Input;
import sayner.sandbox.neuralG.maths.impl.Matrix4f;
import sayner.sandbox.neuralG.maths.impl.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Класс хранит в себье информацию отрисовкит треугольника
 */
public class Figure {

    private final VertexArray body;
    private final Texture texture;
    private Vector3f position = new Vector3f();
    private float delta = 0.0f;
    private float rotation = -this.delta * 90.0f;
    private boolean right = true;

    /**
     * Задаём в конструкторе верошины
     */
    public Figure() {

        float vertices[] = {
                // Вершины
                -0.5f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.5f, 0.5f, 0.0f,
        };

        byte indices[] = {  // Помните, что мы начинаем с 0!
                0, 1, 2,   // Первый треугольник
                0, 2, 3,   // Второй треугольник
        };

        float[] textureCoordinates = new float[]{
                0.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
        };

        // Объект инициаплизирован
        this.body = new VertexArray(vertices, indices, textureCoordinates);
        this.texture = new Texture("./src/main/resources/img/avatar_TheFifthHorseman_1514332092.png");
    }

    /**
     * изменение величины
     */
    public void fall() {

        // hardness
        // выьбор направления при отклонении более, чем на 3 градуса
        if (this.rotation > 1)
            right = true;
        else if (this.rotation < -1)
            right = false;


        float rotationSpeed = 0.001f;
        float rotationAmend = Math.abs(rotation);

        // покачивание
        if (right) {
            this.delta -= rotationSpeed + rotationAmend / 10000;//Math.abs(Math.abs(rotation)/1.0f );
        } else {
            this.delta += rotationSpeed + rotationAmend / 10000;//Math.abs(Math.abs(rotation)/1.0f );
        }
    }

    /**
     * Переммещение объекта
     */
    public void update() {

        if (Input.isKeyDown(GLFW_KEY_A)) {
            this.position.x -= 0.01f;
            this.delta += 0.02f;
        } else if (Input.isKeyDown(GLFW_KEY_D)) {
            this.position.x += 0.01f;
            this.delta -= 0.02f;
        } else {
            fall();
        }

        if (Input.isKeyDown(GLFW_KEY_S)) {
            this.position.y -= 0.01f;
        }
        if (Input.isKeyDown(GLFW_KEY_W)) {
            this.position.y += 0.01f;
        }

        this.rotation = this.delta * 90.0f;
    }

    // Цикл отрисовки
    public void render() {

        // Надо надеяться, что эта штука инициализирована
        Shader.ImageShader.enable();
        Shader.ImageShader.setUniformMat4f("multi_matrix", Matrix4f.translate(this.position).multiply(Matrix4f.rotate(this.rotation)));
        this.texture.bind();
        // Теперь его надо "завести"
        this.body.render();
        // И убить
        this.body.unbind();
        this.texture.unbind();
        Shader.ImageShader.disable();
    }
}
