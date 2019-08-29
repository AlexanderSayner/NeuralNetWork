package sayner.sandbox.neuralG.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * static only class with useful functionality.
 */
public class BufferUtils {

    // Sorry, but I won't let you initialize this class
    private BufferUtils() {

    }

    public static ByteBuffer createByteBuffer(byte[] array) {

        ByteBuffer result = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
        result.put(array).flip(); // to inverse it

        return result;
    }

    public static FloatBuffer createFloatBuffer(float[] array) {

        FloatBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        result.put(array).flip();

        return result;
    }

    public static IntBuffer createIntegerBuffer(int[] array) {

        IntBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
        result.put(array).flip();

        return result;
    }
}
