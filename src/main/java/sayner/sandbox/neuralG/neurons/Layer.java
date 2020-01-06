package sayner.sandbox.neuralG.neurons;

import sayner.sandbox.neuralG.exceptions.TooManyInputValues;

import java.util.List;

/**
 * Абстракция простого слоя нейронной сети
 */
public interface Layer {

    /**
     * Вход слоя
     * Передаёт вектор входных значений нейронам
     *
     * @param input вектор
     * @throws TooManyInputValues если будет передано чисел больше, чем входов нейронов
     */
    void transferValues(Float... input) throws TooManyInputValues;

    /**
     * Запускает вычисления (активирует нейроны)
     *
     * @return
     */
    void activateNeurons();

    /**
     * Получает выходной вектор слоя
     * @return вектор, размерность равна количесиву нейронов слоя
     */
    List<Float> getResultList();

    /**
     * Получается все нейроны, из которых состои слой
     * @return массив с нейронами в порядке их расположения
     */
    List<Neuron> getNeurons();
}
