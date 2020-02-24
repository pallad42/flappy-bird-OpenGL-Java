package game.utils;

import game.core.StaticModel;

public class StaticModelManager {
	public static StaticModel BACKGROUND, BIRD, PIPE;
	
	public static void init() {
		BACKGROUND = new StaticModel("bg.vert", "bg.frag", "bg.jpeg");
		BIRD = new StaticModel("bird.vert", "bird.frag", "bird.png");
		PIPE = new StaticModel("pipe.vert", "pipe.frag", "pipe.png");
	}
	
	public static void destroy() {
		BACKGROUND.destroy();
		BIRD.destroy();
		PIPE.destroy();
	}
}
