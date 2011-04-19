package net.openrs.net.codec.r317.decoders;

import java.nio.ByteBuffer;

import net.openrs.net.codec.Decoder;
import net.openrs.net.io.Message;
import net.openrs.net.reactor.ReactorSession;

public class R317UpdateDecoder extends Decoder {

	public R317UpdateDecoder(ReactorSession session) {
		super(session);
	}

	@Override
	public Message decode(ByteBuffer buffer) {
		return null;
	}

}
