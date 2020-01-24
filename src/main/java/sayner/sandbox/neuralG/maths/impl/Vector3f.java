package sayner.sandbox.neuralG.maths.impl;

import sayner.sandbox.neuralG.maths.VectorI;

/**
 * Typical vector with three coordinates
 * TODO: протестировать
 */
public class Vector3f implements VectorI<Float> {

    // There is might be a Float class, but does it really need?
    public float x, y, z;

    public Vector3f() {
        this(0.0f, 0.0f, 0.0f);
    }

    public Vector3f(float x, float y, float z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public org.joml.Vector3f getJomlVector() {
        return new org.joml.Vector3f(x, y, z);
    }
}
