package com.google.game.wrapped;

public class WrappedBoolean {
	
	private boolean success;
	
	private WrappedBoolean() {}
	
	public WrappedBoolean(boolean success) {
		this.success = success;
	}
	
	public boolean getSuccess() {
		return success;
	}
	
}
