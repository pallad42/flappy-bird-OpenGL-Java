package game.components;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Transform {
	public Vector2f position = new Vector2f();
	private float positionZ;
	public Vector2f scale = new Vector2f(1.0f, 1.0f);
	public Vector3f rotation = new Vector3f();
	
	public Transform(float positionZ) {
		this.positionZ = positionZ;
	}

	public Matrix4f getMatrix() {
		Matrix4f posMat = new Matrix4f().translate(position.x, position.y, positionZ);
		Matrix4f scaleMat = new Matrix4f().scale(scale.x, scale.y, 1.0f);
		
		Matrix4f rotMatX = new Matrix4f().rotation((float)Math.toRadians(rotation.x), new Vector3f(1.0f, 0.0f, 0.0f));
		Matrix4f rotMatY = new Matrix4f().rotation((float)Math.toRadians(rotation.y), new Vector3f(0.0f, 1.0f, 0.0f));
		Matrix4f rotMatZ = new Matrix4f().rotation((float)Math.toRadians(rotation.z), new Vector3f(0.0f, 0.0f, 1.0f));
		
		return posMat.mul(scaleMat).mul(rotMatX).mul(rotMatY).mul(rotMatZ);
	}
}
