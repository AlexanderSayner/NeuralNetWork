package sayner.sandbox.neuralG.neurons.impl;

import sayner.sandbox.neuralG.exceptions.TooManyInputValues;
import sayner.sandbox.neuralG.neurons.Layer;
import sayner.sandbox.neuralG.neurons.Neuron;
import sayner.sandbox.neuralG.neurons.observers.listeners.SynapseCompositeListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Простое (насколько возможно) управление ресурсами слоя нейронов
 */
public class LayerImpl implements Layer {

    // final, чтобы точно инициализировать список, но оставить возможность его изменять
    private final List<Neuron> neurons;
    private List<SynapseCompositeListener> synapseCompositeListeners;

    public LayerImpl(List<Neuron> neurons, SynapseCompositeListener... listeners) {
        this.neurons = neurons;
        this.synapseCompositeListeners.addAll(Arrays.asList(listeners));
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

    @Override
    public void transferValues(Float... input) throws TooManyInputValues {

        int i = 0;
        try {
            // Синапсам, которые не получили входных значений, на вход придёт 0 (см. SynapseImpl)
            for (Float iValue : input) {
                synapseCompositeListeners.get(i++).inputValueHasReceived(iValue);
            }
            // Он добавит все значения, которые только смог, но дальше работать не пойдёт
        } catch (IndexOutOfBoundsException e) {
            System.out.println(String.format("Тсс, слишком чисел дофига на входе %s", e.getMessage()));
        }
    }

    @Override
    public List<Float> activateNeurons() {
        List<Float> resultValuesList = new ArrayList<>();
        for (Neuron neuron : neurons) {
            resultValuesList.add(neuron.activate());
        }
        return resultValuesList;
    }
}
