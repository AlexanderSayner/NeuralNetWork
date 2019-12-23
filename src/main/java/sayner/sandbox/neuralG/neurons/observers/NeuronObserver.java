package sayner.sandbox.neuralG.neurons.observers;

/**
 * Наблюдатель, управляющий нейронами
 */
public interface NeuronObserver {

    /**
     * Активирует все отслеживаемые нейроны
     * @return - Список результиущих значений в порядке сортировки нейронов в списке наблюдалетя
     */
    Float activate();
}
