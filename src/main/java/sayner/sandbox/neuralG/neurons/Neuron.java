package sayner.sandbox.neuralG.neurons;

import sayner.sandbox.neuralG.neurons.observers.NeuronObserver;

import java.util.List;

/**
 * Абстрактное представление нейрона
 * 
 * @author Dark Archon
 *
 */
public interface Neuron extends NeuronObserver {

    /**
     * Получает список синапсов
     * @return синапсы
     */
    List<Synapse> getSynapses();

    /**
     * Получает сумму взвешанных значений с синапсов
     * @return вход активационной функции
     */
    Float getInputValue();

    /**
     * Получает текущее значение ошибки нейрона
     * @return значение ошибки нейрона
     */
    Float getNeuronErrorDelta();

    /**
     * Берёт некущее значение ошибки нейрона и складывает его с текущем
     * @param weightDelta слагаемое к дельте
     * @return сумма
     */
    Float sumWeightDelta(Float weightDelta);

    /**
     * Устанавливает новое значение ошибки нейрона
     * @param neuronErrorDelta дельта ошибки
     */
    void setNeuronErrorDelta(Float neuronErrorDelta);
}
