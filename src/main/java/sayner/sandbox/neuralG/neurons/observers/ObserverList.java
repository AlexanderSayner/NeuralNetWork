package sayner.sandbox.neuralG.neurons.observers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Реализует управление множеством слушателей
 *
 * @param <T> любой тип наблюдателей (синапсов, нейронов)
 */
public class ObserverList<T> implements Iterable<T> {

    /**
     * Обязательно должен быть инитиализирован,
     * чтобы наследники данного класса могли закинуть его в *EventTranslator
     */
    protected List<T> observers = new ArrayList<>();

    public boolean add(T ... listeners){
        return observers.addAll(Arrays.asList(listeners));
    }

    public boolean remove(T listener) {
        return this.observers.remove(listener);
    }

    @Override
    public Iterator<T> iterator() {
        return this.observers.iterator();
    }
}
