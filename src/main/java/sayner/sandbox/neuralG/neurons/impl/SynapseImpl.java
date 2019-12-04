package sayner.sandbox.neuralG.neurons.impl;

import sayner.sandbox.neuralG.neurons.Neuron;
import sayner.sandbox.neuralG.neurons.Synapse;

public class SynapseImpl implements Synapse {

    private Neuron neuron;
    private Float wight = 0.001f;

    public SynapseImpl(Neuron neuron) {
        this.neuron = neuron;
    }

    @Override
    public Neuron neuron() {
        return this.neuron;
    }

    @Override
    public Float getValue() {
        return null;
    }

    @Override
    public Float reduceWeight(Float value) {
        return this.wight -= value;
    }

    @Override
    public Float increaseWeight(Float value) {
        return this.wight += value;
    }

    @Override
    public Float getWeight() {
        return this.wight;
    }
}
