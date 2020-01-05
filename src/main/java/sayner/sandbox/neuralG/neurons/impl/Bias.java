package sayner.sandbox.neuralG.neurons.impl;

import sayner.sandbox.neuralG.neurons.Neuron;
import sayner.sandbox.neuralG.neurons.Synapse;

import java.util.ArrayList;
import java.util.List;

/**
 * Нейрон смещения
 */
public class Bias implements Neuron {

    private List<Synapse> synapseList = new ArrayList<>();

    private Float bias=2.0f;
    private Float neuronErrorDelta; // Дельта ошибки нейрона

    @Override
    public List<Synapse> getSynapses() {
        return synapseList;
    }

    @Override
    public Float getInputValue() {
        // Нейроны смещения не имеют входа
        return bias;
    }

    @Override
    public Float getNeuronErrorDelta() {
        return neuronErrorDelta;
    }

    @Override
    public Float sumWeightDelta(Float weightDelta) {
        if (this.neuronErrorDelta == null) {
            this.neuronErrorDelta = 0.0f;
        }
        return this.neuronErrorDelta += weightDelta;
    }

    @Override
    public void setNeuronErrorDelta(Float neuronErrorDelta) {
        this.neuronErrorDelta = neuronErrorDelta;
    }

    @Override
    public Boolean addNewSynapse(Synapse synapse) {
        return null;
    }

    @Override
    public Boolean removeSynapse(Synapse synapse) {
        return null;
    }

    @Override
    public Float activate() {
        return bias;
    }

    @Override
    public Float getResult() {
        return bias;
    }
}
