package sayner.sandbox.neuralG.neurons;

import sayner.sandbox.neuralG.neurons.observers.SynapseObserver;

/**
 * Абстракция синапса
 * Синапс перенимает свойства наблюдателя
 */
public interface Synapse extends SynapseObserver {

    Float getWeightedValue();
}
