package com.github.Danice123.javamon.script;

public class ScriptException extends Throwable {

	private static final long serialVersionUID = -7266486982940889573L;
	public static final int BAD_ARGS = 0;
	public static final int BAD_TARGET = 1;
	public static final int BAD_COMMAND = 2;
	public static final int BAD_STRING = 3;
	
	private int reason;
	
	public ScriptException(int reason) {
		this.reason = reason;
	}
	
	public String getMessage() {
		switch (reason) {
		case BAD_ARGS:
			return "Bad Arguments";
		case BAD_TARGET:
			return "Bad Target Entity";
		case BAD_COMMAND:
			return "Bad Command";
		case BAD_STRING:
			return "Bad String";
		default:
			return null;
		}
	}
}
