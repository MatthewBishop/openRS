package net.openrs.event;

/**
 * A consumer of {@link Event} instances.
 * 
 * @author Blake Beaupain
 */
public abstract class EventConsumer {

	/**
	 * Creates a new <code>EventConsumer</code> instance.
	 * 
	 * @param eventTypes
	 *            The types of <code>Event</code> objects this consumer is
	 *            interested in. Automatically performs the binding of the types
	 *            within the <code>EventProcessor</code>.
	 */
	public EventConsumer(Class<? extends Event>... eventTypes) {
		bind(eventTypes);
	}

	/**
	 * Binds the given <code>Event</code> types to this consumer.
	 * 
	 * @param eventTypes
	 *            The types that this consumer is interested in
	 */
	public void bind(Class<? extends Event>... eventTypes) {
		for (Class<? extends Event> eventType : eventTypes) {
			EventProcessor.getInstance().bind(eventType, this);
		}
	}

	/**
	 * Unbinds the given <code>Event</code> types from this consumer.
	 * 
	 * @param eventTypes
	 *            The types that this consumer is no longer interested in
	 */
	public void unbind(Class<? extends Event>... eventTypes) {
		for (Class<? extends Event> eventType : eventTypes) {
			EventProcessor.getInstance().unbind(eventType, this);
		}
	}

	/**
	 * Consumes an <code>Event</code>.
	 * 
	 * @param event
	 *            The event to consume
	 */
	public abstract void consume(Event event) throws Exception;

}
