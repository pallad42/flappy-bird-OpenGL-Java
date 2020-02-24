package game.model;

import game.core.Model;
import game.core.Window;
import game.utils.StaticModelManager;

public class Background extends Model {
	private float speed = 5.0f;
	
	public Background() {
		super(StaticModelManager.BACKGROUND, 0.0f);
		
		transform.scale.y = Window.getHeight() / texture.getHeight();
		transform.scale.x = transform.scale.y;
		
		transform.position = texture.getPivot().getLeftTop();
	}
	
	public void update() {
		transform.position.x -= speed;
	}
}
