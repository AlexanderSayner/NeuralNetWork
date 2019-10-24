/**
 * 
 */
package sayner.sandbox.neuralG.level;

import sayner.sandbox.neuralG.graphics.Shader;
import sayner.sandbox.neuralG.graphics.VertexArray;

/**
 * @author uarchon
 *
 */
public class AxisX {

	// Класс-инструмент, проводит все необходимые операции с вершинами
	private final VertexArray body;

	public AxisX() {

		float vertices[] = {
				 // Позиции          // Цвета
				-1.0f,  0.0035f, 0.0f,  1.0f, 0.0f, 0.1f, // Левая координата палки
				-1.0f, -0.0035f, 0.0f,  1.0f, 0.0f, 0.1f, // Левая координата палки
				 1.0f,  0.0035f, 0.0f,  0.0f, 1.0f, 0.0f, // Правая координата палки
				 1.0f, -0.0035f, 0.0f,  0.0f, 1.0f, 0.0f, // Правая координата палки
		};

		byte indices[] = { 
				// Помните, что мы начинаем с 0!
				0, 1, 2, // Первый треугольник
				1, 2, 3, // Второй треугольник
		};

		// Объект инициаплизирован
		this.body = new VertexArray(vertices, indices);
	}
	
	// Цикл отрисовки
    public void render() {

        // Надо надеяться, что эта штука инициализирована
        Shader.TriangleShader.enable();
        // Теперь его надо "завести"
        this.body.render();
        // И убить
        this.body.unbind();
        Shader.TriangleShader.disable();
    }

}
