package sayner.sandbox.neuralG;

import sayner.sandbox.neuralG.neurons.NeuralNet;
import sayner.sandbox.neuralG.neurons.tools.ImageTool;

import java.io.IOException;
import java.util.List;

public class NetLogic {

    public static void start(){
        NeuralNet neuralNet = new NeuralNet();
        neuralNet.startImageLearning();

        // Проверка результата обучения нейронной сети
        ImageTool[] imageTool = new ImageTool[10];
        try {
            imageTool[0] = new ImageTool("./src/main/resources/img/test/Zero-3.jpg");
            imageTool[1] = new ImageTool("./src/main/resources/img/test/One.jpg");
            imageTool[2] = new ImageTool("./src/main/resources/img/test/Two.jpg");
            imageTool[3] = new ImageTool("./src/main/resources/img/test/Three-2.jpg");
            imageTool[4] = new ImageTool("./src/main/resources/img/test/Four.jpg");
            imageTool[5] = new ImageTool("./src/main/resources/img/test/Five-3.jpg");
            imageTool[6] = new ImageTool("./src/main/resources/img/test/Six-2.jpg");
            imageTool[7] = new ImageTool("./src/main/resources/img/test/Seven-2.jpg");
            imageTool[8] = new ImageTool("./src/main/resources/img/test/Eight-3.jpg");
            imageTool[9] = new ImageTool("./src/main/resources/img/test/Nine.jpg");
        } catch (IOException e) {
            System.out.println("Reading file error: " + e.getMessage());
            return;
        }


        for (int i = 0; i < 10; i++) {
            List<Float> inputValues = imageTool[i].createInputArray();
            List<Float> neuralNetworkResult = neuralNet.imageNeuralNetResult(inputValues);
            System.out.println(String.format("======================= Число %f =======================", i + 0.0f));
            System.out.print("t =  { ");
            for (Float result : neuralNetworkResult) {
                if (!neuralNetworkResult.get(9).equals(result)) {
                    System.out.print(String.format("%f; ", result));
                } else {
                    System.out.print(String.format("%f", result));
                }
            }
            System.out.println("}");
        }
    }
}
