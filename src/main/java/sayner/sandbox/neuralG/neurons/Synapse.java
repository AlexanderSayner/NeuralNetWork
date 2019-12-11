package sayner.sandbox.neuralG.neurons;

/**
 * Абстракция синапса
 */
public interface Synapse {

    Float transferValue(Float value);

    Float getWeightedValue();

    Float reduceWeight(Float value);

    Float increaseWeight(Float value);
}
