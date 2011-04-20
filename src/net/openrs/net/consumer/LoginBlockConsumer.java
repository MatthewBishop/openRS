package net.openrs.net.consumer;

import net.openrs.event.Event;
import net.openrs.event.EventConsumer;
import net.openrs.net.io.impl.LoginBlock;

public class LoginBlockConsumer extends EventConsumer {

	@SuppressWarnings("unchecked")
	public LoginBlockConsumer() {
		super(LoginBlock.class);
	}

	@Override
	public void consume(Event event) throws Exception {
		// TODO Auto-generated method stub

	}

}
