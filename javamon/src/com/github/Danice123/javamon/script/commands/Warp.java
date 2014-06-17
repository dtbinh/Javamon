package com.github.Danice123.javamon.script.commands;

import java.util.HashMap;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.entity.Entity;
import com.github.Danice123.javamon.script.ScriptException;

public class Warp extends Command {

	@Override
	public void execute(Game game, HashMap<String, String> strings, Entity target) throws ScriptException {
	try {
		game.getWorld().loadMapSynch(parseString(args[0], strings));
		game.getPlayer().setCoords(Integer.parseInt(parseString(args[1], strings)),
				Integer.parseInt(parseString(args[2], strings)),
				Integer.parseInt(parseString(args[3], strings)));
	} catch (NullPointerException e) {
		throw new ScriptException(ScriptException.BAD_STRING);
	}
	}

}
