package net.openrs.net.codec.r317.decoders;

import java.nio.ByteBuffer;

import net.openrs.net.codec.Decoder;
import net.openrs.net.io.Message;
import net.openrs.net.reactor.ReactorSession;

public class R317MessageDecoder extends Decoder {

	public R317MessageDecoder(ReactorSession session) {
		super(session);
	}

	@Override
	public Message decode(ByteBuffer buffer) {
		/*
		 * TODO: Make a system of decoders (maybe R317Decoder[] indexed by
		 * opcode) that we can pass the buffer along to instead of using a bunch
		 * of if statements in this one.
		 */
		return null;
	}

}
