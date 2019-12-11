package sayner.sandbox.neuralG.neurons;

import java.util.List;

/**
 * Абстакция слоя нейронной сети
 */
public interface Layer {

    /**
     * Передаёт значения входным синапсам нейронов
     * @param input - упорядоченный список входных сигналов,
     *              его размер должен быть равен количеству входных синапсов первого слоя
     * @return
     */
    Boolean onInput(List<Float> input) throws IndexOutOfBoundsException;

    /**
     * Активирует нейроны, получает упорядоченный список с выходными сигналами
     * @return
     * @throws IndexOutOfBoundsException
     */
    List<Float> onOutput();
}
