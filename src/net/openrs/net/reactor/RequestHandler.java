package net.openrs.net.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import net.openrs.net.io.Message;

public final class RequestHandler {

	public static void serveAccept(ReactorSession session) throws IOException {
		ServerSocketChannel ssc = (ServerSocketChannel) session.getSelectionKey().channel();
		SocketChannel socketChannel = ssc.accept();
		if (socketChannel == null) {
			return;
		}
		socketChannel.configureBlocking(false);
		socketChannel.register(session.getSelector(), SelectionKey.OP_READ);
	}

	public static void serveRead(ReactorSession session) throws IOException {
		ByteBuffer buffer = session.getInBuffer();
		SocketChannel socketChannel = (SocketChannel) session.getSelectionKey().channel();

		// Read the data.
		if (socketChannel.read(buffer) == -1) {
			session.disconnect();
			return;
		}

		// Decode and produce the message.
		buffer.flip();
		Message message = null;
		if ((message = session.getDecoder().decode(buffer)) == null) {
			// The message is incomplete.
			buffer.flip();
			buffer.compact();
			return;
		}
		session.produce(message);
	}

	public static void serveWrite(ReactorSession session) throws IOException {
		ByteBuffer buffer = session.getOutBuffer();
		SocketChannel socketChannel = (SocketChannel) session.getSelectionKey().channel();

		// Send the data.
		buffer.flip();
		if (socketChannel.write(buffer) < buffer.remaining()) {
			// Not everything was sent, compact the buffer.
			buffer.compact();
			return;
		}
		buffer.clear();
	}

}
