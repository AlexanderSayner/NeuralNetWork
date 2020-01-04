package sayner.sandbox.neuralG.neurons;

import sayner.sandbox.neuralG.exceptions.TooManyInputValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class NeuralNet {

    Float E = 0.7f; // Скорость обучения
    Float alpha = 0.03f; // Момент

    //    private final Layer firstLayer;
//    private final Layer secondLayer;
    private final Layer firstRandomLayer;
    private final Layer secondRandomLayer;

    private final Layer firstImageLayer;
    private final Layer secondImageLayer;
    private final Layer outputImageLayer;

    public NeuralNet() {

//        firstLayer = XorNetBuilder.createFirstLayerXOR();
//        secondLayer = XorNetBuilder.createSecondLayerXOR();
        firstRandomLayer = XorNetBuilder.createRandomWeightFirstLayer();
        secondRandomLayer = XorNetBuilder.createRandomWeightSecondLayer(firstRandomLayer);

        firstImageLayer = XorNetBuilder.createFirstLayer();
        secondImageLayer = XorNetBuilder.createSecondLayer(firstImageLayer);
        outputImageLayer = XorNetBuilder.createOutputLayer(secondImageLayer);
    }

//    public void start() {
//
//        try {
//            firstLayer.transferValues(1.0f, 1.0f);
//        } catch (TooManyInputValues e) {
//            System.out.println("ERROR: (first lvl) " + e.getMessage());
//        }
//
//        try {
//            secondLayer.transferValues(firstLayer.activateNeurons().toArray(Float[]::new));
//        } catch (TooManyInputValues e) {
//            System.out.println("ERROR: (second lvl) " + e.getMessage());
//        }
//
//        secondLayer.activateNeurons().forEach(System.out::println);
//    }

    /**
     * Запускает вычисления нейронной сети
     *
     * @param onInput входные данные
     * @return фактический результат
     */
    private List<Float> neuralNetResult(List<Float> onInput) {

        // Передаём значения в первый слой
        try {
            firstRandomLayer.transferValues(onInput.toArray(Float[]::new));
        } catch (TooManyInputValues e) {
            System.out.println("ERROR: (first lvl) " + e.getMessage());
        }

        // Активируем первый и сразу закидываем результаты во второй
        try {
            firstRandomLayer.activateNeurons();
            List<Float> firstLayerResult = firstRandomLayer.getResultList();
            secondRandomLayer.transferValues(firstLayerResult.toArray(Float[]::new));
        } catch (TooManyInputValues e) {
            System.out.println("ERROR: (second lvl) " + e.getMessage());
        }

        secondRandomLayer.activateNeurons();
        // Итоговый результат
        return secondRandomLayer.getResultList();
    }


    /**
     * Вычисляет дельту ошибки выходного нейрона
     * Формула вычисления дельты:
     * дельта = (OUTideal - OUTactual) * производную от функции активации(IN)
     * производная сигмоидальной функции активации = (1 - OUT) * OUT
     *
     * @param ideal  ожидаемый выход нейронный сети
     * @param output фактический
     * @return дельта погрешности нейрона выходного слоя
     */
    private Float outputNeuronDelta(Float ideal, Float output) {
        Float delta = (ideal - output) * ((ideal - output) * output);
        return delta;
    }

    /**
     * Считает дельту ошибки внутреннего нейрона
     * При корректировке весов методом обратного распространения
     * формула для внутренних (не выодных) нейронов меняется
     *
     * @param neuronOutput      вход синапса (и аксон нейрона)
     * @param synapseWeight     вес текущего синапса
     * @param outputNeuronDelta дельта ошибки выходного нейрона (к нему приходит синапс)
     * @param expectedResult    ожидаемый выход
     * @return дельта ошибки нейрона
     */
    private Float weightDeltaError(Float neuronOutput, Float synapseWeight, Float outputNeuronDelta, Float expectedResult) {
        Float innerDelta = ((expectedResult - neuronOutput) * neuronOutput) * (synapseWeight * outputNeuronDelta);
        return innerDelta;
    }

    /**
     * Расчёт градиента
     *
     * @param synapseInputValue
     * @param neuronDeltaOnSynapseOutput дельта ошибки нейрона на выходе синапса
     * @return
     */
    private Float weightGradient(Float synapseInputValue, Float neuronDeltaOnSynapseOutput) {
        Float gradient = synapseInputValue * neuronDeltaOnSynapseOutput;
        return gradient;
    }


    /**
     * Вычисление дельты для корректировки веса
     *
     * @param E                   скорость обучения
     * @param weightGradient      градиент веса
     * @param alpha               момент
     * @param previousWeightShift дельта на предыдущем шаге
     * @return значение, на которое следует изменить вес синапса
     */
    private Float weightShift(Float E, Float weightGradient, Float alpha, Float previousWeightShift) {

        return E * weightGradient + alpha * previousWeightShift;
    }

    public void startImageLearning() {

        alpha-=0.01f;

        List<Float> inputValues = new ArrayList<>();
        for (int i = 0; i < 28 * 28; i++) {
            inputValues.add((float) (i / 28 * 28));
        }

        List<Float> expectedOutput = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            expectedOutput.add(i == 2 ? 1.0f : 0.0f);
        }

        // Итерации обучения
        for (int i = 0; i < 100; i++) {

            // Вычисляю выход нейронной сети
            List<Float> neuralNetworkResult = imageNeuralNetResult(inputValues);

            // Теперь я должен отдельно просчитать дельту ошибки для
            // каждого выходного значения нейронной сети
            int er = 0;
            for (Neuron outputNeuron : outputImageLayer.getNeurons()) {
                Float outputNeuralDelta = outputNeuronDelta(expectedOutput.get(er), neuralNetworkResult.get(er));

                for (Synapse learningSynapse : outputNeuron.getSynapses()) {
                    // Дельта ошибки для веса синапса
                    Float weightDelta = weightDeltaError(
                            learningSynapse.getValue(),
                            learningSynapse.getWeight(),
                            outputNeuralDelta,
                            expectedOutput.get(er)
                    );

                    // Пеередаём дельту потомкам
                    learningSynapse.getConnectedAxon().setWeightDelta(weightDelta);

                    // Вычисление градиента для веса синапса
                    Float weightGradient = weightGradient(learningSynapse.getValue(), outputNeuralDelta);

                    // Величина изменения веса
                    Float weightShift = weightShift(E, weightGradient, alpha, learningSynapse.getPreviousWeightShift());
                    // и плюс, и минус нормальнно отработает
                    learningSynapse.increaseWeight(weightShift);

                }

                er++;
            }

            // Следующий шаг - изменить веса между первым и вторым скрытым слоем
            for (Neuron neuron : secondImageLayer.getNeurons()) {

                for (Synapse learningSynapse : neuron.getSynapses()) {

                    Float weightDelta = weightDeltaError(
                            learningSynapse.getValue(),
                            learningSynapse.getWeight(),
                            neuron.getWeightDelta(),
                            expectedOutput.get(2)
                    );

                    // ед. способ высчитать дельту для нейронов первого скрытого слоя
//                    learningSynapse.getConnectedAxon().sumWeightDelta(
//                            neuron.getWeightDelta() * learningSynapse.getWeight());

                    learningSynapse.getConnectedAxon().sumWeightDelta(weightDelta);

                    Float weightGradient = weightGradient(learningSynapse.getValue(), neuron.getWeightDelta());
                    Float weightShift = weightShift(E, weightGradient, alpha, learningSynapse.getPreviousWeightShift());
                    learningSynapse.increaseWeight(weightShift);
                }
            }

            // Последний шаг - пересчитать веса первого скрытого слоя
            for (Neuron neuron : firstImageLayer.getNeurons()) {

                for (Synapse learningSynapse : neuron.getSynapses()) {

                    Float weightGradient = weightGradient(learningSynapse.getValue(), neuron.getWeightDelta());
                    Float weightShift = weightShift(E, weightGradient, alpha, learningSynapse.getPreviousWeightShift());
                    learningSynapse.increaseWeight(weightShift);
                }
            }

            System.out.println(String.format("======================= Step %f =======================", i + 0.0f));
            for (int p = 0; p < 10; p++) {
                System.out.println(String.format("expected result is %f, in fact: %f", expectedOutput.get(p), neuralNetworkResult.get(p)));
            }

        }
    }

    public List<Float> imageNeuralNetResult(List<Float> onInput) {

        // Передаём значения в первый слой
        try {
            firstImageLayer.transferValues(onInput.toArray(Float[]::new));
        } catch (TooManyInputValues e) {
            System.out.println("ERROR: (first lvl) " + e.getMessage());
        }

        // Активируем первый и сразу закидываем результаты во второй
        try {
            firstImageLayer.activateNeurons();
            List<Float> firstLayerResult = firstImageLayer.getResultList();
            secondImageLayer.transferValues(firstLayerResult.toArray(Float[]::new));
        } catch (TooManyInputValues e) {
            System.out.println("ERROR: (second lvl) " + e.getMessage());
        }

        // И в последний слой
        try {
            secondImageLayer.activateNeurons();
            List<Float> secondLayerResult = secondImageLayer.getResultList();
            outputImageLayer.transferValues(secondLayerResult.toArray(Float[]::new));
        } catch (TooManyInputValues e) {
            System.out.println("ERROR: (output lvl) " + e.getMessage());
        }

        outputImageLayer.activateNeurons();
        // Итоговый результат
        return outputImageLayer.getResultList();
    }

    public void startLearning() {

        // Заготавливаются входные значения
        List<Float> inputValues = new ArrayList<>();
        inputValues.add(1.0f);
        inputValues.add(0.0f);

        // Заранее исвестные результаты
        List<Float> expectedOutput = new ArrayList<>();
        expectedOutput.add(1.0f);

        // Итерации обучения
        for (int i = 0; i < 100; i++) {

            // Вычисляю выход нейронной сети
            List<Float> neuralNetworkResult = neuralNetResult(inputValues);
            // 1. Дельта ошибки выходного нейрона
            Float outputNeuralDelta = outputNeuronDelta(expectedOutput.get(0), neuralNetworkResult.get(0));

            // Беру все нейроны второго слоя
            for (Neuron neuron : secondRandomLayer.getNeurons()) {
                // И поехали по всем синапсам, учить их
                for (Synapse learningSynapse : neuron.getSynapses()) {
                    // Вычисляем дельту веса синапса
                    Float weightDelta = weightDeltaError(
                            learningSynapse.getConnectedAxon().getResult(),
                            learningSynapse.getWeight(),
                            outputNeuralDelta,
                            expectedOutput.get(0));

                    // Пеередаём дельту потомкам
                    learningSynapse.getConnectedAxon().setWeightDelta(weightDelta);

                    // Вычисление градиента для веса синапса
                    Float weightGradient = weightGradient(learningSynapse.getValue(), outputNeuralDelta);

                    // Величина изменения веса
                    Float weightShift = weightShift(E, weightGradient, alpha, learningSynapse.getPreviousWeightShift());
                    // и плюс, и минус нормальнно отработает
                    learningSynapse.increaseWeight(weightShift);
                }
            }

            for (Neuron neuron : firstRandomLayer.getNeurons()) {

                for (Synapse learningSynapse : neuron.getSynapses()) {

                    Float weightGradient = weightGradient(learningSynapse.getValue(), neuron.getWeightDelta());
                    Float weightShift = weightShift(E, weightGradient, alpha, learningSynapse.getPreviousWeightShift());
                    learningSynapse.increaseWeight(weightShift);
                }
            }

            System.out.println(String.format("Step %f, expected result is %f, in fact: %f", i + 0.0f, expectedOutput.get(0), neuralNetworkResult.get(0)));

        }

    }
/*
    /**
     * Представление нейронной сети как "чёрный ящик"
     *
     * @param inputValues    входы
     * @param expectedOutput соответствующие ожидаемые выходы
     * @return фактический выход нейронной сети
     *
    public List<Float> netWorkIteration(List<Float> inputValues, List<Float> expectedOutput) {

        List<Float> neuralNetResult = new ArrayList<>(neuralNetResult(inputValues));

        int i = 0;
        for (Float expected : expectedOutput) {
            try {
                System.out.println(String.format("Ожидаемый результат: %f, фактический: %f", expected, neuralNetResult.get(i++)));

            } catch (IndexOutOfBoundsException e) {
                System.out.println(String.format("Обнаружено несоответствие размерности ожидаемого и выходного вектора в нейронной сети (%d, %d)",
                        expectedOutput.size(), neuralNetResult.size()));
            }
        }

        // Особенность реализации обучения в том, что входные значения берутся прямо из синапса
        // т.е. выход нейрона на предыдущем слое - вход соединяющегося с ним синапса



//        Float ideal = expectedOutput.get(0);
//        Float output = neuralNetResult.get(0);
//
//        Float outputNeuronDelta = outputNeuronDelta(ideal, output);
//        System.out.println(String.format("Дельта выходного нейрона равна %f", outputNeuronDelta));
//
//        // =============================================================================================================
//        // =============================================================================================================
//        // =============================================================================================================
//
//        // Берём нейрон выходного слоя
//        List<Neuron> outputNeurons = secondRandomLayer.getNeurons();
//
//        // Берём синапс, который будет учиться
//        Synapse learningSynapse = outputNeurons.get(0).getSynapses().get(0);
//        // Вычисляем дельту веса синапса
//        Float weightDelta = weightDeltaError(
//                learningSynapse.getConnectedAxon().getResult(),
//                learningSynapse.getWeight(),
//                outputNeuronDelta,
//                ideal);
//
//        // Вычисление градиента для веса синапса
//        Float weightGradient = weightGradient(learningSynapse.getConnectedAxon().getResult(), outputNeuronDelta);
//
//        // Величина изменения веса
//        Float weightShift = weightShift(E, weightGradient, alpha, learningSynapse.getPreviousWeightShift());
//        // и плюс, и минус нормальнно отработает
//        learningSynapse.increaseWeight(weightShift);
//
//        System.out.println(learningSynapse.getWeight());

        return neuralNetResult;
    }
*/
    /**
     * Метод обучит синапсы между слоями
     * Если смотреть на архитектуру сети, то outputLevel будет справа,
     * а inputLayer - слева.
     * Данный меотд берёт синапсы нейронов outputLevel, выход inputLayer
     * и пересчитывает веса для синапсов нейронов outputLevel
     * @param outputLevel - слой "справа"
     * @param inputLayer - слой "слева"
     */
/*
    /**
     * Запускает процессы
     *
    public void start() {

        List<Float> inputValues = new ArrayList<>();
        inputValues.add(1.0f);
        inputValues.add(0.0f);

        List<Float> expectedOutput = new ArrayList<>();
        expectedOutput.add(1.0f);

        List<Float> neuralNetResult = netWorkIteration(inputValues, expectedOutput);

        int i = 0;
        for (Float expected : expectedOutput) {
            try {
                System.out.println(String.format("Ожидаемый результат: %f, фактический: %f", expected, neuralNetResult.get(i++)));

            } catch (IndexOutOfBoundsException e) {
                System.out.println(String.format("Обнаружено несоответствие размерности ожидаемого и выходного вектора в нейронной сети (%d, %d)",
                        expectedOutput.size(), neuralNetResult.size()));
            }
        }

        System.out.println("TheEnd");
    }
*/
}
