package sayner.sandbox.neuralG.scene.meshes;

import sayner.sandbox.neuralG.graphics.Shader;
import sayner.sandbox.neuralG.graphics.Texture;
import sayner.sandbox.neuralG.graphics.Transformation;
import sayner.sandbox.neuralG.graphics.VertexArray;
import sayner.sandbox.neuralG.input.Input;
import sayner.sandbox.neuralG.maths.impl.Matrix4f;
import sayner.sandbox.neuralG.maths.impl.Vector3f;
import sayner.sandbox.neuralG.scene.Camera;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Класс хранит в себье информацию отрисовкит треугольника
 */
public class Figure implements Mesh{

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

        // Create the Mesh
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
                4, 6, 7, 5, 4, 7,};
        // Объект инициаплизирован
        this.body = new VertexArray(vertices, indices, textureCoordinates);
        this.texture = new Texture("./src/main/resources/img/grassblock.png");
        this.position.z = -2.0f;
        this.rotation.x = 30.0f;
    }

    /**
     * Переммещение объекта
     */
    public void update(Transformation transformation, Camera camera) {


        if (Input.isKeyDown(GLFW_KEY_S)) {
            rotation.z += 1.0f;
        }
        if (Input.isKeyDown(GLFW_KEY_W)) {
            rotation.z -= 1.0f;
        }

        if (Input.isKeyDown(GLFW_KEY_D)) {
            rotation.x += 1.0f;
        }

        if (Input.isKeyDown(GLFW_KEY_A)) {
            rotation.x -= 1.0f;
        }

        rotation.y += 0.5f;

        this.worldMatrix = transformation.getModelViewMatrix(
                this,
                transformation.getViewMatrix(camera)
        );
    }

    // Цикл отрисовки
    public void render() {

        Shader.ImageShader.enable();
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

    @Override
    public org.joml.Vector3f getRotation() {
        return rotation.getJomlVector();
    }

    @Override
    public org.joml.Vector3f getPosition() {
        return position.getJomlVector();
    }

    @Override
    public float getScale() {
        return scale;
    }
}
