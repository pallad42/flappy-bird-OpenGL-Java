package game.model;

import org.joml.Vector2f;

import game.core.Model;
import game.core.Window;
import game.utils.StaticModelManager;

import static org.lwjgl.glfw.GLFW.*;

public class Bird extends Model {
	private float delta = 0.0f;

	public Bird() {
		super(StaticModelManager.BIRD, 0.2f);

		transform.scale.y = 0.3f * Window.getWidth() / Window.getHeight();
		transform.scale.x = transform.scale.y;

		transform.position = new Vector2f(Window.getWidth() / 2, -Window.getHeight() / 2);
	}

	public void update() {
		int state = glfwGetKey(Window.getWindow(), GLFW_KEY_SPACE);
		if (state == GLFW_PRESS) {
			delta = -10f;
		} else {
			delta += 0.5f;
			delta = Math.min(delta, 10.0f);
		}

		transform.position.y -= delta;
		transform.rotation.z = -delta * 3.0f;
	}
}
