package sayner.sandbox.neuralG.neurons.impl;

import sayner.sandbox.neuralG.neurons.Connection;
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


    /**
     *               1
     * f(x) = -------------
     *         1 + e^(-x)
     * @param x - argument
     * @return f(x)
     */
    private Float activationFunction(float x){

        return (float)(1/(1+Math.exp(-x)));
    }

    @Override
    public Float activate() {
        return activationFunction(summary());
    }

    public Float summary() {
        Float result = 0.0f;
        for (Synapse synapse : this.synapseCollection) {
            result += synapse.getWeight()*synapse.getValue();
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
