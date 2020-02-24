package game.model;

import game.core.Model;
import game.core.Window;
import game.utils.StaticModelManager;

public class Pipe extends Model {
	private float speed = 10.0f;
	public int index = 0;

	public Pipe() {
		super(StaticModelManager.PIPE, 0.1f);

		transform.scale.x = Window.getWidth() * 0.001f;
		transform.scale.y = Window.getHeight() * 0.002f;

		transform.position = texture.getPivot().getLeftTop();
	}

	public void update() {
		transform.position.x -= speed;
	}
}
