package com.github.Danice123.javamon.entity;

public enum Dir {
	North, South, East, West, NorthW, SouthW, EastW, WestW;
	
	public static Dir toWalk(Dir dir) {
		switch(dir) {
		case North:
			return NorthW;
		case South:
			return SouthW;
		case East:
			return EastW;
		case West:
			return WestW;
		default:
			return dir;
		}
	}
}
