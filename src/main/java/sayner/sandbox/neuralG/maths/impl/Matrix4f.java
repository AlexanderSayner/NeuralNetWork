package sayner.sandbox.neuralG.maths.impl;

import sayner.sandbox.neuralG.maths.MatrixI;
import sayner.sandbox.neuralG.utils.BufferUtils;

import java.nio.FloatBuffer;

/**
 * 4 dimensional matrix
 */
public class Matrix4f implements MatrixI<Float> {

    // Количество элементов матрицы 16
    public static final int SIZE = 4 * 4;
    // No need for 2 dimensional array. Every fourth element is the next element in a column
    private float[] elements = new float[SIZE];

    /**
     * Пустая матрица
     */
    public Matrix4f() {

    }

    /**
     * Заполнение единицами главной диагонали
     *
     * @return
     */
    public static Matrix4f identity() {

        Matrix4f result = new Matrix4f();

        for (int i = 0; i < SIZE; i++) {
            result.elements[i] = 0.0f;
        }

        result.elements[0 + 0 * 4] = 1.0f;
        result.elements[1 + 1 * 4] = 1.0f;
        result.elements[2 + 2 * 4] = 1.0f;
        result.elements[3 + 3 * 4] = 1.0f;

        return result;
    }

    /**
     * TODO: посмотреть видос о том, как это работает
     *
     * @param left
     * @param right
     * @param bottom
     * @param top
     * @param near
     * @param far
     * @return
     */
    public static Matrix4f orthogonal(float left, float right, float bottom, float top, float near, float far) {

        Matrix4f result = identity();

        result.elements[0 + 0 * 4] = 2.0f / (right - left);

        result.elements[1 + 1 * 4] = 2.0f / (top - bottom);

        result.elements[2 + 2 * 4] = 2.0f / (near - far);

        result.elements[0 + 3 * 4] = (left + right) / (left - right);
        result.elements[1 + 3 * 4] = (bottom + top) / (bottom - top);
        result.elements[2 + 3 * 4] = (far + near) / (far - near);

        return result;
    }

    /**
     * @param vector
     * @return
     */
    public static Matrix4f translate(Vector3f vector) {

        Matrix4f result = identity();

        result.elements[0 + 3 * 4] = vector.X();
        result.elements[1 + 3 * 4] = vector.Y();
        result.elements[2 + 3 * 4] = vector.Z();

        return result;
    }

    /**
     * @param angle in degrees
     * @return
     */
    public static Matrix4f rotate(float angle) {

        Matrix4f result = identity();

        float radiansAngle = (float) Math.toRadians(angle);
        float cosine = (float) Math.cos(radiansAngle);
        float sine = (float) Math.sin(radiansAngle);

        result.elements[0 + 0 * 4] = cosine;
        result.elements[1 + 0 * 4] = sine;

        result.elements[0 + 1 * 4] = -sine;
        result.elements[1 + 1 * 4] = cosine;

        return result;
    }

    /**
     * Умножение матриц
     *
     * @param matrix
     * @return
     */
    public Matrix4f multiply(Matrix4f matrix) {

        Matrix4f result = new Matrix4f();

        for (int y = 0; y < 4; y++) {

            for (int x = 0; x < 4; x++) {

                float sum = 0.0f;

                for (int e = 0; e < 4; e++) {

                    sum += this.elements[x + e * 4] * matrix.elements[e + y * 4];
                }

                result.elements[x + y * 4] = sum;
            }
        }

        return result;
    }

    public FloatBuffer toFloatBuffer() {

        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(elements);

        return floatBuffer;
    }

    /**
     * Проверка на границы индекса
     *
     * @param x строка
     * @param y столбец
     * @return true, значит, входит, otherwise - нет
     */
    private boolean checkIndex(int x, int y) {

        if ((x >= 1 && x <= 4) && (y >= 1 && y <= 4)) {
            return true;
        }

        return false;
    }

    /**
     * Осчёт начинается с единицы
     * Started from one
     *
     * @param x
     * @param y
     * @return
     */
    @Override
    public Float getElement(int x, int y) {

        if (!checkIndex(x, y)) {
            throw new IllegalArgumentException(String.format("Координата (x = %d, y = %d) выхоодит за пределы 4х-мерной матрицы", x, y));
        }

        return this.elements[(x - 1) + (y - 1) * 4];
    }

    /**
     * Устанавливает значение выбранного элемента
     * Отсчёт тоже с единицы
     *
     * @param x     Строка
     * @param y     Столбец
     * @param value Новое значение
     * @return если == value, то всё ок
     */
    @Override
    public Float setElement(int x, int y, Float value) {

        if (!checkIndex(x, y)) {
            throw new IllegalArgumentException(String.format("Координата (x = %d, y = %d) выхоодит за пределы 4х-мерной матрицы", x, y));
        }

        this.elements[(x - 1) + (y - 1) * 4] = value;

        return value;
    }
}
