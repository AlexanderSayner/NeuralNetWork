package sayner.sandbox.neuralG.neurons.impl;

import sayner.sandbox.neuralG.neurons.Neuron;
import sayner.sandbox.neuralG.neurons.Synapse;

import java.util.Collection;
import java.util.LinkedList;

public class NeuronImpl implements Neuron {

    private final Collection<Synapse> synapseCollection;

    /**
     * По-умолчанию коллекция синапсов реалицуется связным списком,
     * потому что может потребоваться частая вставка синапсов в середину,
     * а также их удаление
     */
    public NeuronImpl() {
        this.synapseCollection = new LinkedList<>();
    }

    /**
     * Позволяет инициалзировать коллекцию синапсов любым типом реализации
     *
     * @param synapseCollection
     */
    public NeuronImpl(Collection<Synapse> synapseCollection) {
        this.synapseCollection = synapseCollection;
    }

    @Override
    public Integer summary() {
        Integer result = 0;
        for (Synapse synapse : this.synapseCollection) {
            result += synapse.getWeight();
        }
        return result;
    }

    @Override
    public Boolean addNewSynapse(Synapse synapse) {

        return this.synapseCollection.add(synapse);
    }

    @Override
    public Boolean deleteSynapse(Synapse synapse) {

        return this.synapseCollection.remove(synapse);
    }
}
