package com.google.game.domain;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.repackaged.com.google.common.collect.ImmutableList;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Log {
	
	@Id
	private long id;
	private List<String> events;
	
	private Log() {}
	
	public Log(long id) {
		this.id = id;
	}
	
	public void addEvent(String event) {
		if (events == null)
			events = new ArrayList<String>();
		events.add(event);
	}
	
	public List<String> getEvents() {
		return events == null ? null : ImmutableList.copyOf(events);
	}
	
	public long getId() {
		return id;
	}
	
}
