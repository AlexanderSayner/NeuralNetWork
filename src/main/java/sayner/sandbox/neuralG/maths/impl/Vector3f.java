package sayner.sandbox.neuralG.maths.impl;

import sayner.sandbox.neuralG.maths.VectorI;

/**
 * Typical vector with three coordinates
 * TODO: протестировать
 */
public class Vector3f implements VectorI<Float> {

    // There is might be a Float class, but does it really need?
    private float x, y, z;

    public Vector3f() {
        this(0.0f, 0.0f, 0.0f);
    }

    public Vector3f(float x, float y, float z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @return X - coordinate
     */
    public float X() {
        return this.x;
    }

    /**
     * @return Y - coordinate
     */
    public float Y() {
        return this.y;
    }

    /**
     * @return Z - coordinate
     */
    public float Z() {
        return this.z;
    }

    /**
     * Установаить новое значение координаты x
     *
     * @param x
     * @return
     */
    public float X(float x) {
        return this.x = x;
    }

    /**
     * Установаить новое значение координаты y
     *
     * @param y
     * @return
     */
    public float Y(float y) {
        return this.y = y;
    }

    /**
     * Установаить новое значение координаты z
     *
     * @param z
     * @return
     */
    public float Z(float z) {
        return this.z = z;
    }

    /**
     * Получить элемент
     *
     * @param i
     * @return
     */
    @Override
    public Float getElement(int i) {

        switch (i) {
            case 1:
                return this.x;
            case 2:
                return this.y;
            case 3:
                return this.z;
            default:
                return 0.0f;
        }
    }

    /**
     * Устакновить значение
     *
     * @param i
     * @param value
     * @return
     */
    @Override
    public Float setElement(int i, Float value) {

        switch (i) {
            case 1:
                return this.x = value;
            case 2:
                return this.y = value;
            case 3:
                return this.z = value;
            default:
                return 0.0f;
        }
    }
}
