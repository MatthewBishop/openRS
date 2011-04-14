package net.openrs.net.reactor;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

import net.openrs.event.Event;

/**
 * An {@link Event} that occurs within the reactor system.
 * 
 * @author Blake Beaupain
 */
public class ReactorEvent implements Event {

	/** The <code>Selector</code> responsible for selecting the event. */
	private final Selector selector;

	/** The <code>SelectionKey</code> of the event. */
	private final SelectionKey selectionKey;

	/**
	 * Creates a new <code>ReactorEvent</code>.
	 * 
	 * @param selector
	 *            The {@link Selector} responsible for selecting the event
	 * @param selectionKey
	 *            The {@link SelectionKey} of the event
	 */
	public ReactorEvent(Selector selector, SelectionKey selectionKey) {
		this.selector = selector;
		this.selectionKey = selectionKey;
	}

	/**
	 * Gets the <code>Selector</code> that selected the event.
	 * 
	 * @return The selector
	 */
	public Selector getSelector() {
		return selector;
	}

	/**
	 * Gets the <code>SelectionKey</code> of the event.
	 * 
	 * @return The selection key
	 */
	public SelectionKey getSelectionKey() {
		return selectionKey;
	}

}
