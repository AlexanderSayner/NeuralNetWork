package sayner.sandbox.neuralG.textures;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture {

	private int id;
	private int width;
	private int height;

	public Texture(String filename) {

		BufferedImage bi;

		try {

			bi = ImageIO.read(new File(filename));
			this.width = bi.getWidth();
			this.height = bi.getHeight();

			// ON this.width * this.height 4d color
			int[] pixels_raw = new int[this.width * this.height * 4];
			pixels_raw = bi.getRGB(0, 0, this.width, this.height, null, 0, this.width);

			ByteBuffer pixels = BufferUtils.createByteBuffer(this.width * this.height * 4);

			for (int i = 0; i < this.width; i++) {
				for (int j = 0; j < this.height; j++) {

					int pixel = pixels_raw[i * this.width + j];
					pixels.put((byte) ((pixel >> 16) & 0xFF)); // RED
					pixels.put((byte) ((pixel >> 8) & 0xFF)); // GREEN
					pixels.put((byte) (pixel & 0xFF)); // BLUE
					pixels.put((byte) ((pixel >> 24) & 0xFF)); // ALPHA
				}

			}

			// Just OpenGL want it
			pixels.flip();

			this.id = glGenTextures();

			glBindTexture(GL_TEXTURE_2D, id);

			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);

		} catch (IOException ex) {

			String text = "IO ex with the texture image on the way " + filename;
			System.out.println(text);
			ex.printStackTrace();
		}
	}

	/**
	 * Связать текстуру
	 */
	public void bind() {

		glBindTexture(GL_TEXTURE_2D, id);
	}
}
