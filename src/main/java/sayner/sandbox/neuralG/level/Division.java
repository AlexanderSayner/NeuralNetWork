package sayner.sandbox.neuralG.level;

import sayner.sandbox.neuralG.graphics.LineVertexArray;
import sayner.sandbox.neuralG.graphics.Shader;

import static org.lwjgl.opengl.GL11.GL_LINES;

public class Division {

    private final LineVertexArray body;

    public Division(float primordialX, float xOffset, float primordialY, float yOffset) {
/*

        float vertices[] = new float[]{
                // Позиции                                          // Цвета
                primordialX + xOffset, primordialY + yOffset, 0.0f, 0.0f, 0.0f, 0.0f,
                primordialX - xOffset, primordialY - yOffset, 0.0f, 0.0f, 0.0f, 0.0f,
        };
*/
        float vertices[] = new float[]{
                primordialX + xOffset, primordialY + yOffset, 0.0f, 0.0f, 0.0f, 0.1f,
                primordialX - xOffset, primordialY - yOffset, 0.0f, 0.1f, 0.0f, 0.0f,
        };
        // Объект инициаплизирован
        this.body = new LineVertexArray(vertices);
    }

    // Цикл отрисовки
    public void render() {

        // Надо надеяться, что эта штука инициализирована
        Shader.TriangleShader.enable();
        // Теперь его надо "завести"
        this.body.render(GL_LINES);
        // И убить
        this.body.unbind();
        Shader.TriangleShader.disable();
    }
}
