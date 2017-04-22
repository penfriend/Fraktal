package com.google.game.wrapped;

public class WrappedInteger {
	
	private boolean success;
	private int amount;
	
	private WrappedInteger() {}
	
	public WrappedInteger(int amount, boolean status) {
		this.amount = amount;
		this.success = status;
	}
	
	public boolean getSuccess() {
		return success;
	}
	
	public int getAmount() {
		return amount;
	}
	
}
