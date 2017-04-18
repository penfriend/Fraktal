package com.google.game.wrapped;

public class WrappedLong {
	
	private long id;
	
	private WrappedLong() {}
	
	public WrappedLong(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
}
