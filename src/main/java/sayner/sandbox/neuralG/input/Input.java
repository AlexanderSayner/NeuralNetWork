package sayner.sandbox.neuralG.input;

import org.lwjgl.glfw.GLFWKeyCallbackI;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Класс будет хранить в себе мапу соответствий между клавишей и фактом её нажатия.
 * Это нужно для того, чтобы позже обратиться к ней и посмотреть: нажали ли её.
 * Как только пользователь жмёт кновку, значение обновляется на true,
 * срабатывает вреница методов, которые задействовали вот этот мой массив.
 * Когда кнопку отпускают, приходит сигнал RELEASE, заставляющий поле действия поставить в значение false.
 * Метод будет использоваться крайне часто, должен работать очень быстро
 */
public class Input implements GLFWKeyCallbackI {

    // Я не думаю, что потребуется больше памяти
    // Хороший повод для IndexOfBoundsException
    private static boolean[] keysAndActions = new boolean[1024];

    public static boolean getKeyActionStatus(int key) {

        return keysAndActions[key];
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {

        // Без излишеств: если escape, то escape
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            return; // Не имеет смысла идти дальше, если пользователь вышел
        }

        keysAndActions[key] = action != GLFW_RELEASE;
    }

    public static boolean isKeyDown(int keycode){
        return keysAndActions[keycode];
    }
}
