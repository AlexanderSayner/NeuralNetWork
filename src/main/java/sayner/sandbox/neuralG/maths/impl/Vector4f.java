package sayner.sandbox.neuralG.maths.impl;

import sayner.sandbox.neuralG.maths.VectorI;

public class Vector4f extends Vector3f implements VectorI<Float> {

    private float a;

    public Vector4f() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public Vector4f(float x, float y, float z, float a) {
        super(x, y, z);
        this.a = a;
    }

    public float A() {
        return this.a;
    }

    public float A(float a) {
        return this.a = a;
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
            case 4:
                return this.a;
            default:
                return super.getElement(i);
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
            case 4:
                return this.a = value;
            default:
                return super.setElement(i, value);
        }
    }
}
