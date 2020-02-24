package game;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import org.joml.Matrix4f;

import game.core.Window;
import game.model.Level;
import game.utils.StaticModelManager;

public class Main {
	public static Matrix4f orto;
	private Level level;
	
	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		Window.init(400 * 4, 300 * 3, "flappy bird");
		//Window.init(400 * 2, 300 * 2, "flappy bird");
		glActiveTexture(GL_TEXTURE0);
		orto = new Matrix4f().setOrtho(0.0f, Window.getWidth(), -Window.getHeight(), 0.0f, -1.0f, 1.0f);
		
		StaticModelManager.init();	

		level = new Level();
		
		loop();

		StaticModelManager.destroy();
		Window.destroy();
	}

	private void update() {
		glfwPollEvents();
		level.update();
		
		int state = glfwGetKey(Window.getWindow(), GLFW_KEY_ENTER);
		if (level.gameOver && state == GLFW_PRESS) {
			level = new Level();
		}
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// render
		level.render();

		// handle GL error
		int error = glGetError();
		if (error != GL_NO_ERROR) {
			System.err.println("GL Error: " + error);
		}

		// render

		glfwSwapBuffers(Window.getWindow());
	}

	private void loop() {
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (!glfwWindowShouldClose(Window.getWindow())) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1.0) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
//				System.out.println(updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
	}
}
