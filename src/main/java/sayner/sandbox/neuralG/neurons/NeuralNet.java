package sayner.sandbox.neuralG.neurons;

import sayner.sandbox.neuralG.exceptions.TooManyInputValues;
import sayner.sandbox.neuralG.neurons.math.NeuronMath;
import sayner.sandbox.neuralG.neurons.tools.ImageTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class NeuralNet {

    Float E = 0.3f; // Скорость обучения

    private final Layer firstImageLayer;
    private final Layer secondImageLayer;
    private final Layer outputImageLayer;

    public NeuralNet() {

        firstImageLayer = NeuralNetBuilder.createFirstLayer();
        secondImageLayer = NeuralNetBuilder.createSecondLayer(firstImageLayer);
        outputImageLayer = NeuralNetBuilder.createOutputLayer(secondImageLayer);
    }

    public void startImageLearning() {

        ImageTool[] imageTool1 = new ImageTool[10];
        ImageTool[] imageTool2 = new ImageTool[10];
        ImageTool[] imageTool3 = new ImageTool[10];
        try {
            imageTool1[0] = new ImageTool("./src/main/resources/img/test/Zero.jpg");
            imageTool1[1] = new ImageTool("./src/main/resources/img/test/One.jpg");
            imageTool1[2] = new ImageTool("./src/main/resources/img/test/Two.jpg");
            imageTool1[3] = new ImageTool("./src/main/resources/img/test/Three.jpg");
            imageTool1[4] = new ImageTool("./src/main/resources/img/test/Four.jpg");
            imageTool1[5] = new ImageTool("./src/main/resources/img/test/Five.jpg");
            imageTool1[6] = new ImageTool("./src/main/resources/img/test/Six.jpg");
            imageTool1[7] = new ImageTool("./src/main/resources/img/test/Seven.jpg");
            imageTool1[8] = new ImageTool("./src/main/resources/img/test/Eight.jpg");
            imageTool1[9] = new ImageTool("./src/main/resources/img/test/Nine.jpg");
            imageTool3[0] = new ImageTool("./src/main/resources/img/test/Zero-3.jpg");
            imageTool3[1] = new ImageTool("./src/main/resources/img/test/One-3.jpg");
            imageTool3[2] = new ImageTool("./src/main/resources/img/test/Two-3.jpg");
            imageTool3[3] = new ImageTool("./src/main/resources/img/test/Three-3.jpg");
            imageTool3[4] = new ImageTool("./src/main/resources/img/test/Four-3.jpg");
            imageTool3[5] = new ImageTool("./src/main/resources/img/test/Five-3.jpg");
            imageTool3[6] = new ImageTool("./src/main/resources/img/test/Six-3.jpg");
            imageTool3[7] = new ImageTool("./src/main/resources/img/test/Seven-3.jpg");
            imageTool3[8] = new ImageTool("./src/main/resources/img/test/Eight-3.jpg");
            imageTool3[9] = new ImageTool("./src/main/resources/img/test/Nine-3.jpg");
            imageTool2[0] = new ImageTool("./src/main/resources/img/test/Zero-2.jpg");
            imageTool2[1] = new ImageTool("./src/main/resources/img/test/One-2.jpg");
            imageTool2[2] = new ImageTool("./src/main/resources/img/test/Two-2.jpg");
            imageTool2[3] = new ImageTool("./src/main/resources/img/test/Three-2.jpg");
            imageTool2[4] = new ImageTool("./src/main/resources/img/test/Four-2.jpg");
            imageTool2[5] = new ImageTool("./src/main/resources/img/test/Five-2.jpg");
            imageTool2[6] = new ImageTool("./src/main/resources/img/test/Six-2.jpg");
            imageTool2[7] = new ImageTool("./src/main/resources/img/test/Seven-2.jpg");
            imageTool2[8] = new ImageTool("./src/main/resources/img/test/Eight-2.jpg");
            imageTool2[9] = new ImageTool("./src/main/resources/img/test/Nine-2.jpg");
        } catch (IOException e) {
            System.out.println("Reading file error: " + e.getMessage());
            return;
        }

//        Random random = new Random();

        // Итерации обучения
        for (int i = 0; i < 50000; i++) {

            // Берём случайное значение из тестовой выборки
//            int lot = random.nextInt(10);


            int selection = i % 30;
            int lot = selection % 10;

            List<Float> inputValues;
            if (selection < 10) {
                inputValues = imageTool1[lot].createInputArray();
            } else if (selection < 20) {
                inputValues = imageTool2[lot].createInputArray();
            } else {
                inputValues = imageTool3[lot].createInputArray();
            }


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
