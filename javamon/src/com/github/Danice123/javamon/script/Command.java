package com.github.Danice123.javamon.script;

import java.util.HashMap;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.entity.Dir;
import com.github.Danice123.javamon.entity.Entity;
import com.github.Danice123.javamon.entity.Walkable;
import com.github.Danice123.javamon.screen.ChatBox;

public class Command {
	
	public final CommandType type;
	public String[] args;

	public Command(CommandType type, String[] args) {
		this.type = type;
		this.args = args;
	}
	
	public void execute(Game game, HashMap<String, String> strings, Entity target) throws ScriptException {
		try {
		switch (type) {
		case Display:
			try {
				Object lock = new Object();
				if (game.getPlayer().menuOpen) {
					new ChatBox(game, game.getWorld(), parseString(strings.get(args[0]), strings), lock); //Should never occur
					System.out.println("What?");
				} else {
					new ChatBox(game, game.getWorld(), parseString(strings.get(args[0]), strings), lock);
					game.getPlayer().menuOpen = true;
				}
				synchronized (lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {}
				}
				game.getPlayer().menuOpen = false;
			} catch (NullPointerException e) {
				throw new ScriptException(ScriptException.BAD_STRING);
			}
			break;
		case Set:
			game.getPlayer().setFlag(parseString(args[0], strings), true);
			break;
		case Unset:
			game.getPlayer().setFlag(parseString(args[0], strings), false);
			break;
		case Face:
			if (args[0].toLowerCase().equals("p")) {
				game.getPlayer().face(getDir(game, args[1]));
			} else if (args[0].toLowerCase().equals("t")) {
				try {
					((Walkable) target).face(getDir(game, args[1]));
				} catch (ClassCastException e) {
					throw new ScriptException(ScriptException.BAD_TARGET);
				}
			} else {
				throw new ScriptException(ScriptException.BAD_ARGS);
			}
			break;
		case Walk:
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
				throw new ScriptException(ScriptException.BAD_ARGS);
			}
			break;
//		case Lock:
//			game.player.menu = new MenuEmpty(game);
//			game.player.menuOpen = true;
//			break;
//		case Unlock:
//			game.player.menuOpen = false;
//			break;
//		case GiveItem:
//			try {
//				game.player.inventory.addItem(((ItemHolder) target).getItem());
//			} catch (ClassCastException e) {
//				throw new ScriptException(ScriptException.BAD_TARGET);
//			}
//			break;
		default:
			break;
		}
		} catch (NullPointerException e) {
			throw new ScriptException(ScriptException.BAD_COMMAND);
		}
	}
	
	private Dir getDir(Game game, String s) throws ScriptException {
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
	
	private String parseString(String s, HashMap<String, String> strings) {
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
	
	public static enum CommandType {
		Display, Set, Unset, Branch, Stop, Face, Walk, Lock, Unlock, GiveItem;
		
		public static CommandType parseString(String s) {
			switch (s.toLowerCase()) {
			case "display":
				return Display;
			case "set":
				return Set;
			case "unset":
				return Unset;
			case "branch":
				return Branch;
			case "stop":
				return Stop;
			case "face":
				return Face;
			case "walk":
				return Walk;
			case "lock":
				return Lock;
			case "unlock":
				return Unlock;
			case "giveitem":
				return GiveItem;
			default:
				return null;
			}
		}
	}
}
