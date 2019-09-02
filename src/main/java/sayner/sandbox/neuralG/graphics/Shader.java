package sayner.sandbox.neuralG.graphics;

import sayner.sandbox.neuralG.maths.impl.Matrix4f;
import sayner.sandbox.neuralG.maths.impl.Vector3f;
import sayner.sandbox.neuralG.maths.impl.Vector4f;
import sayner.sandbox.neuralG.utils.ShaderUtils;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL30.*;

/**
 * Этот класс позволяет избежать прямого вызова функций OpenGL
 * Uniform нужны для того, чтобы находить данные в шейдерной программе и передавать их CPU
 */
public class Shader {

    // Для удобопонимая
    public static final int VERTEX_ATTRIBUTE_LOCATION = 0;
    public static final int TextureCOORD_ATTRIBUTE_LOCATION = 1;

    // Перечислю все шейдеры, которые используются программой
    public static Shader BackGround;
    public static Shader TriangleShader;

    private boolean enabled = false;

    // id шейдерной программы, созданной в контексте OpenGL
    private final int ID;
    // Маппа нужна для того, чтобы не спрашивать снова и снова каждый кадр местоположение переменных
    private Map<String, Integer> locationCache = new HashMap<>();

    /**
     * Создаёт шейдерную программу, принимая на вход путь до файла с
     *
     * @param vertex   программой вершинного шейдера
     * @param fragment программой фрагментарного шейдера
     */
    public Shader(String vertex, String fragment) {

        ID = ShaderUtils.loadShaders(vertex, fragment);
    }

    /**
     * Инициализируются все шейдеры в программе
     */
    public static void loadAllShaders() {

        BackGround = new Shader("./src/main/resources/shader/bg_vert.glsl", "./src/main/resources/shader/bg_frag.glsl");
        TriangleShader = new Shader("./src/main/resources/shader/triangle_vert.glsl", "./src/main/resources/shader/triangle_frag.glsl");
    }

    /**
     * Получить значение переменной из текущей шейдерной программы
     *
     * @param name
     * @return
     */
    public int getUniform(String name) {

        if (this.locationCache.containsKey(name)) {
            return this.locationCache.get(name);
        }

        // Location but the value
        int result = glGetUniformLocation(ID, name);

        if (result == -1) {
            System.err.println(String.format("Не нашлась uniform переменная %s!", name));
        } else {
            this.locationCache.put(name, result);
        }

        return result;
    }

    /**
     * Задать значение переменной в текущей шейдерной программе
     *
     * @param name
     * @param value
     */
    public void setUniform1i(String name, int value) {

        if (!this.enabled) enable();
        glUniform1i(getUniform(name), value);
    }

    public void setUniform1f(String name, float value) {

        if (!this.enabled) enable();
        glUniform1f(getUniform(name), value);
    }

    public void setUniform2f(String name, float x, float y) {

        if (!this.enabled) enable();
        glUniform2f(getUniform(name), x, y);
    }

    public void setUniform3f(String name, Vector3f vector) {

        if (!this.enabled) enable();
        glUniform3f(getUniform(name), vector.X(), vector.Y(), vector.Z());
    }

    public void setUniform4f(String name, Vector4f vector) {

        if (!this.enabled) enable();
        glUniform4f(getUniform(name), vector.X(), vector.Y(), vector.Z(), vector.A());
    }

    public void setUniformMat4f(String name, Matrix4f matrix) {

        if (!this.enabled) enable();
        glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
    }

    public void enable() {

        glUseProgram(ID);
        this.enabled = true;
    }

    public void disable() {

        glUseProgram(0);
        this.enabled = false;
    }

}
