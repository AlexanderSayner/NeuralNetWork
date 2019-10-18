package sayner.sandbox.neuralG.graphics;

import sayner.sandbox.neuralG.utils.BufferUtils;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LineVertexArray {

    private int count; // Length of the array with vertices
    private int vbo; // This will identify our vertex buffer
    private int vao; // Vertex Array Object

    public LineVertexArray(float[] vertices) {

        // Создаётся VBO
        this.vbo = glGenBuffers();

        // Илициализация VAO
        this.vao = glGenVertexArrays();
        // теперь VAO будет использоваться
        glBindVertexArray(this.vao);
        // Копируем наш массив вершин в буфер для OpenGL
        glBindBuffer(GL_ARRAY_BUFFER, this.vbo); // С этого момента любой вызов, использующий буфер, будет работать с VBO
        // Передаём вершины буфферу
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices), GL_STATIC_DRAW);

        // Устанавливаем указатели на вершинные атрибуты
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * 4, 0);
        glEnableVertexAttribArray(0); // Вот тот самый индекс layout в шейдере

        // Цвет
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * 4, 3 * 4);
        glEnableVertexAttribArray(1); // Вот тот самый индекс layout в шейдере

        // Отвязываем VAO
        glBindVertexArray(0);

    }

    /**
     * Сначала всегда нужно привязать данные к памяти
     */
    public void bind() {

        glBindVertexArray(this.vao);

    }

    /**
     * Отрисовывает, требует привязки необходимых данных в памыти
     */
    public void draw() {

        glDrawArrays(GL_LINES, 0, 2);
    }

    /**
     * Отвязать всё, что использовалось
     */
    public void unbind() {

        glBindVertexArray(0);
    }

    /**
     * Функция объеденяет в себе привязку данных и их отрисовку. неоходимо атем отвязать данные
     */
    public void render() {

        bind();
        draw();
        unbind();
    }
}
