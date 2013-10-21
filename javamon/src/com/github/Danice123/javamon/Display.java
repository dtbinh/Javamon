package com.github.Danice123.javamon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Display extends com.badlogic.gdx.Game {
	
	public Game game;
	
	public static int WIDTH;
	public static int HEIGHT;
	public static int scale;
	
	public static BitmapFont font;
	
	public Display(int w, int h) {
		super();
		WIDTH = w;
		HEIGHT = h;
		scale = WIDTH / 240;
	}
	
	@Override
	public void create() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("res/gui/font.ttf"));
		font = generator.generateFont(8 * scale);
		generator.dispose();
		game = new Game(this);
		new Thread(game).start();
	}
}
