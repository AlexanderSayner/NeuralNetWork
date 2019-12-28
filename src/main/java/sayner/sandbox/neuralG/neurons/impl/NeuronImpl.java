package sayner.sandbox.neuralG.neurons.impl;

import sayner.sandbox.neuralG.neurons.Neuron;
import sayner.sandbox.neuralG.neurons.Synapse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NeuronImpl implements Neuron {

    private List<Synapse> synapseList = new ArrayList<>();

    public NeuronImpl(Synapse... synapses) {
        synapseList.addAll(Arrays.asList(synapses));
    }

    /**
     * Суммирует выходы с синапсов
     *
     * @return - сумма взвешанных значений
     */
    private Float sumSynapses() {
        Float sum = 0.0f;
        for (Synapse synapse : synapseList)
            sum += synapse.getWeightedValue();
        return sum;
    }

    /**
     * Вычисление активационной функции
     *
     * @param x - аргумент
     * @return - результат
     */
    private Float activationFunction(Float x) {

        float result= (float) (1 / (1 + Math.exp(-x)));
        return result;
//        return x < 0.5f ? 0.0f : 1.0f;
    }

    @Override
    public Float activate() {
        return activationFunction(sumSynapses());
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
}
