package game.core;

import game.Main;

public class StaticModel {
	private VertexArray vao;
	private ShaderProgram shader;
	private StaticTexture texture;

	public StaticModel(String vertexFile, String fragmentFile, String textureFile) {
		this.shader = new ShaderProgram(vertexFile, fragmentFile);
		this.texture = new StaticTexture(textureFile);
		this.vao = new VertexArray(vertices(texture), indices(), textureCoords());

		shader.setUniformMat4f("projection_matrix", Main.orto);
		shader.disable();
	}

	public void destroy() {
		texture.destroy();
		vao.destroy();
		shader.destroy();
	}

	public void beforeRender() {
		shader.enable();
		texture.bind();
		vao.bind();
	}
	
	public void render() {
		vao.render();
	}

	public void afterRender() {
		vao.unbind();
		texture.unbind();
		shader.disable();
	}

	private static float[] vertices(StaticTexture texture) {
		float x = texture.getWidth() / 2;
		float y = texture.getHeight() / 2;
		float z = 0.0f;
		return new float[] { -x, -y, z, -x, y, z, x, y, z, x, -y, z };
	}

	private static byte[] indices() {
		return new byte[] { 0, 1, 2, 2, 3, 0 };
	}

	private static float[] textureCoords() {
		return new float[] { 0, 1, 0, 0, 1, 0, 1, 1 };
	}

	public StaticTexture getTexture() {
		return texture;
	}

	public ShaderProgram getShader() {
		return shader;
	}

}
