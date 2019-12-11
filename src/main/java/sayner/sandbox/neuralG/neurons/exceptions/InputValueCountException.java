package sayner.sandbox.neuralG.neurons.exceptions;

/**
 * Ошибка при разных количествах входных значений, переданных слою и количестврм синапсов слоя
 */
public class InputValueCountException extends Exception {

    public InputValueCountException() {
        super("Количество переданных значений не совпадает с количеством входных синапсов слоя " +
                "(The number of transmitted values does not match the number of input synapses)");
    }
}
