package net.openrs.net.io.impl;

import net.openrs.net.io.Message;

public class LoginResponse extends Message {

	private int opcode;

	private long serverKey;

	public void setServerKey(long serverKey) {
		this.serverKey = serverKey;
	}

	public long getServerKey() {
		return serverKey;
	}

	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}

	public int getOpcode() {
		return opcode;
	}

}
