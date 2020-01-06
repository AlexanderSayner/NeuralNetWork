package sayner.sandbox.neuralG.neurons.observers.events;

import sayner.sandbox.neuralG.neurons.observers.ObserverList;
import sayner.sandbox.neuralG.neurons.observers.SynapseObserver;

/**
 * Тонкое управление наблютелями синапсов
 * @param <T> - любой тип наблюдателя синапсов
 */
public class SynapseEventTranslator<T extends SynapseObserver> {

    protected ObserverList<T> listeners;

    public SynapseEventTranslator(ObserverList<T> listeners) {
        this.listeners = listeners;
    }

    /**
     * Это имеет смысл, если все эти синапсы
     * подключены к одному выходу нейрона.
     * Следовательно, не имеет никакого значения для кого и откуда взялись эти синапсы,
     * важно, что все они получат одно значение в одно время
     * @param inputValue
     */
    public void inputValueHasReceived(Float inputValue) {
        for (SynapseObserver listener : listeners) {
            listener.inputValueHasReceived(inputValue);
        }
    }


}
