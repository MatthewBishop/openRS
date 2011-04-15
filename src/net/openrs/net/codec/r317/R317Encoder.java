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
		/*
		 * TODO: Make a system of encoders (maybe RS317Encoder[] indexed by
		 * opcodes) that will encode each individual message instead of a bunch
		 * of if statements in this one.
		 */
		return null;
	}

	public static final int RESPOND_LOGIN = 0;

}
