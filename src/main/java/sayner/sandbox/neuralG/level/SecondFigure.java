package sayner.sandbox.neuralG.level;

import sayner.sandbox.neuralG.graphics.Shader;
import sayner.sandbox.neuralG.graphics.Texture;
import sayner.sandbox.neuralG.graphics.VertexArray;

/**
 * Пусть будет для примера
 *
 * @author uarchon
 *
 */
public class SecondFigure {

    private final VertexArray body;
    private final Texture texture;

    public SecondFigure() {

        float vertices[] = {
                 // Positions         // Colors          // Texture Coords
                 0.5f,  0.5f, 0.0f,   1.0f, 0.0f, 0.0f,   1.0f, 0.0f, // Top Right
                 0.5f, -0.5f, 0.0f,   0.0f, 1.0f, 0.0f,   1.0f, 1.0f, // Bottom Right
                -0.5f, -0.5f, 0.0f,   0.0f, 0.0f, 1.0f,   0.0f, 1.0f, // Bottom Left
                -0.5f,  0.5f, 0.0f,   1.0f, 1.0f, 0.0f,   0.0f, 0.0f  // Top Left
        };

        byte indices[] = {  // Note that we start from 0!
                0, 1, 3, // First Triangle
                1, 2, 3  // Second Triangle
        };
/*

        float vertices[] = {
                // Позиции         // Цвета
                -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f,  // Нижний левый угол
                0.0f, 0.3f, 0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 0.0f,  // Верхний угол
                0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f  // Нижний правый угол
        };

        byte indices[] = {
                0, 1, 2,
        };
*/

        // Объект инициаплизирован
        this.body = new VertexArray(vertices, indices);
        this.texture = new Texture("./src/main/resources/img/avatar_TheFifthHorseman_1514332092.png");
    }

    // Цикл отрисовки
    public void render() {

        // Текстурку подкатили
        this.texture.bind();
        // Надо надеяться, что эта штука инициализирована
        Shader.TriangleShader.enable();
        // Теперь его надо "завести"
        this.body.render();
        // И убить
        this.body.unbind();
        Shader.TriangleShader.disable();
        this.texture.unbind();
    }
}
