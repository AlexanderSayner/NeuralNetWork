package sayner.sandbox.neuralG.neurons.observers;

/**
 * Наблюдатель синапсов
 * Класс отслеживает события и активирует синапсы
 */
public interface SynapseObserver {

    /**
     * Присвоит значене синапсу
     *
     * @param input - входное значение
     */
    void inputValueHasReceived(Float input);
}
