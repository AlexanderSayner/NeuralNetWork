package sayner.sandbox.neuralG.neurons.math;

public class NeuronMath {

    /**
     * вычисляет ошибку выходного нейрона
     *
     * @param ideal              требуемый выход нейрона
     * @param neuronOutput       фактический резуотат
     * @param summaryNeuronInput вход на найрон
     * @return величина ошибки
     */
    public static Float outputError(Float ideal, Float neuronOutput, Float summaryNeuronInput) {

        return (ideal - neuronOutput) * derivative(summaryNeuronInput);
    }

    private static Float derivative(Float x) {
        return sigmoid(x) * (1 - sigmoid(x));
    }

    public static Float sigmoid(Float x) {
        return (float) (1 / (1 + Math.exp(-x)));
    }

    /**
     * Вычисляет числовое значение изменения веса
     *
     * @param learningSpeed скорость обучения
     * @param error         ошибка выходного нейрона
     * @param output        выход аксона, к кторому присоединён синапс
     * @return дельта сдвига веса
     */
    public static Float weightShift(Float learningSpeed, Float error, Float output) {
        return learningSpeed * error * output;
    }

    /**
     * Вычисляет ошибку для нейронов на скрытом слое
     * @param hiddenErrorSum дельта ошибки (сумма произведений весов синапсов
     *                       на дельты ошибок соответвующих им нейронов)
     * @param sumOnInput вход функции активации нейрона
     * @return дельта ошибки нейрона скрытого слоя
     */
    public static Float hiddenError(Float hiddenErrorSum,Float sumOnInput){
        return hiddenErrorSum*derivative(sumOnInput);
    }
}
