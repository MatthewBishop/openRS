package net.openrs.net.codec;

import net.openrs.net.reactor.ReactorSession;

public abstract class CodecFactory {

	private static CodecFactory instance;

	public abstract Encoder getEncoder(ReactorSession session);

	public abstract Decoder getDecoder(ReactorSession session);

	public static void setInstance(CodecFactory instance) {
		CodecFactory.instance = instance;
	}

	public static CodecFactory getInstance() {
		return instance;
	}

}
