package com.github.Danice123.javamon.screen.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.screen.Screen;

public abstract class StartMenu extends Screen {
	
	@SuppressWarnings("rawtypes")
	public static Class startMenuClass = com.github.Danice123.javamon.screen.menu.gen1.StartMenu.class;

	public StartMenu(Game game, Screen parent) {
		super(game, parent);
	}
	
	protected void openPokedex() {
		Pokedex.newStartMenu(getGame(), this);
	}
	
	protected void openPokemon() {
		PokemonMenu.newStartMenu(getGame(), this);
	}
	
	protected void openBag() {
		
	}
	
	protected void openTrainer() {
		
	}
	
	protected void openSave() {
		
	}
	
	protected void openOptions() {
		
	}
	
	public static void newStartMenu(Game game, Screen parent) {
		try {
			startMenuClass.getConstructors()[0].newInstance(game, parent);
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
