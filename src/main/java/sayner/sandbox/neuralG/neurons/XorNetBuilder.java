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
public final class XorNetBuilder {

    /**
     * Вычисляет случайное значение веса для синапса
     *
     * @return число в промежутке [ -0.5f ; 0.5f )
     */
    private static float randomWeight() {
        return (ThreadLocalRandom.current().nextFloat())*2 - 1.0f;
    }

    /**
     * Генерация первого скрытого слоя
     * 784 входа, 16 нейронов
     *
     * @return готовый слой
     */
    public static Layer createFirstLayer() {

        List<Neuron> neurons = new ArrayList<>();

        // Нейроны первого скрытого слоя
        for (int i = 0; i < 64; i++) {

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

        // #16
        Neuron bias = new Bias();
        neurons.add(bias);

        return new LayerImpl(neurons, observers);
    }

    /**
     * Генерация второго скрытого слоя
     * 16 входов, 16 нейронов
     *
     * @param firstLayer первый слой
     * @return готовый слой
     */
    public static Layer createSecondLayer(Layer firstLayer) {

        // Нейроны второго скрытого слоя
        List<Neuron> neurons = new ArrayList<>();
        // Каждый нейрон второго слоя
        for (int i = 0; i < 16; i++) {

            List<Synapse> synapses = new ArrayList<>();
            // Соединён с каждым нейроном предыдущего слоя
            for (int s = 0; s < 64; s++) {
                synapses.add(new SynapseImpl(randomWeight(), firstLayer.getNeurons().get(s)));
            }

            // Генерация синапсов для нейрона смещения на предыдущем слое
            Synapse bias = new SynapseImpl(randomWeight(), firstLayer.getNeurons().get(64));
            synapses.add(bias);

            Neuron neuron = new NeuronImpl(synapses.toArray(Synapse[]::new));
            neurons.add(neuron);
        }

        // Входных сигналов c первого слоя 16, следовательно и наблюдателей столько же
        SynapseCompositeListener[] observers = new SynapseCompositeListener[64];
        for (int i = 0; i < 64; i++) {
            observers[i] = new SynapseCompositeListener();
            // 1-й нейрон соединён с 1-м синапсом всем нейронов второго слоя, 2-й со 2-м и т.д.
            for (Neuron neuron : neurons) {
                observers[i].add(neuron.getSynapses().get(i));
            }
        }

        // #16
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
            for (int s = 0; s < 16; s++) {
                synapses.add(new SynapseImpl(randomWeight(), secondLayer.getNeurons().get(s)));
            }

            // Генерация синапсов для нейрона смещения на предыдущем слое
            Synapse bias = new SynapseImpl(randomWeight(), secondLayer.getNeurons().get(16));
            synapses.add(bias);

            Neuron neuron = new NeuronImpl(synapses.toArray(Synapse[]::new));
            neurons.add(neuron);
        }

        // Входных сигналов со второго слоя 16, следовательно и наблюдателей столько же
        SynapseCompositeListener[] observers = new SynapseCompositeListener[16];
        for (int i = 0; i < 16; i++) {
            observers[i] = new SynapseCompositeListener();
            // 1-й нейрон соединён с 1-м синапсом всем нейронов второго слоя, 2-й со 2-м и т.д.
            for (Neuron neuron : neurons) {
                observers[i].add(neuron.getSynapses().get(i));
            }
        }

        return new LayerImpl(neurons, observers);
    }
}
