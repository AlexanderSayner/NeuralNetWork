package sayner.sandbox.neuralG.level;

import java.util.ArrayList;
import java.util.List;

public class Divisions {

    private final List<Division> divisions;

    public Divisions(float interval) {

        // Вряд ли потребуется вставка в середину внезапно, пожтому массив
        List<Division> divisions = new ArrayList<>();

        for (float i = interval; i < 1.0; i += interval) {

            divisions.add(new Division(i, 0.0f, 0.0f, 0.015f));
        }

        for (float i = -interval; i > -1.0; i -= interval) {

            divisions.add(new Division(i, 0.0f, 0.0f, 0.015f));
        }

        for (float i = -interval; i > -1.0; i -= interval) {

            divisions.add(new Division(0.0f, 0.015f, i, 0.0f));
        }

        for (float i = interval; i < 1.0; i += interval) {

            divisions.add(new Division(0.0f, 0.015f, i, 0.0f));
        }

        this.divisions = divisions;
    }

    /**
     * Рисует всех и каждого в порядке живой очереди
     */
    public void render() {

        this.divisions.forEach(
                division ->
                        division.render()
        );
    }
}
