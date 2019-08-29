package sayner.sandbox.neuralG.maths;

public interface MatrixI<P> {

    P getElement(int x, int y);

    P setElement(int x, int y, P value);
}
