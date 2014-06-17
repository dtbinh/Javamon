package com.github.Danice123.javamon.script.commands;

import java.util.HashMap;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.entity.Entity;
import com.github.Danice123.javamon.script.ScriptException;

public class Unhide extends Command {

	@Override
	public void execute(Game game, HashMap<String, String> strings, Entity target) throws ScriptException {
		if (args[0].toLowerCase().equals("p")) {
			game.getPlayer().hidden = false;
		} else if (args[0].toLowerCase().equals("t")) {
			target.hidden = false;
		} else {
			Entity e = game.getWorld().getEntity(args[0]);
			if (e != null) {
				e.hidden = false;
			} else {
				throw new ScriptException(ScriptException.BAD_ARGS);
			}
		}
	}

}
