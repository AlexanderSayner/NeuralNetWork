package sayner.sandbox.neuralG.level;

import sayner.sandbox.neuralG.graphics.Shader;
import sayner.sandbox.neuralG.graphics.VertexArray;

/**
 * Класс хранит в себье информацию отрисовкит треугольника
 */
public class Figure {

    private final VertexArray body;

    /**
     * Задаём в конструкторе верошины
     */
    public Figure() {

        float vertices[] = {
                 // Позиции         // Цвета
                -0.5f, -0.5f,  0.0f, 1.0f, 0.0f, 0.0f,   // Нижний левый угол
                 0.0f,  0.3f,  0.0f, 1.0f, 1.0f, 0.0f,   // Верхний угол
                 0.5f, -0.5f,  0.0f, 1.0f, 0.0f, 1.0f,   // Нижний правый угол
                 0.0f,  0.0f,  0.3f, 0.8f, 0.0f, 0.3f    // Вершина пирамидки
        };

        byte indices[] = {  // Помните, что мы начинаем с 0!
                0, 1, 2,   // Первый треугольник
                0, 1, 3,   // Второй треугольник
                1, 2, 3,   // Третий треугольник
                2, 0, 3    // Последний штрих
        };

        // Объект инициаплизирован
        this.body = new VertexArray(vertices, indices);
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
