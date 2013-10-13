package com.github.Danice123.javamon.control;

import com.github.Danice123.javamon.screen.Screen;

public interface MenuControl {
	
	public Screen getScreen();

	public void up();
	
	public void down();
	
	public void right();
	
	public void left();
	
	public void accept();
	
	public void deny();
}
