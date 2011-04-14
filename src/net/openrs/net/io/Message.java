package net.openrs.net.io;

import java.nio.ByteBuffer;

import net.openrs.event.Event;

public class Message implements Event {

	private MessageHeader header;

	private ByteBuffer contents;

	public void setHeader(MessageHeader header) {
		this.header = header;
	}

	public MessageHeader getHeader() {
		return header;
	}

	public void setContents(ByteBuffer contents) {
		this.contents = contents;
	}

	public ByteBuffer getContents() {
		return contents;
	}

}
