package sayner.sandbox.neuralG.neurons;

/**
 * Абстракция синапса
 */
public interface Synapse {

    Neuron neuron();

    Float getWeightedValue(Float value);

    Float reduceWeight(Float value);

    Float increaseWeight(Float value);
}
