package sayner.sandbox.neuralG.neurons;

import sayner.sandbox.neuralG.neurons.impl.Bias;
import sayner.sandbox.neuralG.neurons.impl.LayerImpl;
import sayner.sandbox.neuralG.neurons.impl.NeuronImpl;
import sayner.sandbox.neuralG.neurons.impl.SynapseImpl;
import sayner.sandbox.neuralG.neurons.observers.listeners.SynapseCompositeListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class XorNetBuilder {

    /**
     * Вычисляет случайное значение веса для синапса
     *
     * @return число в промежутке [ -0.5f ; 0.5f )
     */
    private static float randomWeight() {
        return ThreadLocalRandom.current().nextFloat() - 0.5f;
    }

//    public static Layer createFirstLayer() {
//
//        // Нейроны первого скрытого слоя
//        List<Neuron> neurons = new ArrayList<>();
//        // Кадый нейрон первого скрытого слоя соединён с каждым входом
//        for (int hiddenN1 = 0; hiddenN1 < 16; hiddenN1++) {
//            // Генерация синапсов под каждый пиксель картинки
//            Synapse[] synapses = new Synapse[28 * 28];
//            for (int i = 0; i < 28 * 28; i++) {
//                synapses[i] = new SynapseImpl(randomWeight());
//            }
//            Neuron neuron = new NeuronImpl(synapses);
//            neurons.add(neuron);
//        }
//
//        // Входы нейросети
//        SynapseCompositeListener[] listeners = new SynapseCompositeListener[28 * 28];
//        for (int i = 0; i < 28 * 28; i++) {
//            listeners[i] = new SynapseCompositeListener();
//            for (Neuron neuron : neurons) {
//                listeners[i].add(neuron.getSynapses().get(i));
//            }
//        }
//
//        return new LayerImpl(neurons, listeners);
//    }
//
//    public static Layer createSecondLayer(Layer firstLayer) {
//
//        // Нейроны второго скрытого слоя
//        List<Neuron> neurons = new ArrayList<>();
//        // Соединение "каждый с каждым"
//        for (int hiddenN2 = 0; hiddenN2 < 16; hiddenN2++) {
//            Synapse[] synapses = new Synapse[16];
//            for (int i = 0; i < 16; i++) {
//                synapses[i] = new SynapseImpl(randomWeight(), firstLayer.getNeurons().get(i));
//            }
//            Neuron neuron = new NeuronImpl(synapses);
//            neurons.add(neuron);
//        }
//
//        // Выходы первого с слоя
//        SynapseCompositeListener[] listeners = new SynapseCompositeListener[16];
//        for (int i = 0; i < 16; i++) {
//            listeners[i] = new SynapseCompositeListener();
//            for (Neuron neuron : neurons) {
//                listeners[i].add(neuron.getSynapses().get(i));
//            }
//        }
//
//        return new LayerImpl(neurons, listeners);
//    }
//
//    public static Layer createOutputLayer(Layer secondLayer) {
//
//        // Выходные нейроны
//        List<Neuron> neurons = new ArrayList<>();
//        for (int outN = 0; outN < 10; outN++) {
//            Synapse[] synapses = new Synapse[16];
//            for (int i = 0; i < 16; i++) {
//                synapses[i] = new SynapseImpl(randomWeight(), secondLayer.getNeurons().get(i));
//            }
//            Neuron out = new NeuronImpl(synapses);
//            neurons.add(out);
//        }
//
//        // Выходы со второго слоя
//        SynapseCompositeListener[] listeners = new SynapseCompositeListener[16];
//        for (int i = 0; i < 16; i++) {
//            listeners[i] = new SynapseCompositeListener();
//            for (Neuron neuron : neurons) {
//                listeners[i].add(neuron.getSynapses().get(i));
//            }
//        }
//
//        return new LayerImpl(neurons, listeners);
//    }

    public static Layer createFirstLayer() {

        // Нейроны первого скрытого слоя
        List<Neuron> neurons = new ArrayList<>();
        Synapse synapse1 = new SynapseImpl(-1.0f);
        Synapse synapse2 = new SynapseImpl(1.0f);
        Synapse synapse3 = new SynapseImpl(1.0f);
        Synapse synapse4 = new SynapseImpl(2.0f);
        Neuron neuron1 = new NeuronImpl(synapse1, synapse3);
        Neuron neuron2 = new NeuronImpl(synapse2, synapse4);
        Neuron bias=new Bias();
        neurons.add(neuron1);
        neurons.add(neuron2);
        neurons.add(bias);

        SynapseCompositeListener[] observers = new SynapseCompositeListener[2];
        observers[0] = new SynapseCompositeListener();
        observers[0].add(synapse1, synapse2);
        observers[1] = new SynapseCompositeListener();
        observers[1].add(synapse3, synapse4);

        return new LayerImpl(neurons, observers);
    }

    public static Layer createSecondLayer(Layer firstLayer) {

        // Нейроны второго скрытого слоя
        List<Neuron> neurons = new ArrayList<>();
        Synapse synapse1 = new SynapseImpl(-1.0f, firstLayer.getNeurons().get(0));
        Synapse synapse2 = new SynapseImpl(1.0f, firstLayer.getNeurons().get(0));
        Synapse synapse3 = new SynapseImpl(2.0f, firstLayer.getNeurons().get(1));
        Synapse synapse4 = new SynapseImpl(1.0f, firstLayer.getNeurons().get(1));
        Synapse synapse5 = new SynapseImpl(-2.0f, firstLayer.getNeurons().get(1));
        Synapse synapse6 = new SynapseImpl(3.0f, firstLayer.getNeurons().get(0));
        Neuron neuron1 = new NeuronImpl(synapse1, synapse3);
        Neuron neuron2 = new NeuronImpl(synapse2, synapse4);
        Neuron neuron3 = new NeuronImpl(synapse5, synapse6);
        Synapse biasSynapse1=new SynapseImpl(0.2f,firstLayer.getNeurons().get(2));
        Synapse biasSynapse2=new SynapseImpl(-0.2f,firstLayer.getNeurons().get(2));
        Synapse biasSynapse3=new SynapseImpl(0.2f,firstLayer.getNeurons().get(2));
        neuron1.addNewSynapse(biasSynapse1);
        neuron2.addNewSynapse(biasSynapse2);
        neuron3.addNewSynapse(biasSynapse3);
        Neuron bias=new Bias();
        neurons.add(neuron1);
        neurons.add(neuron2);
        neurons.add(neuron3);
        neurons.add(bias);

        // Выходы первого с слоя
        SynapseCompositeListener[] listeners = new SynapseCompositeListener[3];
        listeners[0] = new SynapseCompositeListener();
        listeners[0].add(synapse1, synapse2, synapse6);
        listeners[1] = new SynapseCompositeListener();
        listeners[1].add(synapse3, synapse4, synapse5);
        listeners[2]=new SynapseCompositeListener();
        listeners[2].add(biasSynapse1,biasSynapse2,biasSynapse3);

        return new LayerImpl(neurons, listeners);
    }

//    public static Layer createSecondLayer() {
//
//        // Нейроны второго скрытого слоя
//        List<Neuron> neurons = new ArrayList<>();
//        Synapse synapse1 = new SynapseImpl(-1.0f);
//        Synapse synapse2 = new SynapseImpl(1.0f);
//        Synapse synapse3 = new SynapseImpl(2.0f);
//        Synapse synapse4 = new SynapseImpl(1.0f);
//        Synapse synapse5 = new SynapseImpl(-2.0f);
//        Synapse synapse6 = new SynapseImpl(3.0f);
//        Neuron neuron1 = new NeuronImpl(synapse1, synapse3);
//        Neuron neuron2 = new NeuronImpl(synapse2, synapse4);
//        Neuron neuron3 = new NeuronImpl(synapse5, synapse6);
//        neurons.add(neuron1);
//        neurons.add(neuron2);
//        neurons.add(neuron3);
//
//        // Выходы первого с слоя
//        SynapseCompositeListener[] listeners = new SynapseCompositeListener[2];
//        listeners[0] = new SynapseCompositeListener();
//        listeners[0].add(synapse1, synapse2, synapse6);
//        listeners[1] = new SynapseCompositeListener();
//        listeners[1].add(synapse3, synapse4, synapse5);
//
//        return new LayerImpl(neurons, listeners);
//    }

//    public static Layer createOutputLayer(Layer secondLayer) {
//
//        // Выходные нейроны
//        List<Neuron> neurons = new ArrayList<>();
//        Synapse synapse1 = new SynapseImpl(1.0f, secondLayer.getNeurons().get(0));
//        Synapse synapse2 = new SynapseImpl(2.0f, secondLayer.getNeurons().get(1));
//        Synapse synapse3 = new SynapseImpl(4.0f, secondLayer.getNeurons().get(2));
//        Neuron neuron = new NeuronImpl(synapse1, synapse2, synapse3);
//        neurons.add(neuron);
//
//        // Выходы со второго слоя
//        SynapseCompositeListener[] listeners = new SynapseCompositeListener[3];
//        listeners[0] = new SynapseCompositeListener();
//        listeners[0].add(synapse1);
//        listeners[1] = new SynapseCompositeListener();
//        listeners[1].add(synapse2);
//        listeners[2] = new SynapseCompositeListener();
//        listeners[2].add(synapse3);
//
//        return new LayerImpl(neurons, listeners);
//    }

    public static Layer createOutputLayer(Layer secondLayer) {

        // Выходные нейроны
        List<Neuron> neurons = new ArrayList<>();
        Synapse synapse1 = new SynapseImpl(1.0f, secondLayer.getNeurons().get(0));
        Synapse synapse2 = new SynapseImpl(2.0f, secondLayer.getNeurons().get(1));
        Synapse synapse3 = new SynapseImpl(4.0f, secondLayer.getNeurons().get(2));
        Neuron neuron = new NeuronImpl(synapse1, synapse2, synapse3);
        Synapse biasSynapse=new SynapseImpl(-0.1f,secondLayer.getNeurons().get(3));
        neuron.addNewSynapse(biasSynapse);
        neurons.add(neuron);

        // Выходы со второго слоя
        SynapseCompositeListener[] listeners = new SynapseCompositeListener[4];
        listeners[0] = new SynapseCompositeListener();
        listeners[0].add(synapse1);
        listeners[1] = new SynapseCompositeListener();
        listeners[1].add(synapse2);
        listeners[2] = new SynapseCompositeListener();
        listeners[2].add(synapse3);
        listeners[3]=new SynapseCompositeListener();
        listeners[3].add(biasSynapse);

        return new LayerImpl(neurons, listeners);
    }
}
