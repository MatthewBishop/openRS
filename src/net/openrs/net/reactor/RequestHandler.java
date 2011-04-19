package net.openrs.net.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Queue;

import net.openrs.net.io.Message;

public final class RequestHandler {

	public static void serveAccept(ReactorSession session) throws IOException {
		ServerSocketChannel ssc = (ServerSocketChannel) session.getSelectionKey().channel();
		SocketChannel socketChannel = ssc.accept();
		if (socketChannel == null) {
			return;
		}
		socketChannel.configureBlocking(false);
		SelectionKey key = socketChannel.register(session.getSelector(), SelectionKey.OP_READ);
		key.attach(new ReactorSession(session.getSelector(), key));
	}

	public static void serveRead(ReactorSession session) throws IOException {
		ByteBuffer buffer = session.getInBuffer();
		SocketChannel socketChannel = (SocketChannel) session.getSelectionKey().channel();

		// Read the data.
		int amount = socketChannel.read(buffer);
		if (amount == -1) {
			session.disconnect();
			return; // End of stream.
		}
		if (amount == 0) {
			return; // No bytes read.
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

		// Encode each message into the buffer.
		Queue<Message> q = session.getOutQueue();
		while (!q.isEmpty()) {
			buffer.put(session.getEncoder().encode(q.poll()));
		}
		buffer.flip();

		// Send the data.
		socketChannel.write(buffer);
		if (buffer.hasRemaining()) {
			buffer.compact(); // Could not send everything.
		} else {
			buffer.clear();
			SelectionKey key = session.getSelectionKey();
			key.interestOps(key.interestOps() & (~SelectionKey.OP_WRITE));
		}
	}

}
