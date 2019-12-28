package sayner.sandbox.neuralG.neurons.impl;

import sayner.sandbox.neuralG.neurons.Synapse;

public class SynapseImpl implements Synapse {

    private Float weight; // Вес синапса
    // Входное значение
    private float value; // Пусть лучше будет примитив, чем схватить NUllPointer или потом нудо будет обработку исключения

    public SynapseImpl(Float weight) {
        this.weight = weight;
    }

    public SynapseImpl(Float weight, Float value) {
        this.weight = weight;
        this.value = value;
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
         weight -= value;
    }

    @Override
    public void increaseWeight(Float value) {
         weight += value;
    }

    // =================================================================================================================
    // Getter'ы & Setter'ы
    // =================================================================================================================

    @Override
    public Float getWeight() {
        return weight;
    }

    @Override
    public Float getValue() {
        return value;
    }
}
