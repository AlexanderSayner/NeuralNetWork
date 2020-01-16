package sayner.sandbox.neuralG.neurons;

import sayner.sandbox.neuralG.neurons.impl.Bias;
import sayner.sandbox.neuralG.neurons.impl.LayerImpl;
import sayner.sandbox.neuralG.neurons.impl.NeuronImpl;
import sayner.sandbox.neuralG.neurons.impl.SynapseImpl;
import sayner.sandbox.neuralG.neurons.observers.listeners.SynapseCompositeListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Алгоритмы постройки стоёв нейронной сети
 */
public final class NeuralNetBuilder {

    /**
     * Вычисляет случайное значение веса для синапса
     *
     * @return число в промежутке [ -0.5f ; 0.5f )
     */
    private static float randomWeight() {
        return (ThreadLocalRandom.current().nextFloat()) * 0.5f - 0.25f;
    }

    /**
     * Генерация первого скрытого слоя
     * 784 входа, 128 нейронов
     *
     * @return готовый слой
     */
    public static Layer createFirstLayer() {

        List<Neuron> neurons = new ArrayList<>();

        // Нейроны первого скрытого слоя
        for (int i = 0; i < 128; i++) {

            List<Synapse> synapses = new ArrayList<>();
            for (int s = 0; s < 28 * 28; s++) {
                synapses.add(new SynapseImpl(randomWeight()));
            }
            Neuron neuron = new NeuronImpl(synapses.toArray(Synapse[]::new));
            neurons.add(neuron);
        }

        // Входных сигналов будет 784, следовательно и наблюдателей столько же
        SynapseCompositeListener[] observers = new SynapseCompositeListener[28 * 28];
        for (int i = 0; i < 28 * 28; i++) {
            observers[i] = new SynapseCompositeListener();
            // 1-й вход соответсвует каждому 1-му синапсу всех нейронов следующего слоя и т.д.
            for (Neuron neuron : neurons) {
                observers[i].add(neuron.getSynapses().get(i));
            }
        }

        // #128
        Neuron bias = new Bias();
        neurons.add(bias);

        return new LayerImpl(neurons, observers);
    }

    /**
     * Генерация второго скрытого слоя
     * 32 входа, 32 нейрона
     *
     * @param firstLayer первый слой
     * @return готовый слой
     */
    public static Layer createSecondLayer(Layer firstLayer) {

        // Нейроны второго скрытого слоя
        List<Neuron> neurons = new ArrayList<>();
        // Каждый нейрон второго слоя
        for (int i = 0; i < 32; i++) {

            List<Synapse> synapses = new ArrayList<>();
            // Соединён с каждым нейроном предыдущего слоя
            for (int s = 0; s < 128; s++) {
                synapses.add(new SynapseImpl(randomWeight(), firstLayer.getNeurons().get(s)));
            }

            // Генерация синапсов для нейрона смещения на предыдущем слое
            Synapse bias = new SynapseImpl(randomWeight(), firstLayer.getNeurons().get(128));
            synapses.add(bias);

            Neuron neuron = new NeuronImpl(synapses.toArray(Synapse[]::new));
            neurons.add(neuron);
        }

        // Входных сигналов c первого слоя 128, следовательно и наблюдателей столько же
        SynapseCompositeListener[] observers = new SynapseCompositeListener[128];
        for (int i = 0; i < 128; i++) {
            observers[i] = new SynapseCompositeListener();
            // 1-й нейрон соединён с 1-м синапсом всем нейронов второго слоя, 2-й со 2-м и т.д.
            for (Neuron neuron : neurons) {
                observers[i].add(neuron.getSynapses().get(i));
            }
        }

        // #32
        Neuron bias = new Bias();
        neurons.add(bias);

        return new LayerImpl(neurons, observers);
    }

    public static Layer createOutputLayer(Layer secondLayer) {

        // Нейроны выходного слоя
        List<Neuron> neurons = new ArrayList<>();
        // Каждый нейрон выходного слоя
        for (int i = 0; i < 10; i++) {

            List<Synapse> synapses = new ArrayList<>();
            // Соединён с каждым нейроном предыдущего скрытого слоя
            for (int s = 0; s < 32; s++) {
                synapses.add(new SynapseImpl(randomWeight(), secondLayer.getNeurons().get(s)));
            }

            // Генерация синапсов для нейрона смещения на предыдущем слое
            Synapse bias = new SynapseImpl(randomWeight(), secondLayer.getNeurons().get(32));
            synapses.add(bias);

            Neuron neuron = new NeuronImpl(synapses.toArray(Synapse[]::new));
            neurons.add(neuron);
        }

        // Входных сигналов со второго слоя 32, следовательно и наблюдателей столько же
        SynapseCompositeListener[] observers = new SynapseCompositeListener[32];
        for (int i = 0; i < 32; i++) {
            observers[i] = new SynapseCompositeListener();
            // 1-й нейрон соединён с 1-м синапсом всем нейронов второго слоя, 2-й со 2-м и т.д.
            for (Neuron neuron : neurons) {
                observers[i].add(neuron.getSynapses().get(i));
            }
        }

        return new LayerImpl(neurons, observers);
    }
}
