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

        int program = glCreateProgram();

        int vertexId = glCreateShader(GL_VERTEX_SHADER);
        int fragmentId = glCreateShader(GL_FRAGMENT_SHADER);

        // Внимательнее на этом месте, привязка должна быть точной
        glShaderSource(vertexId, vertex);
        glShaderSource(fragmentId, fragment);

        glCompileShader(vertexId);
        // Проверка успешности компиляции шейдера
        if (glGetShaderi(vertexId, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Вершинный шейдер не скомпилировался");
            System.err.println(glGetShaderInfoLog(vertexId));
            return -1;
        }

        glCompileShader(fragmentId);
        if (glGetShaderi(fragmentId, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Фрагментарный шейдер не скомпилировался");
            System.err.println(glGetShaderInfoLog(fragmentId));
            return -1;
        }

        glAttachShader(program, vertexId);
        glAttachShader(program, fragmentId);
        glLinkProgram(program);
//        glValidateProgram(program);

        // Шейдерны больше не нужны, они уже загруженны в программу
        glDeleteShader(vertexId);
        glDeleteShader(fragmentId);

        return program;
    }

}
