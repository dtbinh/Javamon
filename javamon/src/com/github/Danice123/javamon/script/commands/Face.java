package com.github.Danice123.javamon.script.commands;

import java.util.HashMap;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.entity.Entity;
import com.github.Danice123.javamon.entity.Walkable;
import com.github.Danice123.javamon.script.ScriptException;

public class Face extends Command {

	@Override
	public void execute(Game game, HashMap<String, String> strings, Entity target) throws ScriptException {
		if (args[0].toLowerCase().equals("p")) {
			game.getPlayer().face(getDir(game, args[1]));
		} else if (args[0].toLowerCase().equals("t")) {
			try {
				((Walkable) target).face(getDir(game, args[1]));
			} catch (ClassCastException e) {
				throw new ScriptException(ScriptException.BAD_TARGET);
			}
		} else {
			Entity e = game.getWorld().getEntity(args[0]);
			if (e != null) {
				try {
					((Walkable) e).face(getDir(game, args[1]));
				} catch (ClassCastException error) {
					throw new ScriptException(ScriptException.BAD_TARGET);
				}
			} else {
				throw new ScriptException(ScriptException.BAD_ARGS);
			}
		}
	}

}
