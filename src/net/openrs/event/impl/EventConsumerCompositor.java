package net.openrs.event.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.openrs.event.Event;
import net.openrs.event.EventConsumer;

public class EventConsumerCompositor extends EventConsumer {

	private List<EventConsumer> consumers = new ArrayList<EventConsumer>();

	@SuppressWarnings("unchecked")
	public EventConsumerCompositor(EventConsumer... consumers) {
		register(consumers);
	}

	@Override
	public void consume(Event event) throws Exception {
		try {
			for (EventConsumer consumer : consumers) {
				consumer.consume(event);
			}
		} catch (CompositorInterruptException ex) {
			// Interrupted.
		}
	}

	public void register(EventConsumer... consumers) {
		this.consumers.addAll(Arrays.asList(consumers));
	}

	public void unregister(EventConsumer... consumers) {
		this.consumers.removeAll(Arrays.asList(consumers));
	}

	public static class CompositorInterruptException extends Exception {

		public CompositorInterruptException() {

		}

		public CompositorInterruptException(String message) {
			super(message);
		}

		private static final long serialVersionUID = -2665582072336608052L;

	}

}
