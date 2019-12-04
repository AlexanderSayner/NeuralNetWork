package sayner.sandbox.neuralG.neurons;

import sayner.sandbox.neuralG.neurons.impl.SynapseImpl;

import java.util.ArrayList;
import java.util.List;

public class BuildNeuralNet {

    public List<Neuron> buildInputLayer(Neuron ... neurons){

        List<Neuron> neurons1=new ArrayList<>();

        for (Neuron neuron:neurons){
            neurons1.add(neuron);
        }

        return neurons1;
    }

    /**
     * Устанавливаю НОВЫЕ связи новых нейронов со старым слоем
     */
    public List<Neuron> buildNextClassicalLayer(List<? extends Neuron> layer, Neuron ... neurons){

        List<Neuron> newLayer=new ArrayList<>();


        for (Neuron neuron:neurons){
            layer.forEach(oldNeuron -> {
                oldNeuron.addNewSynapse(new SynapseImpl(neuron));
            });
        }

        return newLayer;
    }

}
