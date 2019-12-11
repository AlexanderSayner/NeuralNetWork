package sayner.sandbox.neuralG.neurons.observers;

import java.util.ArrayList;
import java.util.List;

// Observable
public class AEventSource {

    private List<ObserverA> listeners = new ArrayList<ObserverA>();

    public void add(ObserverA listener) {
        listeners.add(listener);
    }

    private void aChanged() {
        for (ObserverA listener : listeners) {
            listener.aChanged();
        }
    }

    private void bChanged() {
        for (ObserverA listener : listeners) {
            listener.bChanged();
        }
    }

    //notifyObservers
    public void f() {

        aChanged();
        bChanged();

    }
}
