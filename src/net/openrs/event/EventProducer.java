package net.openrs.event;

/**
 * An abstract class providing the functionality of producing an {@link Event}
 * instance to the core {@link EventProcessor}.
 * 
 * @author Blake Beaupain
 */
public abstract class EventProducer {

	/**
	 * Produces an <code>Event</code>. The event is broadcasted through the
	 * <code>EventProcessor</code> singleton instance.
	 * 
	 * @param event
	 *            The event to produce
	 */
	public final void produce(Event event) {
		EventProcessor.getInstance().broadcast(event);
	}

}
