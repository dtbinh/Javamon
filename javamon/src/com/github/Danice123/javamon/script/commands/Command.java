package com.github.Danice123.javamon.script.commands;

import java.util.HashMap;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.entity.Dir;
import com.github.Danice123.javamon.entity.Entity;
import com.github.Danice123.javamon.script.ScriptException;

public abstract class Command {
	
	public String[] args;
	
	public abstract void execute(Game game, HashMap<String, String> strings, Entity target) throws ScriptException;
	
	protected String parseString(String s, HashMap<String, String> strings) {
		do {
			int b = s.indexOf("<");
			if (b != -1) {
				int e = s.indexOf(">", b);
				s = s.substring(0, b) + strings.get(s.substring(b + 1, e)) + s.substring(e + 1, s.length());
			} else {
				break;
			}
		} while (true);
		return s;
	}
	
	protected Dir getDir(Game game, String s) throws ScriptException {
		switch (s.toLowerCase()) {
		case "n":
			return Dir.North;
		case "s":
			return Dir.South;
		case "e":
			return Dir.East;
		case "w":
			return Dir.West;
		case "p":
			Dir p = game.getPlayer().getFacing();
			switch (p) {
			case East:
				return Dir.West;
			case North:
				return Dir.South;
			case South:
				return Dir.North;
			case West:
				return Dir.East;
			default:
				break;
			}
		default:
			throw new ScriptException(ScriptException.BAD_ARGS);
		}
	}
}
