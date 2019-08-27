package sayner.sandbox.neuralG.neurons;

/**
 * Персептрон
 * 
 * @author Dark Archon
 *
 */
public class Neuron {

	/**
	 * Первый синапс
	 */
	// Вход синапса нейрона
	private float first_synapse_input = 0;
	// Вес синапса
	private int first_synapse_weight = 0;

	/**
	 * Второй синапс
	 */
	// Вход синапса нейрона
	private float second_synapse_input = 0;
	// Вес синапса
	private int second_synapse_weight = 0;

	/**
	 * Сущестрование NULL весов недопускается
	 */
	protected Neuron() {
	}

	/**
	 * Рождение нейрона
	 */
	public Neuron(int first_synapse_weight, int second_synapse_weight) {
		this.first_synapse_weight = first_synapse_weight;
		this.second_synapse_weight = second_synapse_weight;
	}

	/**
	 * Передача сигнала синапсам
	 * 
	 * @param value
	 */
	public void first_synapse_signal(int value) {
		this.first_synapse_input = value;
	}

	public void second_synapse_signal(int value) {
		this.second_synapse_input = value;
	}

	/**
	 * Активационная функция нейрона
	 * 
	 * @return
	 */
	public int axon_value() {

		float sum1 = this.first_synapse_weight * this.first_synapse_input;
		float sum2 = this.second_synapse_weight * this.second_synapse_input;

		float sum = sum1 + sum2;

		if (sum >= 0.5f) {
			return 1;
		}
		return 0;
	}
}
