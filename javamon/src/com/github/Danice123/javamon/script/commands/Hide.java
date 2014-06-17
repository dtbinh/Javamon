package com.github.Danice123.javamon.script.commands;

import java.util.HashMap;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.entity.Entity;
import com.github.Danice123.javamon.script.ScriptException;

public class Hide extends Command {

	@Override
	public void execute(Game game, HashMap<String, String> strings, Entity target) throws ScriptException {
		if (args[0].toLowerCase().equals("p")) {
			game.getPlayer().hidden = true;
		} else if (args[0].toLowerCase().equals("t")) {
			target.hidden = true;
		} else {
			Entity e = game.getWorld().getEntity(args[0]);
			if (e != null) {
				e.hidden = true;
			} else {
				throw new ScriptException(ScriptException.BAD_ARGS);
			}
		}
	}

}
