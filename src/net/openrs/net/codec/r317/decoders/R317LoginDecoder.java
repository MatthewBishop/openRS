package net.openrs.net.codec.r317.decoders;

import java.nio.ByteBuffer;

import net.openrs.net.codec.Decoder;
import net.openrs.net.consumer.LoginRequestConsumer;
import net.openrs.net.io.Message;
import net.openrs.net.io.impl.LoginRequest;
import net.openrs.net.reactor.ReactorSession;

public class R317LoginDecoder extends Decoder {

	private R317LoginRequestDecoder loginRequestDecoder;

	public R317LoginDecoder(ReactorSession session) {
		super(session);
		loginRequestDecoder = new R317LoginRequestDecoder(session);
		new LoginRequestConsumer();
	}

	@Override
	public Message decode(ByteBuffer buffer) {
		switch (getSession().getState()) {
		case CONNECTED:
			return loginRequestDecoder.decode(buffer);
		case LOGGING_IN:
			System.out.println("wow");
			break;
		}
		return null;
	}

	private class R317LoginRequestDecoder extends Decoder {

		public R317LoginRequestDecoder(ReactorSession session) {
			super(session);
		}

		@Override
		public Message decode(ByteBuffer buffer) {
			LoginRequest message = new LoginRequest();
			message.setNameHash(buffer.get() & 0xff);
			return message;
		}

	}

}
