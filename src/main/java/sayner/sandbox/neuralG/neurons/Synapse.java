package sayner.sandbox.neuralG.neurons;

/**
 * Абстракция синапса
 */
public interface Synapse {

    Float reduceWeight(Float value);

    Float increaseWeight(Float value);

    Float getWeight();
}
