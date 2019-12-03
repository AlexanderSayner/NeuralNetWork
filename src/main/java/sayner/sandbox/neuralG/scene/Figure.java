package sayner.sandbox.neuralG.scene;

import sayner.sandbox.neuralG.graphics.Shader;
import sayner.sandbox.neuralG.graphics.Texture;
import sayner.sandbox.neuralG.graphics.VertexArray;

/**
 * Класс хранит в себье информацию отрисовкит треугольника
 */
public class Figure {

    private final VertexArray body;
    private final Texture texture;

    /**
     * Задаём в конструкторе верошины
     */
    public Figure() {

        float vertices[] = {
                // Позиции
                -0.5f,  0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                 0.5f, -0.5f, 0.0f,
                 0.5f,  0.5f, 0.0f,
        };

        byte indices[] = {  // Помните, что мы начинаем с 0!
                0, 1, 2,   // Первый треугольник
                0, 2, 3,   // Второй треугольник
        };

//        float[] textureCoordinates = new float[]{
//                0.0f, 1.0f,
//                0.0f, 0.0f,
//                1.0f, 0.0f,
//                1.0f, 1.0f,
//        };

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

    // Цикл отрисовки
    public void render() {

        // Надо надеяться, что эта штука инициализирована
        Shader.ImageShader.enable();
        this.texture.bind();
        // Теперь его надо "завести"
        this.body.render();
        // И убить
        this.body.unbind();
        this.texture.unbind();
        Shader.ImageShader.disable();
    }
}
