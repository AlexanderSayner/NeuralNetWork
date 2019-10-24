package sayner.sandbox.neuralG.level;


import sayner.sandbox.neuralG.graphics.LineVertexArray;
import sayner.sandbox.neuralG.graphics.Shader;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;


public class Line {

    private final LineVertexArray body;

    private float function(float x, float radius) {

        float y = (float) Math.sqrt(radius * radius - x * x);

        return y;
    }

    private float negativeFunction(float x, float radius) {

        float y = -(float) Math.sqrt(radius * radius - x * x);

        return y;
    }

    private List<Float> calculateGraph(float interval) {

        List<Float> coordinates = new ArrayList<>();

        float RADIUS = 0.64f;

        for (float x = -1.0f; x <= 1.0f; x += interval) {

            if (RADIUS * RADIUS - x * x < 0.0f)
                continue;

            coordinates.add(x); // X
            coordinates.add(function(x, RADIUS)); // Y
            coordinates.add(0.0f); // Z
            coordinates.add(1.0f); // r
            coordinates.add(1.0f); // g
            coordinates.add(0.1f); // b
        }

        // а теперь с другого конца
        for (float x = 1.0f; x >= -1.0f; x -= interval){

            if (RADIUS * RADIUS - x * x < 0.0f)
                continue;

            coordinates.add(x); // X
            coordinates.add(negativeFunction(x, RADIUS)); // Y
            coordinates.add(0.0f); // Z
            coordinates.add(1.0f); // r
            coordinates.add(1.0f); // g
            coordinates.add(0.1f); // b
        }

            return coordinates;
    }

    private float[] getGraph(List<Float> coordinates) {

/*
        float vertices[] = new float[]{
                // Позиции             // Цвета
                0.1f, function(0.1f), 0.0f, 1.0f, 1.0f, 0.1f,
                -0.2f, function(-0.2f), 0.0f, 1.0f, 1.0f, 0.1f,
                0.3f, function(0.3f), 0.0f, 1.0f, 1.0f, 0.1f
        };

*/

        int size = coordinates.size();

        float vertices[] = new float[size];

        for (int i = 0; i < size; i++) {
            vertices[i] = coordinates.get(i);
        }

        return vertices;
    }

    public Line(float interval) {

        float vertices[] = this.getGraph(calculateGraph(interval));

        // Объект инициаплизирован
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
