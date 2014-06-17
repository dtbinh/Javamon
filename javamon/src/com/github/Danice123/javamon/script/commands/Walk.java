package com.github.Danice123.javamon.script.commands;

import java.util.HashMap;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.entity.Entity;
import com.github.Danice123.javamon.entity.Walkable;
import com.github.Danice123.javamon.script.ScriptException;

public class Walk extends Command {

	@Override
	public void execute(Game game, HashMap<String, String> strings, Entity target) throws ScriptException {
		if (args[0].toLowerCase().equals("p")) {
			for (int i = 1; i < args.length; i++) {
				game.getPlayer().walk(getDir(game, args[i]));
			}
		} else if (args[0].toLowerCase().equals("t")) {
			try {
				for (int i = 1; i < args.length; i++) {
					((Walkable) target).walk(getDir(game, args[i]));
				}
			} catch (ClassCastException e) {
				throw new ScriptException(ScriptException.BAD_TARGET);
			}
		} else {
			Entity e = game.getWorld().getEntity(args[0]);
			if (e != null) {
				try {
					for (int i = 1; i < args.length; i++) {
						((Walkable) e).walk(getDir(game, args[i]));
					}
				} catch (ClassCastException error) {
					throw new ScriptException(ScriptException.BAD_TARGET);
				}
			} else {
				throw new ScriptException(ScriptException.BAD_ARGS);
			}
		}
	}

}
