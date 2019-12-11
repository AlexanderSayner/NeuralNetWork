package sayner.sandbox.neuralG.neurons.impl;

import sayner.sandbox.neuralG.neurons.Layer;
import sayner.sandbox.neuralG.neurons.Neuron;
import sayner.sandbox.neuralG.neurons.Synapse;

import java.util.ArrayList;
import java.util.List;

public class LayerImpl implements Layer {

    List<Neuron> neurons = new ArrayList<>(); // Нейроны, формирующие слой

    public LayerImpl() {

        Synapse synapse0 = new SynapseImpl(0.2f);
        Synapse synapse1 = new SynapseImpl(0.3f);
        Synapse synapse2 = new SynapseImpl(0.4f);
        Synapse synapse3 = new SynapseImpl(0.5f);
        Synapse synapse4 = new SynapseImpl(0.6f);

        Neuron neuron0 = new NeuronImpl(synapse0);
        Neuron neuron1 = new NeuronImpl(synapse1);
        Neuron neuron2 = new NeuronImpl(synapse2);
        Neuron neuron3 = new NeuronImpl(synapse3);
        Neuron neuron4 = new NeuronImpl(synapse4);

        neurons.add(neuron0);
        neurons.add(neuron1);
        neurons.add(neuron2);
        neurons.add(neuron3);
        neurons.add(neuron4);
    }

    public LayerImpl(List<Neuron> neurons) {
        this.neurons = neurons;
    }

    @Override
    public Boolean onInput(List<Float> input) throws IndexOutOfBoundsException {

        List<Synapse> inputSynapses = new ArrayList<>();
        neurons.forEach(neuron ->
                inputSynapses.addAll(neuron.getSynapses()));

        for (int i = 0; i < inputSynapses.size(); i++) {
            inputSynapses.get(i).transferValue((float) input.get(i%input.size()));
        }
        return true;
    }

    @Override
    public List<Float> onOutput() {

        List<Float> outputSignals = new ArrayList<>();
        for (Neuron neuron : neurons) {
            outputSignals.add(neuron.activate());
        }
        return outputSignals;
    }
}
