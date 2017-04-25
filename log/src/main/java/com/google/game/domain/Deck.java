package com.google.game.domain;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.repackaged.com.google.common.collect.ImmutableList;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Deck {
	
	@Id
	private long id;
	private List<String> elems;
	
	private Deck(){}
	
	public Deck(long id) {
		this.id = id;
	}
	
	public void add(String smth) {
		if (elems == null)
			elems = new ArrayList<String>();
		elems.add(smth);
	}
	public String pop(){
		int index = elems.size()-1;
		String ans = elems.get(index);
		elems.remove(index);
		return ans;
	}
	
	public List<String> get() {
		return elems == null ? null : ImmutableList.copyOf(elems);
	}
	
	public long getId() {
		return id;
	}
	
	public void shuffle(){
		
		for (int i=0;i<(int)Math.round(Math.random()*10);++i){
			int size = elems.size();
			for (;size>1;)
			{
				int index = (int) Math.round(Math.random()*(--size));
				String el = elems.get(index);
				elems.remove(index);
				elems.add(el);
			}
		}
		
	}
}
