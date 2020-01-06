package sayner.sandbox.neuralG.utils;

import static org.lwjgl.opengl.GL20.*;

/**
 * Ful static class for
 */
public class ShaderUtils {

    // Sorry, but I won't let you initialize this class
    private ShaderUtils() {

    }

    /**
     * Загружает файлы с программой
     *
     * @param vertexShaderPath
     * @param fragmentShaderPath
     * @return
     */
    public static int loadShaders(String vertexShaderPath, String fragmentShaderPath) {

        String vertex = FileUtils.loadAsString(vertexShaderPath);
        String fragment = FileUtils.loadAsString(fragmentShaderPath);

        return createShaderProgram(vertex, fragment);
    }

    /**
     * Компилирует шейдеры в контексте OpenGL
     *
     * @param vertex
     * @param fragment
     * @return
     */
    private static int createShaderProgram(String vertex, String fragment) {

        // Создание шейдера
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);

        // Внимательнее на этом месте, привязка должна быть точной
        // Передача информации о созданном шейдере и его исходном коде
        glShaderSource(vertexShader, vertex);
        glShaderSource(fragmentShader, fragment);

        // Компиляция шейдера
        glCompileShader(vertexShader);
        // Проверка успешности компиляции шейдера
        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Вершинный шейдер не скомпилировался");
            System.err.println(glGetShaderInfoLog(vertexShader));
            return -1;
        }

        glCompileShader(fragmentShader);
        if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Фрагментарный шейдер не скомпилировался");
            System.err.println(glGetShaderInfoLog(fragmentShader));
            return -1;
        }

        // Создание шейдерной программы
        int shaderProgram = glCreateProgram();

        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);

        if (glGetProgrami(shaderProgram, GL_LINK_STATUS) == GL_FALSE) {
            System.err.println("Ошибка при сборке шейдерной программы");
            System.err.println(glGetShaderInfoLog(fragmentShader));
            return -1;
        }

        // Шейдерны больше не нужны, они уже загруженны в программу
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);

        return shaderProgram;
    }

}
