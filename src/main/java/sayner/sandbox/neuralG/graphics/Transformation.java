package sayner.sandbox.neuralG.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformation {

    private final Matrix4f projectionMatrix;

    private final Matrix4f worldMatrix;

    public Transformation() {
        worldMatrix = new Matrix4f();
        projectionMatrix = new Matrix4f();
    }

    public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
        float aspectRatio = width / height;
        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);
        return projectionMatrix;
    }

    public Matrix4f getWorldMatrix(sayner.sandbox.neuralG.maths.impl.Vector3f offsetIn, sayner.sandbox.neuralG.maths.impl.Vector3f rotationIn, float scale) {
        Vector3f offset=new Vector3f();
        offset.set(offsetIn.x,offsetIn.y,offsetIn.z);
        Vector3f rotation=new Vector3f();
        rotation.set(rotationIn.x,rotationIn.y,rotationIn.z);

        worldMatrix.identity().translate(offset).
                rotateX((float)Math.toRadians(rotation.x)).
                rotateY((float)Math.toRadians(rotation.y)).
                rotateZ((float)Math.toRadians(rotation.z)).
                scale(scale);
        return worldMatrix;
    }
}
