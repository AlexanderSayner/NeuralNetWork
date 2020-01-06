package sayner.sandbox.neuralG.maths.impl;

import sayner.sandbox.neuralG.maths.VectorI;

public class Vector4f implements VectorI<Float> {

    public float x, y, z, a;

    public Vector4f() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public Vector4f(float x, float y, float z, float a) {

        this.x = x;
        this.y = y;
        this.z = z;
        this.a = a;
    }
}
