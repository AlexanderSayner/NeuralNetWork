package sayner.sandbox.neuralG.scene.meshes;

import org.joml.Vector3f;

/**
 * Абстракция для любого игрового объекта
 */
public interface Mesh {

    /**
     *
     * @return Вектор поворота
     */
    Vector3f getRotation();

    /**
     *
     * @return Вектор смещения
     */
    Vector3f getPosition();

    /**
     *
     * @return Масштаб
     */
    float getScale();
}
