package sayner.sandbox.neuralG.neurons.impl;

import sayner.sandbox.neuralG.neurons.Neuron;
import sayner.sandbox.neuralG.neurons.Synapse;

public class SynapseImpl implements Synapse {

    private Neuron axon;

    private Float weight; // Вес синапса
    // Входное значение
    private Float value;

    private Float previousWeight = 0.0f; // Перезаписывается при изменении веса

    public SynapseImpl(Float weight) {
        this(weight, 0.0f, new NeuronImpl());
    }

    public SynapseImpl(Float weight, Neuron axon) {
        this(weight, 0.0f, axon);
    }

    public SynapseImpl(Float weight, Float value, Neuron axon) {
        this.weight = weight;
        this.value = value;
        this.axon = axon;
    }

    @Override
    public void inputValueHasReceived(Float value) {
        this.value = value;
    }

    @Override
    public Float getWeightedValue() {
        return value * weight;
    }

    @Override
    public void increaseWeight(Float value) {
        previousWeight = weight;
        weight += value;
    }

    // =================================================================================================================
    // Getter'ы & Setter'ы
    // =================================================================================================================

    @Override
    public Neuron getConnectedAxon() {
        return axon;
    }

    @Override
    public Float getWeight() {
        return weight;
    }

    @Override
    public Float getValue() {
        return value;
    }
}
