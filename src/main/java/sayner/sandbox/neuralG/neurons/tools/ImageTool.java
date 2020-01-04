package sayner.sandbox.neuralG.neurons.tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Переводит картинку в формат, удобный
 */
public class ImageTool {

    private final File file;
    private final BufferedImage source;

    public ImageTool(String path) throws IOException {
        file = new File(path);
        source = ImageIO.read(file);
    }

    /**
     * Формирует массив чёрно-белых пикселей
     * для входа в нейронную сеть
     *
     * @return массив в готовом формате
     */
    public List<Float> createInputArray() {

        List<Float> result = new ArrayList<>();

        for (int x = 0; x < source.getWidth(); x++) {
            for (int y = 0; y < source.getHeight(); y++) {

                // Получаем цвет текущего пикселя
                Color color = new Color(source.getRGB(x, y));

                // Получаем каналы этого цвета
                int blue = color.getBlue();
                int red = color.getRed();
                int green = color.getGreen();

                // Применяем стандартный алгоритм для получения черно-белого изображения
                float grey = (float) (red * 0.299 + green * 0.587 + blue * 0.114);

                result.add(grey);
            }
        }

        return result;
    }
}
