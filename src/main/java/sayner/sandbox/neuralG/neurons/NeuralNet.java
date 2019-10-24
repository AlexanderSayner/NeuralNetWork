package sayner.sandbox.neuralG.neurons;

public class NeuralNet {

	private int x1;
	private int x2;

	private int first_input_neuron_result;
	private int second_input_neuron_result;

	/**
	 * Входные значения
	 * 
	 * @param x1
	 * @param x2
	 */
	public NeuralNet(int x1, int x2) {
		this.x1 = x1;
		this.x2 = x2;
	}

	public int start() {

		Neuron first_layer_one = new Neuron(1, -1);
		Neuron first_layer_two = new Neuron(1, -1);

		Neuron second_layer_one = new Neuron(1, 1);

		first_layer_one.first_synapse_signal(x1);
		first_layer_one.second_synapse_signal(x2);

		first_layer_two.first_synapse_signal(x2);
		first_layer_two.second_synapse_signal(x1);

		this.first_input_neuron_result = first_layer_one.axon_value();
		this.second_input_neuron_result = first_layer_two.axon_value();

		second_layer_one.first_synapse_signal(this.first_input_neuron_result);
		second_layer_one.second_synapse_signal(this.second_input_neuron_result);

		return second_layer_one.axon_value();
	}

	public int get_one() {
		return this.first_input_neuron_result;
	}

	public int get_two() {
		return this.second_input_neuron_result;
	}
}
