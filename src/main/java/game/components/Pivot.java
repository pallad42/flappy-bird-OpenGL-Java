package game.components;

import org.joml.Vector2f;

public class Pivot {
	private Transform transform;

	private Vector2f leftTop;
	private Vector2f rightTop;
	private Vector2f leftBottom;
	private Vector2f rightBottom;

	public Pivot(Transform transform, int width, int height) {
		this.transform = transform;

		leftTop = new Vector2f(width / 2, -height / 2);
		rightTop = new Vector2f(-width / 2, -height / 2);
		leftBottom = new Vector2f(width / 2, height / 2);
		rightBottom = new Vector2f(-width / 2, height / 2);
	}

	private Vector2f createVector(Vector2f vector) {
		Vector2f vec = new Vector2f();
		vec.x = vector.x * transform.scale.x;
		vec.y = vector.y * transform.scale.y;
		return vec;
	}

	public Vector2f getLeftTop() {
		return createVector(leftTop);
	}

	public Vector2f getRightTop() {
		return createVector(rightTop);
	}

	public Vector2f getLeftBottom() {
		return createVector(leftBottom);
	}

	public Vector2f getRightBottom() {
		return createVector(rightBottom);
	}

}
