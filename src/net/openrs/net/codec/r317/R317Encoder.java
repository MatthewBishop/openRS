package net.openrs.net.codec.r317;

import java.nio.ByteBuffer;

import net.openrs.net.codec.Encoder;
import net.openrs.net.io.Message;
import net.openrs.net.reactor.ReactorSession;

public class R317Encoder extends Encoder {

	public R317Encoder(ReactorSession session) {
		super(session);
	}

	@Override
	public ByteBuffer encode(Message message) {
		return null;
	}

}
