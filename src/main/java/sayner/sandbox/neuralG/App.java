package sayner.sandbox.neuralG;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import sayner.sandbox.neuralG.graphics.Shader;
import sayner.sandbox.neuralG.input.Input;
import sayner.sandbox.neuralG.level.Level;
import sayner.sandbox.neuralG.level.Triangle;
import sayner.sandbox.neuralG.maths.impl.Matrix4f;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Hello world!
 */
public class App {

    // The window handle
    private long window;

    private Level level;
    private Triangle triangle;

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

        init();
        loop();

        // Properly de-allocate all resources once they've outlived their purpose
        glDeleteVertexArrays(1);
        glDeleteBuffers(1);

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
//        glfwSetErrorCallback(null).free();
    }

    /**
     * Инициализация всего этого добра
     */
    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
//        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
//        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizablee
        // Set OpenGL 3.0 version
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 5);
        //Установка профайла для которого создается контекст
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        // Create the window
        window = glfwCreateWindow(620, 620, "Fiction", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated
        // or released.

        glfwSetKeyCallback(window, new Input()); // Здесь могла бы быть лямбда, но лучше не надо

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {

            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        GL.createCapabilities();
    }

    /**
     * Цикл прорисовки
     */
    private void loop() {

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        int error13 = glGetError();
        if (error13 != GL_NO_ERROR) {
            System.out.println(String.format("OpenGL error code хрен знает где: %d", error13));
        }

        // Set the clear color
        glClearColor(0.85f, 0.0f, 0.3f, 0.0f);
        // Вот не надо этого :)
        glEnable(GL_DEPTH_TEST);
        System.out.println(String.format("OpenGL version %s", glGetString(GL_VERSION)));

        this.level = new Level();
        this.triangle = new Triangle();
        // Загружаем шейдеры
        Shader.loadAllShaders();

        // projection matrix
        Matrix4f prMatrix = Matrix4f.orthogonal(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f);

//        Shader.BackGround.setUniformMat4f("viewProjMatrix", prMatrix);


        int error11 = glGetError();
        if (error11 != GL_NO_ERROR) {
            System.out.println(String.format("OpenGL error code после: %d", error11));
        }

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer (every pixel to black color)

//            this.level.render();
            this.triangle.render();

            int error = glGetError();
            if (error != GL_NO_ERROR) {
                System.out.println(String.format("OpenGL error code: %d", error));
            }

            glfwSwapBuffers(window); // swap the color buffers
        }
    }

    /**
     * Отправная точка
     *
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("Launching the game");
        new App().startGame();
        System.out.println("Main finished its execution");
    }

}
