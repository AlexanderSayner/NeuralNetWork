package sayner.sandbox.neuralG.neurons;

import sayner.sandbox.neuralG.exceptions.TooManyInputValues;
import sayner.sandbox.neuralG.neurons.math.NeuronMath;
import sayner.sandbox.neuralG.neurons.tools.ImageTool;

import java.io.IOException;
import java.util.*;

public final class NeuralNet {

    Float E = 0.7f; // Скорость обучения

    private final Layer firstImageLayer;
    private final Layer secondImageLayer;
    private final Layer outputImageLayer;

    public NeuralNet() {

        firstImageLayer = NeuralNetBuilder.createFirstLayer();
        secondImageLayer = NeuralNetBuilder.createSecondLayer(firstImageLayer);
        outputImageLayer = NeuralNetBuilder.createOutputLayer(secondImageLayer);
    }

    public void startImageLearning() {

        ImageTool[] imageTool = new ImageTool[10];
        try {
            imageTool[0] = new ImageTool("./src/main/resources/img/Zero.jpg");
            imageTool[1] = new ImageTool("./src/main/resources/img/One.jpg");
            imageTool[2] = new ImageTool("./src/main/resources/img/Two.jpg");
            imageTool[3] = new ImageTool("./src/main/resources/img/Three.jpg");
            imageTool[4] = new ImageTool("./src/main/resources/img/Four.jpg");
            imageTool[5] = new ImageTool("./src/main/resources/img/Five.jpg");
            imageTool[6] = new ImageTool("./src/main/resources/img/Six.jpg");
            imageTool[7] = new ImageTool("./src/main/resources/img/Seven.jpg");
            imageTool[8] = new ImageTool("./src/main/resources/img/Eight.jpg");
            imageTool[9] = new ImageTool("./src/main/resources/img/Nine.jpg");
        } catch (IOException e) {
            System.out.println("Reading file error: " + e.getMessage());
            return;
        }

//        Random random = new Random();

        // Итерации обучения
        for (int i = 0; i < 1000001; i++) {

            // Берём случайное значение из тестовой выборки
//            int lot = random.nextInt(10);

            int lot = i % 10;

            List<Float> inputValues = imageTool[lot].createInputArray();
            // если выпало 3, ожидаем, что 4-й выход = 1.0, а остальные - нулю
            List<Float> expectedOutput = new ArrayList<>();
            for (int foo = 0; foo < 10; foo++) {
                expectedOutput.add(foo == lot ? 1.0f : 0.0f);
            }

            if (inputValues.size() == 0 || expectedOutput.size() == 0) {
                System.out.println("Что-то пошло не так");
                return;
            }

            // Вычисляю выход нейронной сети
            List<Float> neuralNetworkResult = imageNeuralNetResult(inputValues);

            // Теперь я должен отдельно просчитать дельту ошибки для
            // каждого выходного значения нейронной сети
            int resultNumber = 0;
            for (Neuron outputNeuron : outputImageLayer.getNeurons()) {

                Float outputNeuralError = NeuronMath.outputError(
                        expectedOutput.get(resultNumber),
                        neuralNetworkResult.get(resultNumber),
                        outputNeuron.getInputValue()
                );

                for (Synapse learningSynapse : outputNeuron.getSynapses()) {

                    // Величина изменения веса
                    Float weightShift = NeuronMath.weightShift(E, outputNeuralError, learningSynapse.getConnectedAxon().getResult());

                    // Передажю на предыдущий слой слагаемое дельты ошибки нейрона
                    // При проходе по всем нейронам в цикле
                    // каждый нейрон
                    // по синапсу пройдёт в связанный с ним нейрон
                    // и положит туда часть суммы дельты ошибки
                    // В итоге каждый нейрон (10 шт по 16 синапсов в каждом) сделает свой вклад в вычисления
                    // и дельта нейронов на пред. слое будет вычислена правильно
                    learningSynapse.getConnectedAxon().sumWeightDelta(
                            outputNeuralError *
                                    learningSynapse.getWeight()
                    );

                    // и плюс, и минус нормальнно отработает
                    learningSynapse.increaseWeight(weightShift);
                }

                resultNumber++;
            }

            // Следующий шаг - изменить веса между первым и вторым скрытым слоем
            for (Neuron neuron : secondImageLayer.getNeurons()) {

                Float hiddenNeuralError = NeuronMath.hiddenError(
                        neuron.getNeuronErrorDelta(),
                        neuron.getInputValue()
                );

                for (Synapse learningSynapse : neuron.getSynapses()) {

                    Float weightShift = NeuronMath.weightShift(E, hiddenNeuralError, learningSynapse.getConnectedAxon().getResult());

                    learningSynapse.getConnectedAxon().sumWeightDelta(
                            hiddenNeuralError *
                                    learningSynapse.getWeight()
                    );

                    learningSynapse.increaseWeight(weightShift);
                }
            }

            // Последний шаг - пересчитать веса первого скрытого слоя
            for (Neuron neuron : firstImageLayer.getNeurons()) {

                Float hiddenNeuralError = NeuronMath.hiddenError(
                        neuron.getNeuronErrorDelta(),
                        neuron.getInputValue()
                );

                for (Synapse learningSynapse : neuron.getSynapses()) {

                    Float weightShift = NeuronMath.weightShift(E, hiddenNeuralError, learningSynapse.getValue());
                    learningSynapse.increaseWeight(weightShift);
                }
            }


            if (i % 1000 == 0 || i % 1000 == 1 || i % 1000 == 2 || i % 1000 == 3 || i % 1000 == 4 || i % 1000 == 5 || i % 1000 == 6 || i % 1000 == 7 || i % 1000 == 8 || i % 1000 == 9) {
                System.out.println(String.format("======================= Step %f =======================", i + 0.0f));
                for (int p = 0; p < 10; p++) {
                    System.out.println(String.format("expected result is %f, in fact: %f", expectedOutput.get(p), neuralNetworkResult.get(p)));
                }

            } else if (i >= 999990) {
                System.out.println(String.format("======================= Step %f =======================", i + 0.0f));
                for (int p = 0; p < 10; p++) {
                    System.out.println(String.format("expected result is %f, in fact: %f", expectedOutput.get(p), neuralNetworkResult.get(p)));
                }
            }

            for (Neuron neuron : outputImageLayer.getNeurons()) {
                neuron.setNeuronErrorDelta(0.0f);
            }
            for (Neuron neuron : secondImageLayer.getNeurons()) {
                neuron.setNeuronErrorDelta(0.0f);
            }
            for (Neuron neuron : firstImageLayer.getNeurons()) {
                neuron.setNeuronErrorDelta(0.0f);
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
}
