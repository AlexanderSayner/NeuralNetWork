package sayner.sandbox.neuralG.graphics;

import sayner.sandbox.neuralG.utils.BufferUtils;

import static org.lwjgl.opengl.GL30.*;

/**
 * После инициализации объекта, для его использования требуется вызвать bind() в цикле отрисовки
 * Пока не хочу, чтобы этот класс наследовали
 */
public final class VertexArray {

    private int count; // Количество вершин

    private int vbo; // Vertex Buffer Object
    // Объект вершинного массива (VAO) может быть также привязан как и VBO и после этого все последующие вызовы вершинных атрибутов будут храниться в VAO. Преимущество этого метода в том, что нам требуется настроить атрибуты лишь единожды, а все последующие разы будет использована конфигурация VAO. Также такой метод упрощает смену вершинных данных и конфигураций атрибутов простым привязыванием различных VAO.
    private int vao; // Vertex Array Object
    private int ibo; // Index Buffer Object
    private int tbo; // Texture Buffer ObjectПосле инициализации объекта, для его использования требуется вызвать bind() в цикле отрисовки

    /**
     * Массив с вершинами несёт в себе информацию и о цвете
     * а. можно цвет напрямую в шейдере прописать
     * б. передать через uniform
     * в. здесь используется вариант передачи вместе с вершинами
     *
     * @param vertices
     */
    public VertexArray(float[] vertices, byte[] indices) {

        // Сохранить количество индексов (вершин на отрисовку), используется при вызове метода this.draw()
        this.count = indices.length;

        // Создаётся VBO
        this.vbo = glGenBuffers();

        // Илициализация VAO
        this.vao = glGenVertexArrays();
        // 1. теперь VAO будет использоваться
        glBindVertexArray(this.vao);
        // 2. Копируем наш массив вершин в буфер для OpenGL
        glBindBuffer(GL_ARRAY_BUFFER, this.vbo); // С этого момента любой вызов, использующий буфер, будет работать с VBO
        // Передаём вершины буфферу
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices), GL_STATIC_DRAW);

        // Создать буфер
        this.ibo = glGenBuffers();
        // 3. Связать его с типом буфера индексов
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createByteBuffer(indices), GL_STATIC_DRAW);

        // 4. Устанавливаем указатели на вершинные атрибуты
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * 4, 0);
        glEnableVertexAttribArray(0); // Вот тот самый индекс layout в шейдере

        // Цвет
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * 4, 3 * 4);
        glEnableVertexAttribArray(1); // Вот тот самый индекс layout в шейдере

        // Текстура
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 * 4, 3 * 2 * 4);
        glEnableVertexAttribArray(2);

        // 5. Отвязываем VAO (НЕ IBO)
        glBindVertexArray(0);
    }

    public VertexArray(float[] vertices, byte[] indices, float[] textureCoordinates) {

        // Сохранить количество индексов (вершин на отрисовку), используется при вызове метода this.draw()
        this.count = indices.length;

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

        glVertexAttribPointer(Shader.VERTEX_ATTRIBUTE_LOCATION, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(Shader.VERTEX_ATTRIBUTE_LOCATION);

        // Теперь текстуры
        this.tbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, this.tbo);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(textureCoordinates), GL_STATIC_DRAW);
        glVertexAttribPointer(Shader.TextureCOORD_ATTRIBUTE_LOCATION, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(Shader.TextureCOORD_ATTRIBUTE_LOCATION);

        // Создать буфер
        this.ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createByteBuffer(indices), GL_STATIC_DRAW);

        // Отвязываем VAO (НЕ IBO)
        glBindVertexArray(0);
    }

    /**
     * Сначала всегда нужно привязать данные к памяти
     */
    private void bind() {

        glBindVertexArray(this.vao);
    }

    /**
     * Отрисовывает, требует привязки необходимых данных в памыти
     */
    private void draw() {

//        glDrawArrays(GL_TRIANGLES, 0, 3); // Для отсивки простого треугольника
        glDrawElements(GL_TRIANGLES, this.count, GL_UNSIGNED_BYTE, 0); // Для отрисовки сложных фигур
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
