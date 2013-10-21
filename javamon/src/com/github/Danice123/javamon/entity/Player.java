package com.github.Danice123.javamon.entity;

import java.util.HashMap;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.control.MenuControl;
import com.github.Danice123.javamon.control.PlayerControl;
import com.github.Danice123.javamon.entity.sprite.Spriteset;
import com.github.Danice123.javamon.screen.StartMenu;

public class Player extends Walkable {
	
	public Game game;
	
	private HashMap<String, Boolean> flag;
	public PlayerControl control;
	private MenuControl menu;
	
	public boolean menuOpen;

	public Player(Game game, Spriteset sprites) {
		super("Player", sprites, null);
		this.game = game;
		this.menu = game.getWorld();
		setTextureRegion(sprites.getSprite(getFacing()));
		control = new PlayerControl();
		
		flag = new HashMap<String, Boolean>();
	}
	
	public boolean getFlag(String s) {
		try {
			boolean b = flag.get(s);
			return b;
		} catch (NullPointerException e) {
			return false;
		}
		
	}
	
	public void setFlag(String s, boolean state) {
		flag.put(s, state);
	}
	
	@Override
	protected void threaded() {
		if (menuOpen) {
			if (control.A) {
				menu.accept();
				control.A = false;
			} else if (control.B) {
				menu.deny();
				control.B = false;
			} else if (control.getControl() != null){
				switch (control.getControl()) {
				case East:
					menu.right();
					break;
				case North:
					menu.up();
					break;
				case South:
					menu.down();
					break;
				case West:
					menu.left();
					break;
				default:
					break;
				}
			}
			return;
		} else {
			if (control.A) {
				playerActivate();
				control.A = false;
			}
			if (control.start) {
				new StartMenu(game, game.getWorld());
				menuOpen = true;
			}
			Dir d = control.getControl();
			if (d != null)
				walk(d);
		}
	}
	
	private void playerActivate() {
		switch(getFacing()) {
		case North:
			if (game.getWorld().getEntity(trueX(), trueY() + 1, getLayer()) != null) {
				game.getWorld().getEntity(trueX(), trueY() + 1, getLayer()).activate(this);
			}
			break;
		case South:
			if (game.getWorld().getEntity(trueX(), trueY() - 1, getLayer()) != null) {
				game.getWorld().getEntity(trueX(), trueY() - 1, getLayer()).activate(this);
			}
			break;
		case East:
			if (game.getWorld().getEntity(trueX() + 1, trueY(), getLayer()) != null) {
				game.getWorld().getEntity(trueX() + 1, trueY(), getLayer()).activate(this);
			}
			break;
		case West:
			if (game.getWorld().getEntity(trueX() - 1, trueY(), getLayer()) != null) {
				game.getWorld().getEntity(trueX() - 1, trueY(), getLayer()).activate(this);
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected int threadDelay() {
		if (menuOpen)
			return 5;
		return 1;
	}
}
