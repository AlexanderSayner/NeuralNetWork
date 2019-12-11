package sayner.sandbox.neuralG.neurons.impl;

import sayner.sandbox.neuralG.neurons.NeuralNet;
import sayner.sandbox.neuralG.neurons.Neuron;
import sayner.sandbox.neuralG.neurons.Synapse;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetImpl implements NeuralNet {

    private NeuralBuilder neuralBuilder;

    public NeuralNetImpl() {
        neuralBuilder=new NeuralBuilder();
    }

    public NeuralNetImpl(NeuralBuilder neuralBuilder) {
        this.neuralBuilder = neuralBuilder;
    }

    @Override
    public void start() {

        List<Float> input = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            input.add((float) i);
//        }
//
//        Layer layer = new LayerImpl();
//        layer.onInput(input, input.size());
//        List<Float> layerOutput = layer.onOutput();

        input.add(1.0f);
        input.add(1.0f);

        //-------------------------------------------------------------
        // Создать синапсы
        Synapse x1=new SynapseImpl(1.0f);
        Synapse x2=new SynapseImpl(1.0f);

        // передать им значения
        x1.transferValue(input.get(0));
        x2.transferValue(input.get(1));

        // Создать нейроны, присоединить к ним синапсы
        Neuron input1=new NeuronImpl(x1);
        Neuron input2=new NeuronImpl(x2);

        // организовать слой нейронов
        List<Neuron> neurons1=new ArrayList<>();
        neurons1.add(input1);
        neurons1.add(input2);

        // взять выходные значения
        List<Float> result=new ArrayList<>();
        for (Neuron neuron:neurons1) {
            float y=neuron.activate();
            result.add(y);
        }

        //-------------------------------------------------------------

        //-------------------------------------------------------------
        Synapse w1=new SynapseImpl(1.0f);
        Synapse w2=new SynapseImpl(-1.0f);
        Synapse w3=new SynapseImpl(-1.0f);
        Synapse w4=new SynapseImpl(1.0f);

        w1.transferValue(result.get(0));
        w2.transferValue(result.get(0));
        w3.transferValue(result.get(1));
        w4.transferValue(result.get(1));

        Neuron neuron1=new NeuronImpl(w1,w3);
        Neuron neuron2=new NeuronImpl(w2,w4);

        List<Neuron> neurons2=new ArrayList<>();
        neurons2.add(neuron1);
        neurons2.add(neuron2);

        result=new ArrayList<>();
        for (Neuron neuron:neurons2){
            result.add(neuron.activate());
        }
        //-------------------------------------------------------------

        //-------------------------------------------------------------
        Synapse out1=new SynapseImpl(1.0f);
        Synapse out2=new SynapseImpl(1.0f);

        out1.transferValue(result.get(0));
        out2.transferValue(result.get(1));

        Neuron neuron3=new NeuronImpl(out1,out2);

        List<Neuron> neurons3=new ArrayList<>();
        neurons3.add(neuron3);

        result=new ArrayList<>();
        for (Neuron neuron:neurons3){
            result.add(neuron.activate());
        }
        //-------------------------------------------------------------

        result.forEach(System.out::println);
    }
}
