package net.openrs.net.codec.r317.encoders;

import java.nio.ByteBuffer;

import net.openrs.net.codec.Encoder;
import net.openrs.net.io.Message;
import net.openrs.net.io.impl.LoginResponse;
import net.openrs.net.reactor.ReactorSession;
import net.openrs.net.reactor.ReactorSession.State;

public class R317LoginEncoder extends Encoder {

	public R317LoginEncoder(ReactorSession session) {
		super(session);
	}

	@Override
	public ByteBuffer encode(Message message) {
		ByteBuffer buffer = ByteBuffer.allocate(32);
		LoginResponse msg = (LoginResponse) message;
		buffer.put((byte) msg.getOpcode());
		if (msg.getOpcode() == 0) {
			buffer.putLong(0); // First 8 bytes ignored.
			buffer.putLong(msg.getServerKey());
			getSession().setState(State.LOGGING_IN);
		}
		// TODO: Check for other opcodes.
		return buffer;
	}

}
