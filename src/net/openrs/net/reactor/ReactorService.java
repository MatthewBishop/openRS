package net.openrs.net.reactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

import net.openrs.event.EventProducer;
import net.openrs.service.Service;

/**
 * A {@link Service} and {@link EventProducer} that implements the reactor
 * design pattern in a completely asynchronous environment. Performs
 * non-blocking {@link Selector#selectNow()} calls and produces the resulting
 * {@link ReactorEvent} objects.
 * 
 * @author Blake Beaupain
 */
public final class ReactorService extends EventProducer implements Service {

	/** The selector to use. */
	private final Selector selector;

	/**
	 * Creates a new <code>ReactorService</code>.
	 * 
	 * @param selector
	 *            The <code>Selector</code> to use
	 */
	public ReactorService(Selector selector) {
		this.selector = selector;
	}

	@Override
	public void execute() throws IOException {
		selector.selectNow();
		for (SelectionKey selectionKey : selector.selectedKeys()) {
			produce(new ReactorEvent(selector, selectionKey));
		}
		selector.selectedKeys().clear();
	}

}
