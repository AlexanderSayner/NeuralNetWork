package sayner.sandbox.neuralG.neurons;

/**
 * Абстракция синапса
 */
public interface Synapse {

    Neuron neuron();

    Float reduceWeight(Float value);

    Float increaseWeight(Float value);

    Float getWeight();

    Float getValue();
}
