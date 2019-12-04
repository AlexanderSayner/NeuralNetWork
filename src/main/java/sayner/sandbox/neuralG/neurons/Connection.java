package sayner.sandbox.neuralG.neurons;

public class Connection {

    private final Neuron neuron;
    private final Synapse synapse;

    public Connection(Neuron neuron, Synapse synapse) {
        this.neuron = neuron;
        this.synapse = synapse;
    }
}
