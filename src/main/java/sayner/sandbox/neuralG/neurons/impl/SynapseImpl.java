package sayner.sandbox.neuralG.neurons.impl;

import sayner.sandbox.neuralG.neurons.Neuron;
import sayner.sandbox.neuralG.neurons.Synapse;

public class SynapseImpl implements Synapse {

    Neuron inputNeuron;
    private Float weight = 1.0f; // Вес синапса
    private Float value; // Входное знаение

    public SynapseImpl(Float weight) {
        this.weight = weight;
    }

    public SynapseImpl(Float weight, Float value) {
        this.weight = weight;
        this.value = value;
    }

    @Override
    public Float transferValue(Float value) {
        return this.value = value;
    }

    @Override
    public Float getWeightedValue() {
        return value * weight;
    }

    @Override
    public Float reduceWeight(Float value) {
        return weight -= value;
    }

    @Override
    public Float increaseWeight(Float value) {
        return weight += value;
    }
}
