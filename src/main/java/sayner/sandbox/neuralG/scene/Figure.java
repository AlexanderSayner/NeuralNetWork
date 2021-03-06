package sayner.sandbox.neuralG.scene;

import sayner.sandbox.neuralG.graphics.Shader;
import sayner.sandbox.neuralG.graphics.Texture;
import sayner.sandbox.neuralG.graphics.Transformation;
import sayner.sandbox.neuralG.graphics.VertexArray;
import sayner.sandbox.neuralG.input.Input;
import sayner.sandbox.neuralG.maths.impl.Matrix4f;
import sayner.sandbox.neuralG.maths.impl.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

/**
 * Класс хранит в себье информацию отрисовкит треугольника
 */
public class Figure {

    private final VertexArray body;
    private final Texture texture;
    private Vector3f position = new Vector3f(); // сдвиг для матрица проекций
    private Vector3f rotation = new Vector3f(); // поворот
    private float scale = 1.0f; // масштаб
    org.joml.Matrix4f worldMatrix;

    /**
     * Задаём в конструкторе верошины
     */
    public Figure() {

        /*
        Remember that we are now simulating the effect of a camera looking at our scene.
        And we provided two distances, one to the farthest plane (equal to 1000f) and one to the closest plane (equal to 0.01f)
         */
//        float[] vertices = new float[]{
//                -0.5f, 0.5f, 0.5f, 0.5f, 0.0f, 0.0f,
//                -0.5f, -0.5f, 0.5f, 0.0f, 0.5f, 0.0f,
//                0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 0.5f,
//                0.5f, 0.5f, 0.5f, 0.0f, 0.5f, 0.5f,
//                -0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.2f,
//                0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.2f,
//                -0.5f, -0.5f, -0.5f, 0.2f, 0.0f, 1.0f,
//                0.5f, -0.5f, -0.5f, 0.2f, 0.2f, 0.2f,
//        };

        float[] vertices = new float[]{
                // V0
                -0.5f, 0.5f, 0.5f,
                // V1
                -0.5f, -0.5f, 0.5f,
                // V2
                0.5f, -0.5f, 0.5f,
                // V3
                0.5f, 0.5f, 0.5f,
                // V4
                -0.5f, 0.5f, -0.5f,
                // V5
                0.5f, 0.5f, -0.5f,
                // V6
                -0.5f, -0.5f, -0.5f,
                // V7
                0.5f, -0.5f, -0.5f,

                // For text coords in top face
                // V8: V4 repeated
                -0.5f, 0.5f, -0.5f,
                // V9: V5 repeated
                0.5f, 0.5f, -0.5f,
                // V10: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V11: V3 repeated
                0.5f, 0.5f, 0.5f,

                // For text coords in right face
                // V12: V3 repeated
                0.5f, 0.5f, 0.5f,
                // V13: V2 repeated
                0.5f, -0.5f, 0.5f,

                // For text coords in left face
                // V14: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V15: V1 repeated
                -0.5f, -0.5f, 0.5f,

                // For text coords in bottom face
                // V16: V6 repeated
                -0.5f, -0.5f, -0.5f,
                // V17: V7 repeated
                0.5f, -0.5f, -0.5f,
                // V18: V1 repeated
                -0.5f, -0.5f, 0.5f,
                // V19: V2 repeated
                0.5f, -0.5f, 0.5f,
        };

//        byte[] indices = new byte[]{
//                0, 1, 3,
//                3, 1, 2,
//        };
//        byte[] indices = new byte[]{
//                // Front face
//                0, 1, 3, 3, 1, 2,
//                // Top Face
//                4, 0, 3, 5, 4, 3,
//                // Right face
//                3, 2, 7, 5, 3, 7,
//                // Left face
//                6, 1, 0, 6, 0, 4,
//                // Bottom face
//                2, 1, 6, 2, 6, 7,
//                // Back face
//                7, 6, 4, 7, 4, 5,
//        };

        byte[] indices = new byte[]{
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                8, 10, 11, 9, 8, 11,
                // Right face
                12, 13, 7, 5, 12, 7,
                // Left face
                14, 15, 6, 4, 14, 6,
                // Bottom face
                16, 18, 19, 17, 16, 19,
                // Back face
                4, 6, 7, 5, 4, 7,
        };

        float[] textureCoordinates = new float[]{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,

                0.0f, 0.0f,
                0.5f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,

                // For text coords in top face
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,

                // For text coords in right face
                0.0f, 0.0f,
                0.0f, 0.5f,

                // For text coords in left face
                0.5f, 0.0f,
                0.5f, 0.5f,

                // For text coords in bottom face
                0.5f, 0.0f,
                1.0f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
        };

        // Объект инициаплизирован
        this.body = new VertexArray(vertices, indices, textureCoordinates);
        this.texture = new Texture("./src/main/resources/img/текстура.png");
        this.position.z = -2.0f;
        this.rotation.x = 210.0f;
    }

    /**
     * Переммещение объекта
     */
    public void update(Transformation transformation) {


        if (Input.isKeyDown(GLFW_KEY_S)) {

        }
        if (Input.isKeyDown(GLFW_KEY_W)) {

        }

//        rotation.z += 1.0f;
//        rotation.x += 0.5f;
        rotation.y += 0.5f;

        this.worldMatrix = transformation.getWorldMatrix(
                this.position,
                this.rotation,
                this.scale
        );
    }

    // Цикл отрисовки
    public void render() {

        // Надо надеяться, что эта штука инициализирована
        Shader.ImageShader.enable();
//        Shader.ImageShader.setUniformMat4f("multi_matrix", Matrix4f.translate(this.position).multiply(Matrix4f.rotate(this.rotation)));
        Matrix4f worldMatrix = new Matrix4f(this.worldMatrix);
        Shader.ImageShader.setUniformMat4f("worldMatrix", worldMatrix);
        this.texture.bind();
        // Теперь его надо "завести"
        this.body.render();
        // И убить
        this.body.unbind();
        this.texture.unbind();
        Shader.ImageShader.disable();
    }
}
