package com.github.Danice123.javamon.screen.menu;

import java.util.Random;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.battlesystem.BattleFunction;
import com.github.Danice123.javamon.battlesystem.Battlesystem;
import com.github.Danice123.javamon.screen.Screen;

public abstract class Battle extends Screen implements BattleFunction {
	
	private Random random;
	protected Battlesystem battle;

	public Battle(Game game, Screen parent, Battlesystem battle) {
		super(game, parent);
		random = new Random();
		this.battle = battle;
	}

	@Override
	public Random getRandom() {
		return random;
	}
}
