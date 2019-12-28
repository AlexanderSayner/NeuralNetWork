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
     * Выход слоя
     * Запускает вычисления
     *
     * @return
     */
    List<Float> activateNeurons();

    List<Neuron> getNeurons();

    List<Float> getResultValuesList();
}
