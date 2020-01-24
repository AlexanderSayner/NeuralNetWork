package sayner.sandbox.neuralG;

import org.lwjgl.Version;
import org.lwjgl.opengl.GL;
import sayner.sandbox.neuralG.engine.Window;
import sayner.sandbox.neuralG.graphics.Shader;
import sayner.sandbox.neuralG.graphics.Transformation;
import sayner.sandbox.neuralG.maths.impl.Matrix4f;
import sayner.sandbox.neuralG.scene.Camera;
import sayner.sandbox.neuralG.scene.meshes.Figure;

import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.GL_MAX_VERTEX_ATTRIBS;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

/**
 * Hello world!
 */
public class App {

    private Window window = new Window();
    /**
     * Field of View in Radians
     */
    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.f;
    private Matrix4f projectionMatrix;

    private final Camera camera = new Camera();

    private Figure figure;

    /**
     * Start in the new thread
     */
    public void startGame() {

        // Сделано для того, чтобы OpenGL запускался в другом потоке (мультизадачность)
        Thread thread = new Thread(this::run);
        thread.start();
    }

    /**
     * Запуск и освобождение памяти
     */
    public void run() {

        System.out.println(String.format("Hello LWJGL %s!", Version.getVersion()));

        // Функция инициализации всего, что будет необходимо OpenGL
        window.init();
        // Цикл отрисовки
        loop();

        // Properly de-allocate all resources once they've outlived their purpose
        glDeleteVertexArrays(1);
        glDeleteBuffers(1);

        window.destroy();

        // Terminate GLFW and free the error callback
        glfwTerminate();
    }

    private void setUpLoop() {

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        int error121 = glGetError();
        if (error121 != GL_NO_ERROR) {
            System.out.println(String.format("OpenGL error code при инициализации 1: %d", error121));
        }

        // Set the clear color
        glClearColor(0.85f, 0.0f, 0.6f, 0.0f);
        // Вот не надо этого :)
        glEnable(GL_DEPTH_TEST);
        // So if you use a modern opengl which has custom shaders, its option won't work ,and you won't need it.
        // glEnable(GL_TEXTURE_2D);

        glActiveTexture(GL_TEXTURE1); // Где прописан номер текстуры? Когда её автирировать?
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        int error1231 = glGetError();
        if (error1231 != GL_NO_ERROR) {
            System.out.println(String.format("OpenGL error code при инициализации 2: %d", error1231));
        }

        System.out.println(String.format("OpenGL version %s", glGetString(GL_VERSION)));

        System.out.println(String.format("Максимальное количество 4-х компонентных вершин, которое можно передать видеокарте - %d", glGetInteger(GL_MAX_VERTEX_ATTRIBS)));

    }

    /**
     * Цикл прорисовки
     */
    private void loop() {

        setUpLoop();

        this.figure = new Figure();
        // Загружаем шейдеры
        Shader.loadAllShaders();

        Transformation transformation = new Transformation();

        // Вот это вот всё добро уходит в шейдеры
        Shader.ImageShader.setUniform1i("ourTexture", 1);
//        Shader.ImageShader.setUniform1f("scale", 1.0f);

        int error11 = glGetError();
        if (error11 != GL_NO_ERROR) {
            System.out.println(String.format("OpenGL error code при инициализации 3: %d", error11));
        }

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!window.shouldClose()) {

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer (every pixel to black color)

            // Update projection Matrix
            this.projectionMatrix = new Matrix4f(transformation.getProjectionMatrix(FOV, 620, 620, Z_NEAR, Z_FAR));
            Matrix4f matrix4f = new Matrix4f();
            Shader.ImageShader.setUniformMat4f("projectionMatrix", this.projectionMatrix);

            this.figure.update(transformation, camera);
            this.figure.render();

            int error = glGetError();
            if (error != GL_NO_ERROR) {
                System.out.println(String.format("OpenGL error code: %d", error));
            }

            window.update();
        }
    }

    /**
     * Отправная точка
     *
     * @param args они пока не учитываеются
     */
    public static void main(String[] args) {
        System.out.println("Launching the game");
        new App().startGame();
        System.out.println("Main finished its execution");
    }

}
