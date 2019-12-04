package sayner.sandbox.neuralG.neurons;

import sayner.sandbox.neuralG.neurons.impl.NeuronImpl;

public class NeuralBuilder {

    public static Neuron buildNeuron(Synapse synapse){
        Neuron neuron=new NeuronImpl();
        neuron.addNewSynapse(synapse);
        return neuron;
    }
}
