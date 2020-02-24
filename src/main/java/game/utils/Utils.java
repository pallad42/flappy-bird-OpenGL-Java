package game.utils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;

public class Utils {
	public static IntBuffer createIntBuffer(int[] data) {
		IntBuffer buffer = org.lwjgl.BufferUtils.createIntBuffer(data.length);
		buffer.put(data).flip();
		return buffer;
	}
	
	public static FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buffer = org.lwjgl.BufferUtils.createFloatBuffer(data.length);
		buffer.put(data).flip();
		return buffer;
	}
	
	public static ByteBuffer createByteBuffer(byte[] data) {
		ByteBuffer buffer = org.lwjgl.BufferUtils.createByteBuffer(data.length);
		buffer.put(data).flip();
		return buffer;
	}
	
	public static FloatBuffer createFloatBufferFromMatrix(Matrix4f matrix) {
		FloatBuffer buffer = org.lwjgl.BufferUtils.createFloatBuffer(16);
		matrix.get(buffer);
		return buffer;
	}
	
	// (900 + 900-512) / 512 = 900 + 338 / 512 = 2.51...
	public static float calculateScale(float bigger, float smaller) {
		return (bigger + bigger - smaller) / smaller;
	}
}
