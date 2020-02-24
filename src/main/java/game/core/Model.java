package game.core;

import game.components.Texture;
import game.components.Transform;

public class Model {
	private StaticModel model;
	public Transform transform;
	public Texture texture;

	public Model(StaticModel model, float positionZ) {
		this.model = model;
		transform = new Transform(positionZ);
		this.texture = new Texture(model.getTexture(), transform);
	}

	public StaticModel getModel() {
		return model;
	}
}
