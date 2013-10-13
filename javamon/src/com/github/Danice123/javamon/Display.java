package com.github.Danice123.javamon;

public class Display extends com.badlogic.gdx.Game {
	
	public Game game;
	
	public static int WIDTH;
	public static int HEIGHT;
	
	public Display(int w, int h) {
		super();
		WIDTH = w;
		HEIGHT = h;
	}
	
	@Override
	public void create() {
		game = new Game(this);
		new Thread(game).start();
	}
}
