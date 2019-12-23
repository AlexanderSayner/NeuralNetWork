package sayner.sandbox.neuralG.neurons.observers.events;

import sayner.sandbox.neuralG.neurons.observers.ObserverList;
import sayner.sandbox.neuralG.neurons.observers.NeuronObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Управление наблюдателями нейронов
 * @param <T> - любой тип наблюдателя нейронов
 */
public class NeuronEventTranslator<T extends NeuronObserver> {

    protected ObserverList<T> listeners;

    public NeuronEventTranslator(ObserverList<T> listeners) {
        this.listeners = listeners;
    }

    /**
     * Подойдёт для активации слоя нейронов
     */
    public List<Float> activateNeuron() {

        List<Float> resultValues=new ArrayList<>();

        for (NeuronObserver listener : listeners) {
            resultValues.add(listener.activate());
        }

        return resultValues;
    }
}
