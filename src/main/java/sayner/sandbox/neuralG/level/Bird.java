package sayner.sandbox.neuralG.level;

import static org.lwjgl.glfw.GLFW.*;

import sayner.sandbox.neuralG.graphics.Shader;
import sayner.sandbox.neuralG.graphics.Texture;
import sayner.sandbox.neuralG.graphics.VertexArray;
import sayner.sandbox.neuralG.input.Input;
import sayner.sandbox.neuralG.maths.impl.Matrix4f;
import sayner.sandbox.neuralG.maths.impl.Vector3f;

public class Bird {

    private float SIZE = 1.0f;
    private final VertexArray mesh;
    private final Texture texture;

    private Vector3f position = new Vector3f();
    private float rotation;
    private float delta = 0.0f;

    /**
     * Создание птички
     */
    public Bird() {

        // Let's go
        float[] vertices = new float[]{
                -SIZE / 2.0f, -SIZE / 2.0f, 0.1f,
                -SIZE / 2.0f, SIZE / 2.0f, 0.1f,
                SIZE / 2.0f, SIZE / 2.0f, 0.1f,
                SIZE / 2.0f, -SIZE / 2.0f, 0.1f,
        };

        byte[] indices = new byte[]{
                0, 1, 2,
                2, 3, 0
        };

        float[] textureCoordinates = new float[]{
                0.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
        };

        this.mesh = new VertexArray(vertices, indices, textureCoordinates);
        this.texture = new Texture("./src/main/resources/img/bird.png");
    }

    public void update() {

        this.position.y -= this.delta;
        if (Input.isKeyDown(GLFW_KEY_SPACE)) {
            fall();
        } else {
            this.delta += 0.01f;
        }

        this.rotation = -this.delta * 90.0f;
    }

    private void fall() {

        this.delta -= 0.15f;
    }

    /**
     * Цикл отрисовки птицы
     */
    public void render() {

        Shader.Bird.enable();
        Shader.Bird.setUniformMat4f("mult_matrix", Matrix4f.translate(this.position).multiply(Matrix4f.rotate(this.rotation)));
        this.texture.bind();
        mesh.render();
        Shader.Bird.disable();
    }
}
