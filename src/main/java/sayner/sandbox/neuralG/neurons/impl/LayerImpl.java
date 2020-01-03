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

    // Для обучения методом обратного распространения
    List<Float> resultValuesList;

    /**
     * Принимает на входе множество нейронов
     * и слушателей для их входных синапсов
     *
     * @param neurons   - множество нейронов
     * @param listeners - их входы
     */
    public LayerImpl(List<Neuron> neurons, SynapseCompositeListener... listeners) {
        this.neurons = neurons;
        this.synapseCompositeListeners = new ArrayList<>();
        this.synapseCompositeListeners.addAll(Arrays.asList(listeners));
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
    public void activateNeurons() {

        for (Neuron neuron : neurons) {
            neuron.activate();
        }
    }

    // =================================================================================================================
    // Getter'ы & Setter'ы
    // =================================================================================================================

    public List<Neuron> getNeurons() {
        return neurons;
    }

    @Override
    public List<Float> getResultList() {
        List<Float> result=new ArrayList<>();
        for (Neuron neuron : neurons) {
            result.add(neuron.getResult());
        }
        return result;
    }
}
