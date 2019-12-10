package sayner.sandbox.neuralG.neurons.impl;

import sayner.sandbox.neuralG.neurons.Neuron;
import sayner.sandbox.neuralG.neurons.Synapse;

import java.util.List;

public class NeuronImpl implements Neuron {

    private List<Synapse> synapseList;

    public NeuronImpl(Synapse ... synapses) {
        for (Synapse synapse:synapses) {
            synapseList.add(synapse);
        }
    }



    @Override
    public Float activate() {
        return null;
    }

    @Override
    public Boolean addNewSynapse(Synapse synapse) {
        return synapseList.add(synapse);
    }

    @Override
    public Boolean removeSynapse(Synapse synapse) {
        return synapseList.remove(synapse);
    }
}
