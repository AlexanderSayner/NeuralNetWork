package sayner.sandbox.neuralG.neurons.impl;

import sayner.sandbox.neuralG.neurons.Layer;
import sayner.sandbox.neuralG.neurons.Neuron;
import sayner.sandbox.neuralG.neurons.Synapse;

import java.util.ArrayList;
import java.util.List;

public class NeuralBuilder {

    public NeuralBuilder() {
    }

    /**
     * Входной уровень нейронов. Из задача принимать внешние сигналы
     * и передавать дальше нейросети. Характеризуются еденичными весами их синапсов,
     * а также отсутствием возможности обучения
     * @return
     */
    public Layer getInputLayer(){

        Synapse x1=new SynapseImpl(1.0f);
        Synapse x2=new SynapseImpl(1.0f);

        Neuron input1=new NeuronImpl(x1);
        Neuron input2=new NeuronImpl(x2);

        List<Neuron> neurons=new ArrayList<>();
        neurons.add(input1);
        neurons.add(input2);

        return new LayerImpl(neurons);
    }

    public Layer getFirstLayer(){

        Synapse w1=new SynapseImpl(1.0f);
        Synapse w2=new SynapseImpl(-1.0f);
        Synapse w3=new SynapseImpl(-1.0f);
        Synapse w4=new SynapseImpl(-1.0f);

        Neuron neuron1=new NeuronImpl(w1,w3);
        Neuron neuron2=new NeuronImpl(w2,w4);

        List<Neuron> neurons=new ArrayList<>();
        neurons.add(neuron1);
        neurons.add(neuron2);

        return new LayerImpl(neurons);
    }

    public Layer getSecondLayer(){

        Synapse out1=new SynapseImpl(1.0f);
        Synapse out2=new SynapseImpl(1.0f);

        Neuron neuron3=new NeuronImpl(out1,out2);

        List<Neuron> neurons=new ArrayList<>();
        neurons.add(neuron3);

        return new LayerImpl(neurons);
    }
}
