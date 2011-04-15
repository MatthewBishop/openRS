package net.openrs.net.consumer;

import java.security.SecureRandom;

import net.openrs.event.Event;
import net.openrs.event.EventConsumer;
import net.openrs.net.io.impl.LoginRequest;
import net.openrs.net.io.impl.LoginResponse;

public class LoginRequestConsumer extends EventConsumer {

	private SecureRandom rand = new SecureRandom();

	@SuppressWarnings("unchecked")
	public LoginRequestConsumer() {
		super(LoginRequest.class);
	}

	@Override
	public void consume(Event event) throws Exception {
		LoginRequest request = (LoginRequest) event;
		LoginResponse response = new LoginResponse();

		// Write the response.
		response.setOpcode(0);
		response.setServerKey(rand.nextLong());

		// Send the response.
		request.getSession().send(response);
	}

}
