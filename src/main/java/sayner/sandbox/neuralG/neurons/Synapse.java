package sayner.sandbox.neuralG.neurons;

import sayner.sandbox.neuralG.neurons.observers.SynapseObserver;

/**
 * Абстракция синапса
 * Синапс перенимает свойства наблюдателя
 */
public interface Synapse extends SynapseObserver {

    /**
     * Взвешивает входное значение
     *
     * @return произведение веса на значение
     */
    Float getWeightedValue();

    /**
     * Простой геттер, необходим для получения веса при обучении
     *
     * @return вес
     */
    Float getWeight();

    /**
     * Для простоты получения данных при обучении
     *
     * @return входное значение
     */
    Float getValue();

    // ========================================================
    // Функционал для обучения нейронной сети
    // ========================================================

    /**
     * Получает значение веса на предыдушем шаге обучения
     *
     * @return дробное значение веса
     */
    Float getPreviousStepWeight();

    /**
     * 1. Обновляет значение веса на предыдущем шаге обучения,
     * заменяя его текщим
     * 2. Устанавливает новое значение веса нейрона
     *
     * @param weight новое значение веса
     * @return старое значение веса
     */
    Float setNewWeight(Float weight);
}