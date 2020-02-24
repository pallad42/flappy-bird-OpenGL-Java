package game.components;

import game.core.StaticTexture;

public class Texture {
	private int width, height;
	private Transform transform;
	private Pivot pivot;

	public Texture(StaticTexture texture, Transform transform) {
		this.transform = transform;
		this.width = texture.getWidth();
		this.height = texture.getHeight();
		pivot = new Pivot(transform, width, height);
	}

	public float getWidth() {
		return width * transform.scale.x;
	}

	public float getHeight() {
		return height * transform.scale.y;
	}

	public Pivot getPivot() {
		return pivot;
	}

}
