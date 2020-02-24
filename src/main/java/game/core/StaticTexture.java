package game.core;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import static org.lwjgl.opengl.GL11.*;

public class StaticTexture {
	public static final String TEXTURES = "src/main/resources/textures/";
	private int id;
	private int width, height;

	public StaticTexture(String file) {
		String path = TEXTURES + file;
		
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer compBuffer = BufferUtils.createIntBuffer(1);

		ByteBuffer pixels = STBImage.stbi_load(path, widthBuffer, heightBuffer, compBuffer, 4);

		if (pixels == null) {
			throw new RuntimeException("Failed to load texture '" + path + "': " + STBImage.stbi_failure_reason());
		}

		width = widthBuffer.get(0);
		height = heightBuffer.get(0);

		id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);

		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public void destroy() {
		unbind();
		glDeleteTextures(id);
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
