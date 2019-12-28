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

    /**
     * Отдать команду синапсу уменьшить вес
     *
     * @param value - дельта уменьшения весаа
     */
    void reduceWeight(Float value);

    /**
     * Отдать команду увеличить вес
     *
     * @param value - дельта увеличения веса
     */
    void increaseWeight(Float value);
}
