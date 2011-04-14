package net.openrs.net.reactor;

import java.nio.channels.SelectionKey;

import net.openrs.event.Event;
import net.openrs.event.EventConsumer;

/**
 * A {@link EventConsumer} for events related to the {@link ReactorService}.
 * 
 * @author Blake Beaupain
 */
public class ReactorEventConsumer extends EventConsumer {

	/**
	 * Creates a new ReactorEventConsumer.
	 */
	@SuppressWarnings("unchecked")
	public ReactorEventConsumer() {
		super(ReactorEvent.class);
	}

	@Override
	public void consume(Event event) {
		ReactorEvent ev = (ReactorEvent) event;
		SelectionKey key = ev.getSelectionKey();
		ReactorSession session = (ReactorSession) key.attachment();
		if (session == null) {
			// Initialize and attach the session.
			key.attach(session = new ReactorSession(ev.getSelector(), key));
		}
		session.ready(); // Let the session handle I/O.
	}
	
}
