package sayner.sandbox.neuralG.neurons.impl;

import sayner.sandbox.neuralG.neurons.Neuron;
import sayner.sandbox.neuralG.neurons.Synapse;

public class SynapseImpl implements Synapse {

    private Neuron axon;

    private Float weight; // Вес синапса
    // Входное значение
    private Float value; // Пусть лучше будет примитив, чем схватить NUllPointer или потом нудо будет обработку исключения

    private Float previousWeight = 0.0f; // Перезаписывается при изменении веса

    public SynapseImpl(Float weight) {
        this(weight, 0.0f, new NeuronImpl());
    }

    public SynapseImpl(Float weight, Float value) {
        this(weight, value, new NeuronImpl());
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
    public void reduceWeight(Float value) {
        previousWeight = weight;
        weight -= value;
    }

    @Override
    public void increaseWeight(Float value) {
        previousWeight = weight;
        weight += value;
    }

    @Override
    public Float getPreviousWeightShift() {
        return previousWeight;
    }

    @Override
    public Float setNewWeight(Float weight) {
        previousWeight = this.weight;
        this.weight = weight;
        return previousWeight;
    }

    // =================================================================================================================
    // Getter'ы & Setter'ы
    // =================================================================================================================

    @Override
    public Neuron getConnectedAxon() {
        return axon;
    }

    @Override
    public void setNewConnectedAxon(Neuron axon) {
        this.axon = axon;
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
