package sayner.sandbox.neuralG.engine;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import sayner.sandbox.neuralG.input.Input;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Управление инициолизацией окна игры
 */
public class Window {

    private final String title;
    private final int width;
    private final int height;
    private final boolean resized;
    private final boolean vSync;

    // The window handle
    private long window;

    /**
     * Настройки "по-умолчанию"
     */
    public Window() {
        this("Fiction");
    }

    public Window(String title) {
        this(title, 620, 620);
    }

    public Window(String title, int width, int height) {
        this(title, width, height, false, true);
    }

    public Window(String title, int width, int height, boolean resized, boolean vSync) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.resized = resized;
        this.vSync = vSync;
    }

    // ====================
    // Core function
    // ====================

    /**
     * Открывает окно
     */
    public void init() {
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
        // Set OpenGL 4.0 version
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
        //Установка профайла для которого создается контекст
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        // Create the window
        window = glfwCreateWindow(width, height, title, NULL, NULL);
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
            GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            assert videoMode != null;
            glfwSetWindowPos(window, (videoMode.width() - pWidth.get(0)) / 2, (videoMode.height() - pHeight.get(0)) / 2);
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        GL.createCapabilities();

//        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE); // Режим Wireframe
    }

    public void destroy(){
        // Free the window callbacks and destroy the window
        Callbacks.glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
    }

    // ====================
    // Functionality
    // ====================

    /**
     * Фвет фона экрана
     * @param r red
     * @param g green
     * @param b blue
     * @param alpha a
     */
    public void setClearColor(float r, float g, float b, float alpha) {
        glClearColor(r, g, b, alpha);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    /**
     * Вызывается каждый кадр в цикле отрисовки
     */
    public void update() {

        glfwSwapBuffers(window); // swap the color buffers

        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
    }

    /**
     * Используется при передвижении камеры
     * @param keyCode GLFW_KEY_? @see {@link org.lwjgl.glfw.GLFW}
     * @return статус нажатия данной клавиши
     */
    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(window, keyCode) == GLFW_PRESS;
    }

    // ====================
    // Getter'ы & Setter'ы
    // ====================

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isResized() {
        return resized;
    }

    public boolean isvSync() {
        return vSync;
    }

    public long getWindowHandle() {
        return window;
    }
}
