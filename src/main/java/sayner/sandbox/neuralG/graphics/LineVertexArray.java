package sayner.sandbox.neuralG.graphics;

import sayner.sandbox.neuralG.utils.BufferUtils;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

<<<<<<< HEAD
/**
 * Класс аналогичен VertexArray, только предназначен ля отрисовки простых линий
 */
public class LineVertexArray {

    private int count; // Количество вершин

    private int vbo; // Vertex Buffer Object
    // Объект вершинного массива (VAO) может быть также привязан как и VBO и после этого все последующие вызовы вершинных атрибутов будут храниться в VAO. Преимущество этого метода в том, что нам требуется настроить атрибуты лишь единожды, а все последующие разы будет использована конфигурация VAO. Также такой метод упрощает смену вершинных данных и конфигураций атрибутов простым привязыванием различных VAO.
=======
public class LineVertexArray {

    private int count; // Length of the array with vertices
    private int vbo; // This will identify our vertex buffer
>>>>>>> Рисульки
    private int vao; // Vertex Array Object

    public LineVertexArray(float[] vertices) {

<<<<<<< HEAD
        this.count=vertices.length/6;

=======
>>>>>>> Рисульки
        // Создаётся VBO
        this.vbo = glGenBuffers();

        // Илициализация VAO
        this.vao = glGenVertexArrays();
<<<<<<< HEAD
        // 1. теперь VAO будет использоваться
        glBindVertexArray(this.vao);
        // 2. Копируем наш массив вершин в буфер для OpenGL
=======
        // теперь VAO будет использоваться
        glBindVertexArray(this.vao);
        // Копируем наш массив вершин в буфер для OpenGL
>>>>>>> Рисульки
        glBindBuffer(GL_ARRAY_BUFFER, this.vbo); // С этого момента любой вызов, использующий буфер, будет работать с VBO
        // Передаём вершины буфферу
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices), GL_STATIC_DRAW);

<<<<<<< HEAD
        // 4. Устанавливаем указатели на вершинные атрибуты
=======
        // Устанавливаем указатели на вершинные атрибуты
>>>>>>> Рисульки
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * 4, 0);
        glEnableVertexAttribArray(0); // Вот тот самый индекс layout в шейдере

        // Цвет
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * 4, 3 * 4);
        glEnableVertexAttribArray(1); // Вот тот самый индекс layout в шейдере

<<<<<<< HEAD
        // 5. Отвязываем VAO (НЕ IBO)
        glBindVertexArray(0);
=======
        // Отвязываем VAO
        glBindVertexArray(0);

>>>>>>> Рисульки
    }

    /**
     * Сначала всегда нужно привязать данные к памяти
     */
    public void bind() {

        glBindVertexArray(this.vao);
<<<<<<< HEAD
=======

>>>>>>> Рисульки
    }

    /**
     * Отрисовывает, требует привязки необходимых данных в памыти
     */
<<<<<<< HEAD
    public void draw(int primitive) {

        glDrawArrays(primitive, 0, this.count);
=======
    public void draw() {

        glDrawArrays(GL_LINES, 0, 2);
>>>>>>> Рисульки
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
<<<<<<< HEAD
    public void render(int primitive) {

        bind();
        draw(primitive);
=======
    public void render() {

        bind();
        draw();
>>>>>>> Рисульки
        unbind();
    }
}
