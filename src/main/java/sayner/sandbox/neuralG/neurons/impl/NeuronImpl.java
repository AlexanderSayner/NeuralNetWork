package sayner.sandbox.neuralG.neurons.impl;

import sayner.sandbox.neuralG.neurons.Neuron;
import sayner.sandbox.neuralG.neurons.Synapse;
import sayner.sandbox.neuralG.neurons.math.NeuronMath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NeuronImpl implements Neuron {

    private List<Synapse> synapseList = new ArrayList<>();
    private Float input; // Суммарное входное значение

    private Float result;
    private Float neuronErrorDelta; // Дельта ошибки нейрона

    public NeuronImpl(Synapse... synapses) {
        synapseList.addAll(Arrays.asList(synapses));
    }

    /**
     * Суммирует выходы с синапсов
     *
     * @return - сумма взвешанных значений
     */
    private Float sumSynapses() {
        input = 0.0f;
        for (Synapse synapse : synapseList)
            input += synapse.getWeightedValue();
        return input;
    }

    /**
     * Вычисление активационной функции
     *
     * @param x - аргумент
     * @return - результат
     */
    private Float activationFunction(Float x) {

        return NeuronMath.sigmoid(x);
    }

    @Override
    public Float activate() {
        result = activationFunction(sumSynapses());
        return result;
    }

    @Override
    public Float getInputValue() {
        return input;
    }

    @Override
    public List<Synapse> getSynapses() {
        return synapseList;
    }

    @Override
    public Boolean addNewSynapse(Synapse synapse) {
        return synapseList.add(synapse);
    }

    @Override
    public Boolean removeSynapse(Synapse synapse) {
        return synapseList.remove(synapse);
    }

    @Override
    public Float getResult() {
        return result;
    }

    @Override
    public Float getNeuronErrorDelta() {
        return neuronErrorDelta;
    }

    @Override
    public void setNeuronErrorDelta(Float neuronErrorDelta) {
        this.neuronErrorDelta = neuronErrorDelta;
    }

    @Override
    public Float sumWeightDelta(Float weightDelta) {
        if (this.neuronErrorDelta == null) {
            this.neuronErrorDelta = 0.0f;
        }
        return this.neuronErrorDelta += weightDelta;
    }
}
