package sayner.sandbox.neuralG.maths;

public interface VectorI<P> {

    P getElement(int i);

    P setElement(int i, P value);
}
