package sayner.sandbox.neuralG.neurons;

/**
 * Абстрактное представление нейрона
 * 
 * @author Dark Archon
 *
 */
public interface Neuron {

    Float summary();

    Boolean addNewSynapse(Synapse synapse);

    Boolean deleteSynapse(Synapse synapse);
}
