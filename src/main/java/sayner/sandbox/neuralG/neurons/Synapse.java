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

    /**
     * Пока что может соединяться с одним нейроном
     * @return нейрон, к выходу которого присоединён синапс
     */
    Neuron getConnectedAxon();

    // ========================================================
    // Функционал для обучения нейронной сети
    // ========================================================


    /**
     *  Изменяет вес, складывая текущее его значение с новым
     *
     * @param value - дельта увеличения веса
     */
    void increaseWeight(Float value);
}