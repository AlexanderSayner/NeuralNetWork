package sayner.sandbox.neuralG.neurons;

import sayner.sandbox.neuralG.neurons.impl.LayerImpl;
import sayner.sandbox.neuralG.neurons.impl.NeuronImpl;
import sayner.sandbox.neuralG.neurons.impl.SynapseImpl;
import sayner.sandbox.neuralG.neurons.observers.listeners.SynapseCompositeListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class XorNetBuilder {

    /**
     * Вычисляет случайное значение веса для синапса
     *
     * @return число в промежутке [ -0.5f ; 0.5f )
     */
    private static float randomWeight() {
        return ThreadLocalRandom.current().nextFloat() - 0.5f;
    }

    public static LayerImpl createRandomWeightFirstLayer() {

        // Входные синапсы
//        Synapse w1 = new SynapseImpl(randomWeight());
//        Synapse w2 = new SynapseImpl(randomWeight());
//        Synapse w3 = new SynapseImpl(randomWeight());
//        Synapse w4 = new SynapseImpl(randomWeight());
        Synapse w1 = new SynapseImpl(0.45f);
        Synapse w2 = new SynapseImpl(0.78f);
        Synapse w3 = new SynapseImpl(-0.12f);
        Synapse w4 = new SynapseImpl(0.13f);

        // Создаём нейрон, присоединяем к нему входные синапсы
        // Входной нейрон 1
        Neuron neuron1Layer1 = new NeuronImpl(w1,w3);
        Neuron neuron2Layer1 = new NeuronImpl(w2,w4);

        // Надо передать синапсам значения
        // Этот клас позволит одновременно передать значения множеству синапсов
        SynapseCompositeListener x1 = new SynapseCompositeListener();
        // Синапсы "присоединяются" к одной входной точке
        x1.add(w1, w2);

        // Есть и второй вход, передаём данные синапсам и от него
        SynapseCompositeListener x2 = new SynapseCompositeListener();
        x2.add(w3, w4);

        // Учитывая порядок добавления нейронов "сверху-вниз"
        List<Neuron> neurons = new ArrayList<>();
        neurons.add(neuron1Layer1);
        neurons.add(neuron2Layer1);

        // При передаче параметров никакой порядок уже не важен
        LayerImpl layer = new LayerImpl(neurons, x1, x2);
        return layer;
    }

    public static LayerImpl createRandomWeightSecondLayer() {

        // Входные синапсы второго слоя
//        Synapse out1 = new SynapseImpl(randomWeight());
//        Synapse out2 = new SynapseImpl(randomWeight());
        Synapse out1 = new SynapseImpl(1.5f);
        Synapse out2 = new SynapseImpl(-2.3f);

        // Третий нейрон, выходной
        // Синапсы у него - выходы с первого слоя
        Neuron neuron1Layer2 = new NeuronImpl(out1, out2);

        // Выход первого нейрона первого слоя
        SynapseCompositeListener neuron1Layer1Output = new SynapseCompositeListener();
        neuron1Layer1Output.add(out1);

        // Взять выход со второго нейрона первого слоя
        SynapseCompositeListener neuron2Layer1Output = new SynapseCompositeListener();
        neuron2Layer1Output.add(out2);

        List<Neuron> neurons = new ArrayList<>();
        neurons.add(neuron1Layer2);

        LayerImpl layer = new LayerImpl(neurons, neuron1Layer1Output, neuron2Layer1Output);
        return layer;
    }

    // TODO: как-то задокументировать в виде картинки
//    public static LayerImpl createFirstLayerXOR() {
//
//        // Входные синапсы
//        Synapse w1 = new SynapseImpl(1.0f);
//        Synapse w2 = new SynapseImpl(-1.0f);
//        Synapse w3 = new SynapseImpl(-1.0f);
//        Synapse w4 = new SynapseImpl(1.0f);
//
//        // Создаём нейрон, присоединяем к нему входные синапсы
//        // Входной нейрон 1
//        Neuron neuron1Layer1 = new NeuronImpl(w1, w3);
//        Neuron neuron2Layer1 = new NeuronImpl(w2, w4);
//
//        // Надо передать синапсам значения
//        // Этот клас позволит одновременно передать значения множеству синапсов
//        SynapseCompositeListener x1 = new SynapseCompositeListener();
//        // Синапсы "присоединяются" к одной входной точке
//        x1.add(w1, w2);
//
//        // Есть и второй вход, передаём данные синапсам и от него
//        SynapseCompositeListener x2 = new SynapseCompositeListener();
//        x2.add(w3, w4);
//
//        // Учитывая порядок добавления нейронов "сверху-вниз"
//        List<Neuron> neurons = new ArrayList<>();
//        neurons.add(neuron1Layer1);
//        neurons.add(neuron2Layer1);
//
//        // При передаче параметров никакой порядок уже не важен
//        LayerImpl layer = new LayerImpl(neurons, x1, x2);
//        return layer;
//    }
//
//    public static LayerImpl createSecondLayerXOR() {
//
//        // Входные синапсы второго слоя
//        Synapse out1 = new SynapseImpl(1.0f);
//        Synapse out2 = new SynapseImpl(1.0f);
//
//        // Третий нейрон, выходной
//        // Синапсы у него - выходы с первого слоя
//        Neuron neuron1Layer2 = new NeuronImpl(out1, out2);
//
//        // Выход первого нейрона первого слоя
//        SynapseCompositeListener neuron1Layer1Output = new SynapseCompositeListener();
//        neuron1Layer1Output.add(out1);
//
//        // Взять выход со второго нейрона первого слоя
//        SynapseCompositeListener neuron2Layer1Output = new SynapseCompositeListener();
//        neuron2Layer1Output.add(out2);
//
//        List<Neuron> neurons = new ArrayList<>();
//        neurons.add(neuron1Layer2);
//
//        LayerImpl layer = new LayerImpl(neurons, neuron1Layer1Output, neuron2Layer1Output);
//        return layer;
//    }
}
