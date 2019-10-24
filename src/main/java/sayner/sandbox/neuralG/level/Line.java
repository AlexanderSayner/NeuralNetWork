package sayner.sandbox.neuralG.level;


import sayner.sandbox.neuralG.graphics.LineVertexArray;
import sayner.sandbox.neuralG.graphics.Shader;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;


public class Line {

    private final LineVertexArray body;

    private float function(float x) {

        float y = x;

        return y;
    }

    private float[] getGraph() {

        float vertices[] = new float[]{
                // Позиции             // Цвета
                0.1f, function(0.1f), 0.0f, 1.0f, 1.0f, 0.1f,
                0.2f, function(0.2f), 0.0f, 1.0f, 1.0f, 0.1f,
                0.3f, function(0.3f), 0.0f, 1.0f, 1.0f, 0.1f
        };

        return vertices;
    }

    public Line() {

        float vertices[] = this.getGraph();

        // Объект инициаплизирован
        this.body = new LineVertexArray(vertices);
    }

    // Цикл отрисовки
    public void render() {

        // Надо надеяться, что эта штука инициализирована
        Shader.TriangleShader.enable();
        // Теперь его надо "завести"
        this.body.render(GL_LINE_STRIP);
        // И убить
        this.body.unbind();
        Shader.TriangleShader.disable();
    }
}
