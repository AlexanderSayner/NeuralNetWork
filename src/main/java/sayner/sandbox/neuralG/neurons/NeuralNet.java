package sayner.sandbox.neuralG.neurons;

import java.util.List;

public class NeuralNet {

    private final List<Connection> inputLayer;
    private final List<Connection> hiddenLayerOne;
    private final List<Connection> outputLayer;

    public NeuralNet(List<Neuron> inputLayer, List<Neuron> hiddenLayerOne, List<Neuron> outputLayer) {
        this.inputLayer = inputLayer;
        this.hiddenLayerOne = hiddenLayerOne;
        this.outputLayer = outputLayer;
    }

    public void start(){

        for (Neuron neuron: inputLayer){
            neuron.activate();
            for (Neuron input: hiddenLayerOne){
                input.
            }
        }
    }
}
