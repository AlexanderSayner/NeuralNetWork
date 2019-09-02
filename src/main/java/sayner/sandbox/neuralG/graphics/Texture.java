package sayner.sandbox.neuralG.graphics;

import sayner.sandbox.neuralG.utils.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.lwjgl.opengl.GL30.*;

/**
 * Проекция текстуры
 */
public class Texture {

    private int width, height;
    private int textureId;

    public Texture(String filePath) {

        this.textureId = load(filePath);
    }

    /**
     * Загружает текстуру в OpenGL
     *
     * @param filePath
     * @return
     */
    private int load(String filePath) {

        int[] pixels = null;

        try {

            BufferedImage image = ImageIO.read(new FileInputStream(filePath));
            this.width = image.getWidth();
            this.height = image.getHeight();
            pixels = new int[height * width];

            image.getRGB(
                    0, 0, width, height, pixels, 0, width
            );

        } catch (FileNotFoundException fnfe) {

            System.err.println(String.format("Файл текстуры под именем %s не обнаружен на базе", filePath));
            fnfe.printStackTrace();

        } catch (IOException ioe) {

            System.err.println(String.format("Ошибка при загрузке текстуры %s", filePath));
            ioe.printStackTrace();

        }

        int[] data = new int[width * height];

        for (int i = 0; i < width * height; i++) {

            int a = (pixels[i] & 0xff000000) >> 24; // ALPHA
            int r = (pixels[i] & 0xff0000) >> 16; // RED
            int g = (pixels[i] & 0xff00) >> 8; // GREEN
            int b = (pixels[i] & 0xff); // BLUE

            data[i] = a << 24 | b << 16 | g << 8 | r;
        }

        // Генерация текстуры
        int result = glGenTextures();
        // Связываем текстуру с контекстом
        glBindTexture(GL_TEXTURE_2D, result); // All upcoming GL_TEXTURE_2D operations now have effect on our texture object

        //tell opengl how to unpack bytes
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        // Set our texture parameters
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);    // Set texture wrapping to GL_REPEAT
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        // Set texture filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createIntegerBuffer(data));
        glGenerateMipmap(GL_TEXTURE_2D);

        glBindTexture(GL_TEXTURE_2D, 0); // Unbind texture when done, so we won't accidentily mess up our texture.

        return result;
    }

    public void bind() {

        glBindTexture(GL_TEXTURE_2D, this.textureId);
    }

    public void unbind() {

        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
