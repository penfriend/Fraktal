package com.google.game.wrapped;

import java.util.List;
import com.google.appengine.repackaged.com.google.common.collect.ImmutableList;

public class WrappedEvents {
	
	private boolean success;
	private List<String> events;
	
	private WrappedEvents() {}
	
	public WrappedEvents(List<String> events, boolean status) {
		this.events = events;
		this.success = status;
	}
	
	public boolean getSuccess() {
		return success;
	}
	
	public List<String> getEvents() {
		return events == null ? null : ImmutableList.copyOf(events);
	}
	
}
