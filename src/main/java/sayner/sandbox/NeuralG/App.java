package sayner.sandbox.NeuralG;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import sayner.sandbox.NeuralG.Neurons.NeuralNet;
import sayner.sandbox.NeuralG.Textures.Texture;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Hello world!
 *
 */
public class App {

	// The window handle
	private long window;

	/**
	 * Запуск и освобождение памяти
	 */
	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	/**
	 * Инициализация всего этого добра
	 */
	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(620, 620, "Hello World!", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated
		// or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

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
	}

	/**
	 * Цикл прорисовки
	 */
	private void loop() {

		int x1 = 0;
		int x2 = 0;
		int player_selected = 2;

		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Don't forget to do it
		glEnable(GL_TEXTURE_2D);

		// Creating texture under the OpenGL context
		Texture green_texture = new Texture("./src/main/resources/img/Neuron.png");
		Texture activated_texture = new Texture("./src/main/resources/img/ActivatedNeuron.png");
		Texture input_one_texture = new Texture("./src/main/resources/img/one.png");
		Texture input_zero_texture = new Texture("./src/main/resources/img/zero.png");
		Texture selected_input_one_texture = new Texture("./src/main/resources/img/selected_one.png");
		Texture selected_input_zero_texture = new Texture("./src/main/resources/img/selecteed_zero.png");
		// Texture selected_input_one_texture = new
		// Texture("./src/resources/Neuron.png");
		// Texture selected_input_zero_texture = new
		// Texture("./src/resources/Neuron.png");

		// Set the clear color
		glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

		float x = 0.3f;
		float y = 0.0f;/*
						 * float red = 0.0f; float green = 0.0f; float blue = 0.0f;
						 */

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while (!glfwWindowShouldClose(window)) {

			// Starting NeuralNet
			NeuralNet neuralNet = new NeuralNet(x1, x2);
			int result = neuralNet.start();

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer (every pixel to black color)

			glLineWidth(5);

			glBegin(GL_LINES);

				glVertex2d(-0.9f + x, 0.4f + y);
				glVertex2d(-0.6f + x, 0.4f + y);
	
				glVertex2d(-0.9f + x, -0.4f + y);
				glVertex2d(-0.6f + x, -0.4f + y);
	
				glVertex2d(-0.9f + x, 0.4f + y);
				glVertex2d(-0.6f + x, -0.4f + y);
	
				glVertex2d(-0.9f + x, -0.4f + y);
				glVertex2d(-0.6f + x, 0.4f + y);
	
				glVertex2d(-0.6f + x, 0.4f + y); // первая линия
				glVertex2d(0 + x, 0 + y);
	
				glVertex2d(-0.6f + x, -0.4f + y); // вторая линия
				glVertex2d(0 + x, 0 + y);
	
				glVertex2d(0.3f + x, 0f + y);
				glVertex2d(0 + x, 0 + y);

			glEnd();

			// Now. we drowing a texture
			if (neuralNet.get_one() == 0) {
				green_texture.bind();
			} else {
				activated_texture.bind();
			}
			// firts up neuron
			glBegin(GL_QUADS);

				// left up
				glTexCoord2f(0, 0);
				// glColor4f(1f, 0.1f, 0.1f, 0f);
				glVertex2f(-0.65f + x, 0.5f + y);
	
				// right up
				glTexCoord2f(1, 0);
				glVertex2f(-0.50f + x, 0.5f + y);
	
				// right down
				glTexCoord2f(1, 1);
				glVertex2f(-0.50f + x, 0.35f + y);
	
				// left down
				glTexCoord2f(0, 1);
				glVertex2f(-0.65f + x, 0.35f + y);

			glEnd();

			// firts down neuron
			if (neuralNet.get_two() == 0) {
				green_texture.bind();
			} else {
				activated_texture.bind();
			}
			glBegin(GL_QUADS);

				// left up
				glTexCoord2f(0, 0);
				// glColor4f(1f, 0.1f, 0.1f, 0f);
				glVertex2f(-0.65f + x, -0.5f + y);
	
				// right up
				glTexCoord2f(1, 0);
				glVertex2f(-0.50f + x, -0.5f + y);
	
				// right down
				glTexCoord2f(1, 1);
				glVertex2f(-0.50f + x, -0.35f + y);
	
				// left down
				glTexCoord2f(0, 1);
				glVertex2f(-0.65f + x, -0.35f + y);

			glEnd();

			// second neuron
			if (result == 0) {
				green_texture.bind();
			} else {
				activated_texture.bind();
			}
			glBegin(GL_QUADS);
	
				// left up
				glTexCoord2f(0, 0);
				// glColor4f(0.5f, 0.1f, 0.1f, 0f);
				glVertex2f(-0.05f + x, 0.08f + y);
	
				// right up
				glTexCoord2f(1, 0);
				glVertex2f(0.1f + x, 0.08f + y);
	
				// right down
				glTexCoord2f(1, 1);
				glVertex2f(0.1f + x, -0.07f + y);
	
				// left down
				glTexCoord2f(0, 1);
				glVertex2f(-0.05f + x, -0.07f + y);

			glEnd();

			if (x1 == 1 && player_selected == 1) {

				selected_input_one_texture.bind();
			} else if (x1 == 1 && player_selected == 2) {
				input_one_texture.bind();
			} else if (x1 == 0 && player_selected == 1) {

				selected_input_zero_texture.bind();
			} else if (x1 == 0 && player_selected == 2) {

				input_zero_texture.bind();
			}

			glBegin(GL_QUADS);

				// left up
				glTexCoord2f(0, 0);
				// glColor4f(1f, 0.1f, 0.1f, 0f);
				glVertex2f(-0.95f + x, 0.5f + y);
	
				// right up
				glTexCoord2f(1, 0);
				glVertex2f(-0.80f + x, 0.5f + y);
	
				// right down
				glTexCoord2f(1, 1);
				glVertex2f(-0.80f + x, 0.35f + y);
	
				// left down
				glTexCoord2f(0, 1);
				glVertex2f(-0.95f + x, 0.35f + y);

			glEnd();

			if (x2 == 1 && player_selected == 2) {

				selected_input_one_texture.bind();
			} else if (x2 == 1 && player_selected == 1) {
				input_one_texture.bind();
			} else if (x2 == 0 && player_selected == 2) {

				selected_input_zero_texture.bind();
			} else if (x2 == 0 && player_selected == 1) {
				input_zero_texture.bind();
			}

			glBegin(GL_QUADS);

				// left up
				glTexCoord2f(0, 0);
				// glColor4f(1f, 0.1f, 0.1f, 0f);
				glVertex2f(-0.95f + x, -0.35f + y);
	
				// right up
				glTexCoord2f(1, 0);
				glVertex2f(-0.80f + x, -0.35f + y);
	
				// right down
				glTexCoord2f(1, 1);
				glVertex2f(-0.80f + x, -0.5f + y);
	
				// left down
				glTexCoord2f(0, 1);
				glVertex2f(-0.95f + x, -0.5f + y);

			glEnd();

			// out
			if (result == 1) {
				input_one_texture.bind();
			} else {
				input_zero_texture.bind();
			}
			glBegin(GL_QUADS);

				// left up
				glTexCoord2f(0, 0);
				// glColor4f(0.5f, 0.1f, 0.1f, 0f);
				glVertex2f(0.25f + x, 0.08f + y);
	
				// right up
				glTexCoord2f(1, 0);
				glVertex2f(0.40f + x, 0.08f + y);
	
				// right down
				glTexCoord2f(1, 1);
				glVertex2f(0.4f + x, -0.07f + y);
	
				// left down
				glTexCoord2f(0, 1);
				glVertex2f(0.25f + x, -0.07f + y);

			glEnd();

			glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();

			// ESCAPE
			if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GL_TRUE) {

				glfwSetWindowShouldClose(window, true);
				break;
			}

			// MOVING
			if (glfwGetKey(window, GLFW_KEY_D) == GL_TRUE) {

				x += 0.01f;
			}

			if (glfwGetKey(window, GLFW_KEY_A) == GL_TRUE) {

				x -= 0.01f;
			}

			if (glfwGetKey(window, GLFW_KEY_W) == GL_TRUE) {

				if (player_selected == 2) {
					player_selected = 1;
				}
			}

			if (glfwGetKey(window, GLFW_KEY_S) == GL_TRUE) {

				if (player_selected == 1) {
					player_selected = 2;
				}
			}

			if (glfwGetKey(window, GLFW_KEY_SPACE) == GL_TRUE) {

				if (player_selected == 1) {
					if (x1 == 0)
						x1 = 1;
					else
						x1 = 0;
				} else if (x2 == 0)
					x2 = 1;
				else
					x2 = 0;
			}

			/*
			 * // Mouse 0 - left, 1 - right, 2 - scroll if(glfwGetMouseButton(window, 0) ==
			 * GL_TRUE) {
			 * 
			 * red = 1f; }
			 */
		}
	}

	/**
	 * Отправная точка
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new App().run();
	}

}
