package com.github.Danice123.javamon.screen.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.battlesystem.Party;
import com.github.Danice123.javamon.pokemon.PokeInstance;
import com.github.Danice123.javamon.screen.Screen;

public abstract class PokemonMenu extends Screen {
	
	protected int[] health;
	
	@SuppressWarnings("rawtypes")
	public static Class pokemonMenuClass = com.github.Danice123.javamon.screen.menu.gen1.PokemonMenu.class;

	public PokemonMenu(Game game, Screen parent) {
		super(game, parent);
	}
	
	@Override
	protected void init() {
		health = new int[getParty().getSize()];
		for (int i = 0; i < getParty().getSize(); i++)
			health[i] = getParty().getPokemon(i).getHealth();
	}
	
	protected Party getParty() {
		return getGame().getPlayer().getParty();
	}
	
	protected void openPokemonStatusMenu(PokeInstance pokemon) {
		PokemonStatusMenu.newStartMenu(getGame(), getScreen(), pokemon);
	}
	
	public static void newStartMenu(Game game, Screen parent) {
		try {
			pokemonMenuClass.getConstructors()[0].newInstance(game, parent);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
}
