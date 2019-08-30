package sayner.sandbox.neuralG.level;

import sayner.sandbox.neuralG.graphics.Shader;
import sayner.sandbox.neuralG.graphics.VertexArray;

/**
 * Класс хранит в себье информацию отрисовкит треугольника
 */
public class Triangle {

    private final VertexArray body;

    /**
     * Задаём в конструкторе верошины
     */
    public Triangle() {

        float vertices[] = {
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f, 0.5f, 0.0f
        };

        // Объект инициаплизирован
        this.body = new VertexArray(vertices);
    }

    // Цикл отрисовки
    public void render() {

        // Надо надеяться, что эта штука инициализирована
        Shader.TriangleShader.enable();
        // Теперь его надо "завести"
        this.body.render();
        // И убить
        this.body.unbind();
        Shader.TriangleShader.disable();
    }
}
