package net.openrs.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The core event processing engine. Handles the binding, unbinding, and
 * broadcasting of global events to their respective consumers. When an
 * {@link Event} is bound to any number of {@link EventConsumer} instances, all
 * calls of {@link EventProcessor#broadcast(Event)} will result in the
 * {@link EventConsumer#consume(Event)} method being called for the given
 * <code>EventConsumer</code> instances.
 * 
 * @author Blake Beaupain
 */
public final class EventProcessor {

	/** The singelton instance. */
	private static EventProcessor instance;

	/** The event consumer map. */
	private Map<Class<? extends Event>, List<EventConsumer>> map = new HashMap<Class<? extends Event>, List<EventConsumer>>();

	/**
	 * Broadcasts an event to all bound {@link EventConsumer}s. This is a
	 * <code>protected</code> method, all broadcasting of <code>Event</code>
	 * instances must be done through an {@link EventProducer}.
	 * 
	 * @param event
	 *            The event to broadcast
	 */
	protected void broadcast(Event event) {
		for (EventConsumer consumer : getConsumersOf(event.getClass())) {
			try {
				consumer.consume(event);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Binds an {@link Event} type with a set {@link EventConsumer} instances.
	 * All calls to {@link EventProcessor#broadcast(Event)} with an
	 * <code>Event</code> of the given type as an argument will result in a
	 * {@link EventConsumer#consume(Event)} call on the given consumers.
	 * 
	 * @param eventType
	 *            The type of <code>Event</code> to bind the consumers to
	 * @param consumers
	 *            The <code>EventConsumer</code> instances to consume the given
	 *            event when broadcasted to this event processor
	 */
	protected void bind(Class<? extends Event> eventType, EventConsumer... consumers) {
		getConsumersOf(eventType).addAll(Arrays.asList(consumers));
	}

	/**
	 * Unbinds an {@link Event} type with a set of {@link EventConsumer}
	 * instances. All argued <code>EventConsumer</code> instances will not
	 * receive any broadcasts of the given <code>Event</code> type.
	 * 
	 * @param eventType
	 * @param consumers
	 */
	public void unbind(Class<? extends Event> eventType, EventConsumer... consumers) {
		getConsumersOf(eventType).removeAll(Arrays.asList(consumers));
	}

	/**
	 * Gets a <code>List</code> containing the <code>EventConsumer</code>
	 * instances interested in receiving <code>Event</code>s of the given type.
	 * Performs lazily initialization of non-existent lists.
	 * 
	 * @param eventType
	 *            The type of <code>Event</code>
	 * @return A list of <code>EventConsumer</code> instances interested in the
	 *         given event.
	 */
	private List<EventConsumer> getConsumersOf(Class<? extends Event> eventType) {
		List<EventConsumer> list = map.get(eventType);
		if (list == null) {
			map.put(eventType, list = new ArrayList<EventConsumer>());
		}
		return list;
	}

	/**
	 * Gets the singleton <code>EventProcessor</code> instance.
	 * 
	 * @return The singleton instance
	 */
	public static EventProcessor getInstance() {
		return instance == null ? instance = new EventProcessor() : instance;
	}

}
