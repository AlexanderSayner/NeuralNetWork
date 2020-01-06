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

    /**
     * Получает значение нейрона, которое последний выдал при активации
     * @return результат последней активации
     */
    Float getResult();
}
