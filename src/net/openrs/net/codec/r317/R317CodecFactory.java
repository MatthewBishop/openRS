package net.openrs.net.codec.r317;

import net.openrs.net.codec.CodecFactory;
import net.openrs.net.codec.Decoder;
import net.openrs.net.codec.Encoder;
import net.openrs.net.reactor.ReactorSession;

public class R317CodecFactory extends CodecFactory {

	@Override
	public Encoder getEncoder(ReactorSession session) {
		return new R317Encoder(session);
	}

	@Override
	public Decoder getDecoder(ReactorSession session) {
		return new R317Decoder(session);
	}

}
