package net.openrs.net.codec.r317;

import java.nio.ByteBuffer;

import net.openrs.net.codec.Decoder;
import net.openrs.net.codec.r317.decoders.R317LoginDecoder;
import net.openrs.net.codec.r317.decoders.R317MessageDecoder;
import net.openrs.net.codec.r317.decoders.R317UpdateDecoder;
import net.openrs.net.io.Message;
import net.openrs.net.reactor.ReactorSession;

public class R317Decoder extends Decoder {

	private R317LoginDecoder loginDecoder;

	private R317UpdateDecoder updateDecoder;

	private R317MessageDecoder messageDecoder;

	public R317Decoder(ReactorSession session) {
		super(session);
		loginDecoder = new R317LoginDecoder(session);
		updateDecoder = new R317UpdateDecoder(session);
		messageDecoder = new R317MessageDecoder(session);
	}

	@Override
	public Message decode(ByteBuffer buffer) {
		Message message = null;
		switch (getSession().getState()) {
		case CONNECTED:
			message = handleServiceRequest(buffer);
			break;
		case UPDATING:
			message = updateDecoder.decode(buffer);
			break;
		case LOGGING_IN:
			message = loginDecoder.decode(buffer);
			break;
		case LOGGED_IN:
			message = messageDecoder.decode(buffer);
			break;
		}
		if (message != null) {
			message.setSession(getSession());
		}
		return message;
	}

	private Message handleServiceRequest(ByteBuffer buffer) {
		int request = buffer.get() & 0xff;
		if (request == REQUEST_LOGIN) {
			return loginDecoder.decode(buffer);
		}
		if (request == REQUEST_UPDATE) {
			return updateDecoder.decode(buffer);
		}
		return null;
	}

	public static final int REQUEST_LOGIN = 14;

	public static final int REQUEST_UPDATE = 15;

}
