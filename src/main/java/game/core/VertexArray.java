package game.core;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import game.utils.Utils;

public class VertexArray {
	private int vao, vertexVbo, indicesVbo, textureCoordVbo;
	private int vertexCount;
	
	public VertexArray(float[] vertices, byte[] indices, float[] textureCoords) {
		vertexCount = indices.length;
		
		// VAO
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		// Vertex
		vertexVbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexVbo);
		glBufferData(GL_ARRAY_BUFFER, Utils.createFloatBuffer(vertices), GL_STATIC_DRAW);
		glVertexAttribPointer(ShaderProgram.VERTEX_ATTRIB_POS, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(ShaderProgram.VERTEX_ATTRIB_POS);
		
		// Indices
		indicesVbo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesVbo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utils.createByteBuffer(indices), GL_STATIC_DRAW);
		
		// Texture coordinates
		textureCoordVbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, textureCoordVbo);
		glBufferData(GL_ARRAY_BUFFER, Utils.createFloatBuffer(textureCoords), GL_STATIC_DRAW);
		glVertexAttribPointer(ShaderProgram.TEXTURECOORDS_ATTRIB_POS, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(ShaderProgram.TEXTURECOORDS_ATTRIB_POS);
		
		// Unbind all
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	public void destroy() {
		unbind();
		glDeleteBuffers(vertexVbo);
		glDeleteBuffers(indicesVbo);
		glDeleteBuffers(textureCoordVbo);
		glDeleteVertexArrays(vao);
	}
	
	public void bind() {
		glBindVertexArray(vao);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesVbo);
	}
	
	public void unbind() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	public void render() {
		glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_BYTE, 0);
	}
}
