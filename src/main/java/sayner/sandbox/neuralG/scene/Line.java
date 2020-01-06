package sayner.sandbox.neuralG.scene;


import sayner.sandbox.neuralG.graphics.LineVertexArray;
import sayner.sandbox.neuralG.graphics.Shader;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.cos;

import static java.lang.Math.sin;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;


public class Line {

    private final LineVertexArray body;

    private float[] getGraph(List<Float> coordinates) {

        int size = coordinates.size();

        float vertices[] = new float[size];

        for (int i = 0; i < size; i++) {
            vertices[i] = coordinates.get(i);
        }

        return vertices;
    }

    private float[] polarSystem() {

//        float RADIUS = 0.6f;

        List<Float> verticesList = new ArrayList<>();

        for (float angle = 0.0f; angle < 2 * Math.PI; angle += (Math.PI / 1000)) {

            float x = (float) ((1 + cos(angle)) * cos(angle));
            float y = (float) ((1 + cos(angle)) * sin(angle));
            float z = 0.0f;
            float r = 1.0f;
            float g = 1.0f;
            float b = 0.1f;

            verticesList.add(x/4.0f);
            verticesList.add(y/4.0f);
            verticesList.add(z);
            verticesList.add(r);
            verticesList.add(g);
            verticesList.add(b);
        }

        return getGraph(verticesList);
    }

    public Line(float interval) {

        float vertices[] = this.polarSystem();

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
