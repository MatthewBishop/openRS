package net.openrs.net.codec.r317;

import java.nio.ByteBuffer;

import net.openrs.net.codec.Encoder;
import net.openrs.net.codec.r317.encoders.R317LoginEncoder;
import net.openrs.net.io.Message;
import net.openrs.net.reactor.ReactorSession;

public class R317Encoder extends Encoder {

	private R317LoginEncoder loginEncoder;

	public R317Encoder(ReactorSession session) {
		super(session);
		loginEncoder = new R317LoginEncoder(session);
	}

	@Override
	public ByteBuffer encode(Message message) {
		switch (getSession().getState()) {
		case CONNECTED:
		case LOGGING_IN:
			return loginEncoder.encode(message);
		case UPDATING:
			// TODO: Update encoder.
			break;
		case LOGGED_IN:
			// TODO: Game stream encoding.
			break;
		}
		return null;
	}

}
