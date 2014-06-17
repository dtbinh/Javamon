package com.github.Danice123.javamon.entity;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.battlesystem.Battlesystem;
import com.github.Danice123.javamon.battlesystem.Party;
import com.github.Danice123.javamon.battlesystem.Trainer;
import com.github.Danice123.javamon.control.MenuControl;
import com.github.Danice123.javamon.control.PlayerControl;
import com.github.Danice123.javamon.entity.sprite.Spriteset;
import com.github.Danice123.javamon.pokemon.Status;
import com.github.Danice123.javamon.screen.menu.gen1.Battle;
import com.github.Danice123.javamon.screen.menu.gen1.StartMenu;
import com.github.Danice123.javamon.script.Script;
import com.github.Danice123.javamon.script.ScriptHandler;
import com.github.Danice123.javamon_bs.PokeData;

public class Player extends Walkable implements Trainer {
	
	public Game game;
	
	private HashMap<String, Boolean> flag;
	public PlayerControl control;
	private MenuControl menu;
	
	public boolean menuOpen;
	public boolean controlLock = false;
	
	public PokeData pokeData;
	public String name = "Red";
	private Party party;

	public Player(Game game, Spriteset sprites) {
		super("Player", sprites, null);
		this.game = game;
		this.menu = game.getWorld();
		setTextureRegion(sprites.getSprite(getFacing()));
		control = new PlayerControl();
		
		flag = new HashMap<String, Boolean>();
		
		pokeData = new PokeData();
		party = new Party();
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
		} else {
			if (controlLock)
				return;
			if (control.A) {
				playerActivate();
				control.A = false;
			}
			if (control.start) {
				StartMenu.newStartMenu(game, game.getWorld());
				menuOpen = true;
			}
			if (control.select) {
				if (!game.getWorld().hasChild()) {
					Battlesystem bs = new Battlesystem(this, new com.github.Danice123.javamon.entity.Trainer("Test", null, null));
					Battle b = new Battle(game, game.getWorld(), bs);
					menuOpen = true;
					bs.init(b);
					new Thread(bs).start();
				}
			}
			Dir d = control.getControl();
			if (d != null) {
				if (d == Dir.North && trueY() == map.getY() - 1) {
					game.getWorld().loadMapSynch(map.getMapBorder(d));
					this.setCoords(trueX() - map.getMapBorderTweak(d), -1, getLayer());
				}
				if (d == Dir.South && trueY() == 0) {
					game.getWorld().loadMapSynch(map.getMapBorder(d));
					this.setCoords(trueX() - map.getMapBorderTweak(d), map.getMapBorderBottomHeight(), getLayer());
				}
				walk(d);
				Script trigger = map.getTrigger(trueX(), trueY(), getLayer());
				if (trigger != null)
					new Thread(new ScriptHandler(game, trigger, null)).start();
			}
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

	@Override
	public Party getParty() {
		return party;
	}

	@Override
	public int firstPokemon() {
		return 0;
	}
	
	@Override
	public boolean hasPokemonLeft() {
		for (int i = 0; i < party.getSize(); i++)
			if (party.getPokemon(i).status != Status.Fainted) return true;
		return false;
	}

	@Override
	public Texture getImage() {
		return new Texture("res/trainer/player.png");
	}

	@Override
	public Texture getBackImage() {
		return new Texture("res/playerBack.png");
	}
}
