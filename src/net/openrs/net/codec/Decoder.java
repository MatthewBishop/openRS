package net.openrs.net.codec;

import java.nio.ByteBuffer;

import net.openrs.net.io.Message;
import net.openrs.net.reactor.ReactorSession;

public abstract class Decoder {

	private final ReactorSession session;

	public Decoder(ReactorSession session) {
		this.session = session;
	}

	public abstract Message decode(ByteBuffer buffer);

	public ReactorSession getSession() {
		return session;
	}

}
