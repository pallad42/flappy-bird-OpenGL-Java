package game.core;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import game.utils.Utils;

public class ShaderProgram {
	public static final String SHADERS = "src/main/resources/shaders/";

	public static int VERTEX_ATTRIB_POS = 0;
	public static int TEXTURECOORDS_ATTRIB_POS = 1;

	private int programID;
	private String vertexFile;

	private boolean enabled = false;

	public ShaderProgram(String vertexFile, String fragmentFile) {
		int vertexID = loadShader(vertexFile, GL_VERTEX_SHADER);
		int fragmentID = loadShader(fragmentFile, GL_FRAGMENT_SHADER);
		this.programID = glCreateProgram();

		glAttachShader(programID, vertexID);
		glAttachShader(programID, fragmentID);

		glLinkProgram(programID);
		glValidateProgram(programID);

		glDetachShader(programID, vertexID);
		glDetachShader(programID, fragmentID);

		glDeleteShader(vertexID);
		glDeleteShader(fragmentID);

		this.vertexFile = vertexFile;
	}

	public void enable() {
		glUseProgram(programID);
		enabled = true;
	}

	public void disable() {
		glUseProgram(0);
		enabled = false;
	}

	public void destroy() {
		disable();
		glDeleteProgram(programID);
	}

	public int getUniform(String name) {
		int result = glGetUniformLocation(programID, name);
		if (result == -1) {
			throw new RuntimeException("Could not find uniform variable: '" + name + "' in + " + vertexFile + "");
		}
		return result;
	}

	public void setUniform1i(String name, int value) {
		if (!enabled)
			enable();
		glUniform1i(getUniform(name), value);
	}

	public void setUniform1f(String name, float value) {
		if (!enabled)
			enable();
		glUniform1f(getUniform(name), value);
	}

	public void setUniform2f(String name, float x, float y) {
		if (!enabled)
			enable();
		glUniform2f(getUniform(name), x, y);
	}

	public void setUniform3f(String name, float x, float y, float z) {
		if (!enabled)
			enable();
		glUniform3f(getUniform(name), x, y, z);
	}

	public void setUniformMat4f(String name, Matrix4f matrix) {
		if (!enabled)
			enable();
		glUniformMatrix4fv(getUniform(name), false, Utils.createFloatBufferFromMatrix(matrix));
	}

	private int loadShader(String shaderFile, int shaderType) {
		String path = SHADERS + shaderFile;
		StringBuilder source = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = reader.readLine()) != null) {
				source.append(line).append("\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int shaderID = glCreateShader(shaderType);

		glShaderSource(shaderID, source);
		glCompileShader(shaderID);

		if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println("ERROR: Could not compile shader: '" + path + "'");
			throw new RuntimeException(glGetShaderInfoLog(shaderID, 1024));
		}

		return shaderID;
	}
}
