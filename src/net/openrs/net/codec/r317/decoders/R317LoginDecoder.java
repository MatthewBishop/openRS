package net.openrs.net.codec.r317.decoders;

import java.nio.ByteBuffer;

import net.openrs.net.codec.r317.R317Decoder;
import net.openrs.net.io.Message;
import net.openrs.net.reactor.ReactorSession;

public class R317LoginDecoder extends R317Decoder {

	public R317LoginDecoder(ReactorSession session) {
		super(session);
	}

	@Override
	public Message decode(ByteBuffer buffer) {
		/*
		 * TODO: Define two different decoders, one will decode a LoginRequest
		 * and the other will decode a LoginBlock. We'll decide which one of
		 * these to use by checking getSession().getState() and using either
		 * CONNECTED or LOGGING_IN.
		 */
		return null;
	}

}
