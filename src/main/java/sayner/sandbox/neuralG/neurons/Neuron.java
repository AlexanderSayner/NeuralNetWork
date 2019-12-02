package sayner.sandbox.neuralG.neurons;

/**
 * Абстрактное представление нейрона
 * 
 * @author Dark Archon
 *
 */
public interface Neuron {

    Integer summary();

    Boolean addNewSynapse(Synapse synapse);

    Boolean deleteSynapse(Synapse synapse);
}
