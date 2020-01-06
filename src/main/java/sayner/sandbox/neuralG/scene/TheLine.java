package sayner.sandbox.neuralG.scene;

import sayner.sandbox.neuralG.graphics.LineVertexArray;
import sayner.sandbox.neuralG.graphics.Shader;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;

public class TheLine {

    private final LineVertexArray body;

    private float[] calculateVertices(){

        List<Float> points = new ArrayList<>();

        float vertices[] = {
                // Позиции          // Цвета
                -1.0f, -1.0f, 0.0f, 1.0f, 0.0f, 0.1f, // Левая координата палки
                0.0f,  1.0f, 0.0f, 0.0f, 1.0f, 0.0f, // Правая координата палки
        };

        return vertices;
    };

    public TheLine() {

        float vertices[] = calculateVertices();

        this.body = new LineVertexArray(vertices);
    }

    // Цикл отрисовки
    public void render() {

        // Надо надеяться, что эта штука инициализирована
        Shader.TriangleShader.enable();
        // Теперь его надо "завести"
        this.body.render(GL_LINE_LOOP);
        // И убить
        this.body.unbind();
        Shader.TriangleShader.disable();
    }
}
