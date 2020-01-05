package sayner.sandbox.neuralG.neurons;

import sayner.sandbox.neuralG.neurons.observers.NeuronObserver;

import java.util.List;

/**
 * Абстрактное представление нейрона
 * 
 * @author Dark Archon
 *
 */
public interface Neuron extends NeuronObserver {

    List<Synapse> getSynapses();

    Float getInputValue();

    Float getNeuronErrorDelta();
    Float sumWeightDelta(Float weightDelta);
    void setNeuronErrorDelta(Float neuronErrorDelta);

    Boolean addNewSynapse(Synapse synapse);

    Boolean removeSynapse(Synapse synapse);
}
