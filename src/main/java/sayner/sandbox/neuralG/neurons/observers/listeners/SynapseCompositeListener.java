package sayner.sandbox.neuralG.neurons.observers.listeners;

import sayner.sandbox.neuralG.neurons.observers.ObserverList;
import sayner.sandbox.neuralG.neurons.observers.events.SynapseEventTranslator;
import sayner.sandbox.neuralG.neurons.observers.SynapseObserver;

public class SynapseCompositeListener extends ObserverList<SynapseObserver> implements SynapseObserver {

    private SynapseEventTranslator<SynapseObserver> synapseTranslator = new SynapseEventTranslator<SynapseObserver>(this);

    @Override
    public void inputValueHasReceived(Float input) {

        synapseTranslator.inputValueHasReceived(input);
    }

    @Override
    public void reduceWeight(Float value) {

        // TODO: добавить сюда новый транслятор
    }

    @Override
    public void increaseWeight(Float value) {

        // TODO: добавить сюда новый транслятор
    }
}
