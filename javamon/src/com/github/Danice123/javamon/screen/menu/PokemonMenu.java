package com.github.Danice123.javamon.screen.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.battlesystem.Party;
import com.github.Danice123.javamon.screen.Screen;

public abstract class PokemonMenu extends Screen {
	
	@SuppressWarnings("rawtypes")
	public static Class pokemonMenuClass = com.github.Danice123.javamon.screen.menu.gen1.PokemonMenu.class;

	public PokemonMenu(Game game, Screen parent) {
		super(game, parent);
	}
	
	protected Party getParty() {
		return getGame().getPlayer().getParty();
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
