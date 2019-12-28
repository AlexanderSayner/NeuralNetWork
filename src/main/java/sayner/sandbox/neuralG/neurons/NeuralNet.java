package sayner.sandbox.neuralG.neurons;

import sayner.sandbox.neuralG.exceptions.TooManyInputValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class NeuralNet {

    Float E = 0.7f; // Скорость обучения
    Float alpha = 0.3f; // Момент

    //    private final Layer firstLayer;
//    private final Layer secondLayer;
    private final Layer firstRandomLayer;
    private final Layer secondRandomLayer;

    public NeuralNet() {

//        firstLayer = XorNetBuilder.createFirstLayerXOR();
//        secondLayer = XorNetBuilder.createSecondLayerXOR();
        firstRandomLayer = XorNetBuilder.createRandomWeightFirstLayer();
        secondRandomLayer = XorNetBuilder.createRandomWeightSecondLayer();
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
            List<Float> firstLayerResult = firstRandomLayer.activateNeurons();
            secondRandomLayer.transferValues(firstLayerResult.toArray(Float[]::new));
        } catch (TooManyInputValues e) {
            System.out.println("ERROR: (second lvl) " + e.getMessage());
        }

        // Итоговый результат
        return secondRandomLayer.activateNeurons();
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

        Float shift = E * weightGradient + alpha * previousWeightShift;
        return shift;
    }

    private void synapsesLearning(Map<Synapse, Float> synapseAndStepDelta, Float outputNeuralDelta, Float expectedOutput) {

        for(Map.Entry<Synapse,Float> synapseAndStepDeltaEntry:synapseAndStepDelta.entrySet()){
            Synapse synapse=synapseAndStepDeltaEntry.getKey();
            Float previousDeltaWeight=synapseAndStepDeltaEntry.getValue();

            // 1) Считаю дельту ошибки
            Float weightDeltaError=weightDeltaError(synapse.getValue(),synapse.getWeight(),outputNeuralDelta,expectedOutput);
            // 2) Градиент
            Float gradient=weightGradient(synapse.getValue(),outputNeuralDelta);
            // 3) Корректировка веса
            Float weightShift=weightShift(E,gradient,alpha,previousDeltaWeight);

            synapse.increaseWeight(weightShift);
            synapseAndStepDeltaEntry.setValue(weightShift);
        }
    }

    private void startLearning() {

        // Заготавливаются входные значения
        List<Float> inputValues = new ArrayList<>();
        inputValues.add(1.0f);
        inputValues.add(0.0f);

        // Заранее исвестные результаты
        List<Float> expectedOutput = new ArrayList<>();
        expectedOutput.add(1.0f);

        // Задаётся скорость обучения
        Float epsilon = 0.7f;
        Float alpha = 0.3f; // Момент

        // Итерации обучения
        for (int i = 0; i < 1; i++) {
            List<Float> neuralNetworkResult = netWorkIteration(inputValues, expectedOutput);
            Float outputNeuralDelta = outputNeuronDelta(expectedOutput.get(0), neuralNetworkResult.get(0));
        }

    }

    /**
     * Представление нейронной сети как "чёрный ящик"
     *
     * @param inputValues    входы
     * @param expectedOutput соответствующие ожидаемые выходы
     * @return фактический выход нейронной сети
     */
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

        Float ideal = expectedOutput.get(0);
        Float output = neuralNetResult.get(0);

        Float outputNeuronDelta = outputNeuronDelta(ideal, output);
        System.out.println(String.format("Дельта выходного нейрона равна %f", outputNeuronDelta));

        // Берём нейрон выходного слоя
        List<Neuron> outputNeurons = secondRandomLayer.getNeurons();
        List<Synapse> synapsesForSecondLevel = outputNeurons.get(0).getSynapses();

        // Результат рабюоты первого слоя
        List<Float> listOfTheFirstLayerOutputResults = firstRandomLayer.getResultValuesList();

        // Вычисление дельты для веса синапса нейронов второго слоя
        Float secondLayerResult = listOfTheFirstLayerOutputResults.get(0);
        // Берём синапс, который будет учиться
        Synapse learningSynapse = synapsesForSecondLevel.get(0);
        // Вычисляем дельту веса синапса
        Float weightDelta = weightDeltaError(secondLayerResult, learningSynapse.getWeight(), outputNeuronDelta, ideal);

        // Вычисление градиента для веса синапса
        Float weightGradient = weightGradient(listOfTheFirstLayerOutputResults.get(0), outputNeuronDelta);


        Float previousWeightShift = 0.0f; // Предыдущий сдвиг

        // Величина изменения веса
        Float weightShift = weightShift(E, weightGradient, alpha, previousWeightShift);
        // и плюс, и минус нормальнно отработает
        learningSynapse.increaseWeight(weightShift);

        System.out.println(learningSynapse.getWeight());

        return neuralNetResult;
    }

    /**
     * Запускает процессы
     */
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

}
