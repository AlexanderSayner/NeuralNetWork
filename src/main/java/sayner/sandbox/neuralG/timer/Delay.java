package sayner.sandbox.neuralG.timer;

/**
 * Регулирует частоту перемещения игровых объектов
 */
public class Delay {

    long lastTime = System.nanoTime();
    double delta = 0.0;
    double ns = 1000000000.0 / 60.0;
    long timer = System.currentTimeMillis();
    int updates = 0;
    int frames = 0;

    public void gap(Update update){

        long now = System.nanoTime();
        delta += (now - lastTime) / ns;
        lastTime = now;
        if (delta >= 1.0) {

            update.rate();
            updates++;
            delta--;
        }

        frames++;

        if (System.currentTimeMillis() - timer > 1000) {
            timer += 1000;
            System.out.println(updates + " ups, " + frames + " fps");
            updates = 0;
            frames = 0;
        }
    }
}
