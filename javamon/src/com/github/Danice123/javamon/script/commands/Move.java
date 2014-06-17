package com.github.Danice123.javamon.script.commands;

import java.util.HashMap;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.entity.Entity;
import com.github.Danice123.javamon.script.ScriptException;

public class Move extends Command {

	@Override
	public void execute(Game game, HashMap<String, String> strings, Entity target) throws ScriptException {
		if (args[0].toLowerCase().equals("p")) {
			game.getPlayer().setCoords(Integer.parseInt(args[1]),
					Integer.parseInt(args[2]),
					Integer.parseInt(args[3]));
		} else if (args[0].toLowerCase().equals("t")) {
			target.setCoords(Integer.parseInt(args[1]),
					Integer.parseInt(args[2]),
					Integer.parseInt(args[3]));
		} else {
			Entity e = game.getWorld().getEntity(args[0]);
			if (e != null) {
				e.setCoords(Integer.parseInt(args[1]),
						Integer.parseInt(args[2]),
						Integer.parseInt(args[3]));
			} else {
				throw new ScriptException(ScriptException.BAD_ARGS);
			}
		}
	}

}
