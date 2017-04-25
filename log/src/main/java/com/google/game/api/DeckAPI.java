package com.google.game.api;

import java.util.ArrayList;
import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.game.wrapped.*;
import com.googlecode.objectify.Key;
import com.google.game.constants.Constants;
import com.google.game.domain.Deck;
import com.google.game.domain.Log;

import static com.google.game.objectify.OfyService.factory;
import static com.google.game.objectify.OfyService.ofy;

@Api (name = "deckAPI", version = "v1", scopes = {Constants.EMAIL_SCOPE},
	clientIds = {Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID},
	description = "Deck API")
public class DeckAPI {
	
	@ApiMethod (name = "createDeck", path = "createDeck", httpMethod = HttpMethod.POST)
	public WrappedLong createDeck() {
		Key<Deck> key = factory().allocateId(Deck.class);
		long id = key.getId();
		
		Deck d = new Deck(id);
		
		ofy().save().entity(d).now();
		
		return new WrappedLong(id);
	}
	
	@ApiMethod (name = "putCard", path = "putCard", httpMethod = HttpMethod.POST)
	public WrappedBoolean add(@Named("id") long id, @Named("card") String card) {
		Key<Deck> key = Key.create(Deck.class, id);
		Deck d = ofy().load().key(key).now();
		
		if (d == null)
			return new WrappedBoolean(false);
		
		d.add(card);
		ofy().save().entity(d).now();
		
		return new WrappedBoolean(true);
	}
	
	@ApiMethod (name = "takeCard", path = "takeCard", httpMethod = HttpMethod.POST)
	public WrappedEvents takeCard(@Named("id") long id) {
		Key<Deck> key = Key.create(Deck.class, id);
		Deck deck = ofy().load().key(key).now();
		
		if (deck == null)
			return new WrappedEvents(null, false);
		
		if (cardsLeft(id).getAmount()!=0)
		{
			String s = deck.pop();
			ofy().save().entity(deck).now();
			List<String> res = new ArrayList<String>(1);
			res.add(s);
			return new WrappedEvents(res, true);
		}
		
		return new WrappedEvents(null, true);
	}
	
	@ApiMethod (name = "cardsLeft", path = "cardsLeft", httpMethod = HttpMethod.POST)
	public WrappedInteger cardsLeft(@Named("id") long id) {
		Key<Deck> key = Key.create(Deck.class, id);
		Deck d = ofy().load().key(key).now();
		
		if (d == null)
			return new WrappedInteger(0, false);
		
		List<String> events = d.get();
		if (events == null || events.isEmpty())
			return new WrappedInteger(0, true);
		
		return new WrappedInteger(events.size(), true);
	}
	
	@ApiMethod (name = "takeCards", path = "takeCards", httpMethod = HttpMethod.POST)
	public WrappedEvents take(@Named("id") long id, @Named("amount") int amount) {
		Key<Deck> key = Key.create(Deck.class, id);
		Deck d = ofy().load().key(key).now();
		
		if (d == null)
			return new WrappedEvents(null, false);
		
		List<String> cards = d.get();
		if (cards == null || cards.isEmpty() || amount < 1)
			return new WrappedEvents(null, true);
		
		List<String> res = new ArrayList<String>(amount);
		while(amount-->0)
		{
			res.add(d.pop());
		}
		ofy().save().entity(d).now();
		return new WrappedEvents(res, true);
	}
	
	@ApiMethod (name = "initTheDeck", path = "initTheDeck", httpMethod = HttpMethod.POST)
	public WrappedEvents initTheDeck(@Named("id") long id, @Named("csvCardArr") String sin) {
		Key<Deck> key = Key.create(Deck.class, id);
		Deck d = ofy().load().key(key).now();
		
		if (d == null)
			return new WrappedEvents(null, false);
		
		for (String s:sin.split(","))
			d.add(s);
		
		ofy().save().entity(d).now();
		return new WrappedEvents(null, true);
	}
	
	@ApiMethod (name = "shuffle", path = "shuffle", httpMethod = HttpMethod.POST)
	public WrappedEvents takeSinceTo(@Named("id") long id) {
		Key<Deck> key = Key.create(Deck.class, id);
		Deck deck = ofy().load().key(key).now();
		
		if (deck==null)
			return new WrappedEvents(null, false);
		
		deck.shuffle();
		ofy().save().entity(deck).now();
		
		return new WrappedEvents(null, true);
	}
	
	@ApiMethod (name = "delete", path = "delete", httpMethod = HttpMethod.POST)
	public WrappedEvents delete(@Named("id") long id) {
		
		Key<Deck> key = Key.create(Deck.class, id);
		ofy().delete().entity(key).now();
		return new WrappedEvents(null, true);
	}
	
}
