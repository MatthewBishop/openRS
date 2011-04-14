package net.openrs.net.io.impl;

import net.openrs.net.io.Message;

public class LoginRequest extends Message {

	private int nameHash;

	public void setNameHash(int nameHash) {
		this.nameHash = nameHash;
	}

	public int getNameHash() {
		return nameHash;
	}

}
