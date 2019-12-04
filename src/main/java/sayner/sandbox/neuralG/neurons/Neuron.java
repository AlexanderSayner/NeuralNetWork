package sayner.sandbox.neuralG.neurons;

/**
 * Абстрактное представление нейрона
 * 
 * @author Dark Archon
 *
 */
public interface Neuron {

    /**
     * Логика
     * @return
     */
    Float activate();

    Float summary();

    Boolean addNewSynapse(Synapse synapse);

    Boolean deleteSynapse(Synapse synapse);
}
