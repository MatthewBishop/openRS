package net.openrs.net.codec.r317;

import java.nio.ByteBuffer;

import net.openrs.net.codec.Decoder;
import net.openrs.net.io.Message;
import net.openrs.net.io.impl.LoginRequest;
import net.openrs.net.reactor.ReactorSession;

public class R317Decoder extends Decoder {

	public R317Decoder(ReactorSession session) {
		super(session);
	}

	@Override
	public Message decode(ByteBuffer buffer) {
		switch (getSession().getState()) {
		case CONNECTED:
			return decodeServiceRequest(buffer);
		case UPDATING:
			break;
		case LOGGING_IN:
			break;
		case LOGGED_IN:
			break;
		}
		return null;
	}

	private Message decodeServiceRequest(ByteBuffer buffer) {
		int requestOpcode = buffer.get() & 0xff;
		if (requestOpcode == 14) { // Login request
			LoginRequest msg = new LoginRequest();
			msg.setNameHash(buffer.get() & 0xff);
			return msg;
		} else if (requestOpcode == 15) { // Update request

		}
		return null;
	}

}
