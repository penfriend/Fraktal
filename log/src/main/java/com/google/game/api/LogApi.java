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
import com.google.game.domain.Log;

import static com.google.game.objectify.OfyService.factory;
import static com.google.game.objectify.OfyService.ofy;

@Api (name = "logApi", version = "v1", scopes = {Constants.EMAIL_SCOPE},
	clientIds = {Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID})
public class LogApi {
	
	@ApiMethod (name = "createLog", path = "createLog", httpMethod = HttpMethod.POST)
	public WrappedLong createLog() {
		Key<Log> key = factory().allocateId(Log.class);
		long id = key.getId();
		
		Log log = new Log(id);
		
		ofy().save().entity(log).now();
		
		return new WrappedLong(id);
	}
	
	@ApiMethod (name = "add", path = "add", httpMethod = HttpMethod.POST)
	public WrappedBoolean add(@Named("id") long id, @Named("event") String event) {
		Key<Log> key = Key.create(Log.class, id);
		Log log = ofy().load().key(key).now();
		
		if (log == null)
			return new WrappedBoolean(false);
		
		log.addEvent(event);
		ofy().save().entity(log).now();
		
		return new WrappedBoolean(true);
	}
	
	@ApiMethod (name = "takeLast", path = "takeLast", httpMethod = HttpMethod.POST)
	public WrappedEvents takeLast(@Named("id") long id) {
		Key<Log> key = Key.create(Log.class, id);
		Log log = ofy().load().key(key).now();
		
		if (log == null)
			return new WrappedEvents(null, false);
		
		List<String> events = log.getEvents();
		if (events == null || events.isEmpty())
			return new WrappedEvents(null, true);
		
		List<String> result = new ArrayList<String>(1);
		String event = events.get(events.size()-1);
		result.add(event);
		
		return new WrappedEvents(result, true);
	}
	
	@ApiMethod (name = "amount", path = "amount", httpMethod = HttpMethod.POST)
	public WrappedInteger amount(@Named("id") long id) {
		Key<Log> key = Key.create(Log.class, id);
		Log log = ofy().load().key(key).now();
		
		if (log == null)
			return new WrappedInteger(0, false);
		
		List<String> events = log.getEvents();
		if (events == null || events.isEmpty())
			return new WrappedInteger(0, true);
		
		return new WrappedInteger(events.size(), true);
	}
	
	@ApiMethod (name = "take", path = "take", httpMethod = HttpMethod.POST)
	public WrappedEvents take(@Named("id") long id, @Named("index") int index) {
		Key<Log> key = Key.create(Log.class, id);
		Log log = ofy().load().key(key).now();
		
		if (log == null)
			return new WrappedEvents(null, false);
		
		List<String> events = log.getEvents();
		if (events == null || events.isEmpty() || index < 0 || index >= events.size())
			return new WrappedEvents(null, true);
		
		List<String> res = new ArrayList<String>(1);
		String event = events.get(index);
		res.add(event);
		
		return new WrappedEvents(res, true);
	}
	
	@ApiMethod (name = "takeSince", path = "takeSince", httpMethod = HttpMethod.POST)
	public WrappedEvents takeSince(@Named("id") long id, @Named("index") int index) {
		Key<Log> key = Key.create(Log.class, id);
		Log log = ofy().load().key(key).now();
		
		if (log == null)
			return new WrappedEvents(null, false);
		
		List<String> events = log.getEvents();
		if (events == null || events.isEmpty() || index < 0 || index >= events.size())
			return new WrappedEvents(null, true);
		
		int size = events.size() - index;
		List<String> res = new ArrayList<String>(size);
		
		for (int i = index; i < events.size(); i++) {
			String event = events.get(i);
			res.add(event);
		}
		
		return new WrappedEvents(res, true);
	}
	
	@ApiMethod (name = "takeSinceTo", path = "takeSinceTo", httpMethod = HttpMethod.POST)
	public WrappedEvents takeSinceTo(@Named("id") long id, @Named("from") int from, @Named("to") int to) {
		Key<Log> key = Key.create(Log.class, id);
		Log log = ofy().load().key(key).now();
		
		if (log == null)
			return new WrappedEvents(null, false);
		
		List<String> events = log.getEvents();
		boolean fromOut = from < 0 || from >= events.size();
		boolean toOut = to < 0 || to >= events.size()+1;
		boolean less = from < to;
		if (events == null || events.isEmpty() || fromOut || toOut || !less)
			return new WrappedEvents(null, true);
		
		int size = to - from;
		List<String> res = new ArrayList<String>(size);
		
		for (int i = from; i < to; i++) {
			String event = events.get(i);
			res.add(event);
		}
		
		return new WrappedEvents(res, true);
	}
	
}
