package sayner.sandbox.neuralG.neurons;

/**
 * Абстракция синапса
 */
public interface Synapse {

    Integer reduceWeight(Integer value);

    Integer increaseWeight(Integer value);

    Integer getWeight();
}
