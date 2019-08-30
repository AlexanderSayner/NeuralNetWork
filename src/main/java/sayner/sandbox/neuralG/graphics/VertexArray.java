package sayner.sandbox.neuralG.graphics;

import sayner.sandbox.neuralG.utils.BufferUtils;

import static org.lwjgl.opengl.GL30.*;

/**
 * После инициализации объекта, для его использования требуется вызвать bind() в цикле отрисовки
 */
public class VertexArray {

    private int count; // Количество вершин

    private int vbo; // Vertex Buffer Object
    // Объект вершинного массива (VAO) может быть также привязан как и VBO и после этого все последующие вызовы вершинных атрибутов будут храниться в VAO. Преимущество этого метода в том, что нам требуется настроить атрибуты лишь единожды, а все последующие разы будет использована конфигурация VAO. Также такой метод упрощает смену вершинных данных и конфигураций атрибутов простым привязыванием различных VAO.
    private int vao; // Vertex Array Object
    private int ibo; // Index Buffer Object
    private int tbo; // Texture Buffer ObjectПосле инициализации объекта, для его использования требуется вызвать bind() в цикле отрисовки

    /**
     * only vertex array in use
     *
     * @param vertices
     */
    public VertexArray(float[] vertices) {

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
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        // Отвязываем VAO
        glBindVertexArray(0);
    }

    public VertexArray(float[] vertices, byte[] indices, float[] textureCoordinates) {

        this.count = indices.length;

        // Create and bind vertex array object
        this.vao = glGenVertexArrays();
        glBindVertexArray(this.vao);

        // Create and bind vertex buffer object
        this.vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, this.vbo);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices), GL_STATIC_DRAW);
        // x,y,z - that is why 3
        glVertexAttribPointer(Shader.VERTEX_ATTRIBUTE_LOCATION, 3, GL_FLOAT, false, 0, 0); // Every 3 components it goes x coordinate
        glEnableVertexAttribArray(Shader.VERTEX_ATTRIBUTE_LOCATION);

        // Create and bind texture buffer object
        this.tbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, this.tbo);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(textureCoordinates), GL_STATIC_DRAW);
        glVertexAttribPointer(Shader.TextureCOORD_ATTRIBUTE_LOCATION, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(Shader.TextureCOORD_ATTRIBUTE_LOCATION);

        // Create and bind index buffer object
        this.ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createByteBuffer(indices), GL_STATIC_DRAW);

        // Now it can be unbind
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void bind() {

        glBindVertexArray(this.vao);
//        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ibo); // Это слишком сложно
    }

    public void draw() {

//        glDrawElements(GL_TRIANGLES, this.count, GL_UNSIGNED_BYTE, 0); // Сложно
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }

    public void unbind() {

        // Отвязываем VAO
        glBindVertexArray(0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void render() {

        bind();
        draw();
    }
}
