package com.github.Danice123.javamon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.github.Danice123.javamon.screen.menu.Arrow;
import com.github.Danice123.javamon.screen.menu.BorderBox;

public class Display extends com.badlogic.gdx.Game {
	
	public Game game;
	
	public static int WIDTH;
	public static int HEIGHT;
	public static int scale;
	
	public static BitmapFont font;
	public static BorderBox border;
	public static Arrow arrow;
	
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
		border = new BorderBox(new Texture("res/gui/border.png"));
		arrow = new Arrow(new Texture("res/gui/arrow.png"));
		game = new Game(this);
		new Thread(game).start();
	}
}
