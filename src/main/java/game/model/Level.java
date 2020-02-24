package game.model;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import org.joml.Vector2f;

import game.core.Model;
import game.core.Window;
import game.utils.StaticModelManager;

public class Level {
	public boolean gameOver;
	private int score;
	private int index, maxIndex;
	private Pipe next;

	// background
	private LinkedList<Background> bgs = new LinkedList<Background>();

	// pipes
	private LinkedList<Pipe> pipes = new LinkedList<Pipe>();
	private float spaceBetweenPipesX;
	private float spaceBetweenPipesY;
	private float offset;

	// bird
	private Bird bird;

	public Level() {
		gameOver = false;

		// background init
		bgs.add(new Background());

		int bgSize = (int) Math.ceil(Window.getWidth() / bgs.getFirst().texture.getWidth());

		for (int i = 0; i < bgSize; ++i) {
			bgs.add(new Background());
		}
		for (int i = 1; i <= bgSize; ++i) {
			bgs.get(i).transform.position.x = bgs.getFirst().transform.position.x + i * bgs.get(i).texture.getWidth();
		}

		// bird init
		bird = new Bird();

		// pipe init
		float pipesSize = 10;

		// calculation
		spaceBetweenPipesX = Window.getWidth() / pipesSize;
		spaceBetweenPipesY = Window.getHeight() / pipesSize * 2 + Window.getHeight() / 10;
		offset = bird.transform.position.x + Window.getWidth() / 2;

		for (int i = 0; i < pipesSize; ++i) {
			pipes.add(new Pipe());
		}
		pipes.getFirst().transform.position.x += offset;
		for (int i = 0; i < pipesSize - 1; i += 2) {
			float randomY = ThreadLocalRandom.current().nextInt(
					(int) pipes.get(i).texture.getPivot().getLeftTop().y / 2,
					(int) pipes.get(i).texture.getHeight() / 4);

			pipes.get(i).transform.position.x = pipes.getFirst().transform.position.x
					+ i * pipes.get(i).texture.getWidth() / 2 + i * spaceBetweenPipesX;
			pipes.get(i).transform.position.y = randomY;

			pipes.get(i + 1).transform.position.x = pipes.get(i).transform.position.x;
			pipes.get(i + 1).transform.position.y = pipes.get(i).transform.position.y - pipes.get(i).texture.getHeight()
					- spaceBetweenPipesY;
		}
		
		next = pipes.getFirst();
		
		maxIndex = (int)(bird.transform.position.x / (pipes.get(0).texture.getWidth() + spaceBetweenPipesX)) * 2;
	}

	public void update() {
		if (!gameOver) {
			updateBackground();
			updatePipes();
			updateBird();
		} else {
			bird.transform.position.y -= 10.0f;
		}
	}

	public void render() {
		renderBackground();
		renderPipes();
		renderBird();
	}

	private void updateBackground() {
		if (Math.abs(bgs.getFirst().transform.position.x) > bgs.getFirst().texture.getWidth() / 2) {
			Background last = new Background();
			last.transform.position.x = bgs.get(bgs.size() - 1).transform.position.x + last.texture.getWidth();
			bgs.addLast(last);
			bgs.removeFirst();
		}

		for (Background bg : bgs) {
			bg.update();
		}
	}

	private void updatePipes() {
		if (pipes.getFirst().transform.position.x + pipes.getFirst().texture.getWidth() / 2 < 0.0f) {
			Pipe top = new Pipe();
			float randomY = ThreadLocalRandom.current().nextInt((int) top.texture.getPivot().getLeftTop().y / 2,
					(int) top.texture.getHeight() / 4);

			top.transform.position.x = pipes.getLast().transform.position.x + top.texture.getWidth() / 2
					+ spaceBetweenPipesX * 2;
			top.transform.position.y = randomY;

			pipes.removeFirst();
			pipes.addLast(top);

			Pipe bottom = new Pipe();
			bottom.transform.position.x = top.transform.position.x;
			bottom.transform.position.y = top.transform.position.y - top.texture.getHeight() - spaceBetweenPipesY;

			pipes.removeFirst();
			pipes.addLast(bottom);
		}

		for (Pipe pipe : pipes) {
			pipe.update();
		}
	}

	private void updateBird() {
		bird.update();

		for (Pipe pipe : pipes) {
			if (collision(pipe, bird)) {
				 gameOver = true;
			}
		}

		if (bird.transform.position.y + bird.texture.getPivot().getLeftTop().y >= 0.0f) {
			 gameOver = true;
		}
		if (bird.transform.position.y + bird.texture.getPivot().getLeftTop().y <= -Window.getHeight()) {
			 gameOver = true;
		}

		int tempScore = score;
		if (bird.transform.position.x >= next.transform.position.x) {
			if(index <= maxIndex) {
				index++;
				if(index % 2 == 0) {
					next = pipes.get(index);
					score++;
				}
			} else {
				next = pipes.get(index);
				score++;
			}
		}
		if(tempScore != score) {
			System.out.println(score);
		}
	}

	private void renderBackground() {
		StaticModelManager.BACKGROUND.beforeRender();

		for (Background bg : bgs) {
			bg.getModel().getShader().setUniformMat4f("viewmodel_matrix", bg.transform.getMatrix());
			StaticModelManager.BACKGROUND.render();
		}

		StaticModelManager.BACKGROUND.afterRender();
	}

	private void renderPipes() {
		StaticModelManager.PIPE.beforeRender();

		for (int i = 0; i < pipes.size(); ++i) {
			pipes.get(i).getModel().getShader().setUniformMat4f("viewmodel_matrix", pipes.get(i).transform.getMatrix());
			pipes.get(i).getModel().getShader().setUniform1i("top", i % 2 == 0 ? 1 : 0);
			StaticModelManager.PIPE.render();
		}

		StaticModelManager.PIPE.afterRender();
	}

	private void renderBird() {
		StaticModelManager.BIRD.beforeRender();

		bird.getModel().getShader().setUniformMat4f("viewmodel_matrix", bird.transform.getMatrix());
		StaticModelManager.BIRD.render();

		StaticModelManager.BIRD.afterRender();
	}

	private boolean collision(Model a, Model b) {
		Vector2f positionA = a.transform.position;
		Vector2f positionB = b.transform.position;

		Vector2f texturePositionA = a.texture.getPivot().getLeftTop();
		Vector2f texturePositionB = b.texture.getPivot().getLeftTop();

		Vector2f posA = new Vector2f(positionA.x - texturePositionA.x, positionA.y + texturePositionA.y);
		Vector2f posB = new Vector2f(positionB.x - texturePositionB.x, positionB.y + texturePositionB.y);

		float widthA = a.texture.getWidth();
		float widthB = b.texture.getWidth();

		float heightA = a.texture.getHeight();
		float heightB = b.texture.getHeight();

		if (posA.x + widthA >= posB.x) {
			if (posA.x <= posB.x + widthB) {
				if (posA.y + heightA >= posB.y) {
					if (posA.y <= posB.y + heightB) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
