package net.openrs.net.codec;

import java.nio.ByteBuffer;

import net.openrs.net.io.Message;
import net.openrs.net.reactor.ReactorSession;

public abstract class Encoder {

	private final ReactorSession session;

	public Encoder(ReactorSession session) {
		this.session = session;
	}

	public abstract ByteBuffer encode(Message message);

	public ReactorSession getSession() {
		return session;
	}

}
