package sayner.sandbox.neuralG.neurons;

import java.util.List;

/**
 * Абстрактное представление нейрона
 * 
 * @author Dark Archon
 *
 */
public interface Neuron {

    /**
     * Логика активации
     * @return
     */
    Float activate();

    List<Synapse> getSynapses();

    Boolean addNewSynapse(Synapse synapse);

    Boolean removeSynapse(Synapse synapse);
}
