package net.openrs.net.reactor;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.LinkedList;
import java.util.Queue;

import net.openrs.event.EventProducer;
import net.openrs.net.codec.CodecFactory;
import net.openrs.net.codec.Decoder;
import net.openrs.net.codec.Encoder;
import net.openrs.net.io.Message;
import net.openrs.util.Configuration;

/**
 * A state-based object that represents an active connection and it's
 * relationship to the reactor system.
 * 
 * @author Blake Beaupain
 */
public final class ReactorSession extends EventProducer {

	public enum State {
		CONNECTED, UPDATING, LOGGING_IN, LOGGED_IN
	}

	/** The <code>Selector</code>. */
	private final Selector selector;

	/** The <code>SelectionKey</code>. */
	private final SelectionKey selectionKey;

	private final ByteBuffer inBuffer = ByteBuffer.allocate(Configuration.getBufferSize());

	private final ByteBuffer outBuffer = ByteBuffer.allocate(Configuration.getBufferSize());

	private final Encoder encoder = CodecFactory.getInstance().getEncoder(this);

	private final Decoder decoder = CodecFactory.getInstance().getDecoder(this);

	private final Queue<Message> outQ = new LinkedList<Message>();

	private State state = State.CONNECTED;

	/**
	 * Creates a new <code>ReactorSession</code>.
	 * 
	 * @param selector
	 *            The {@link Selector}
	 * @param selectionKey
	 *            The {@link SelectionKey}
	 */
	public ReactorSession(Selector selector, SelectionKey selectionKey) {
		this.selector = selector;
		this.selectionKey = selectionKey;
	}

	public void send(Message message) {
		getOutQueue().offer(message);
		selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
	}

	/**
	 * Notifies the reactor session that an I/O operation is ready to be
	 * performed. The session will determine by itself what type of I/O
	 * operation is ready and will react accordingly.
	 */
	public void ready() {
		if (!selectionKey.isValid()) {
			return;
		}

		// Service the request.
		try {
			if (selectionKey.isAcceptable()) {
				RequestHandler.serveAccept(this);
			}
			if (selectionKey.isReadable()) {
				RequestHandler.serveRead(this);
			}
			if (selectionKey.isWritable()) {
				RequestHandler.serveWrite(this);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			disconnect();
		}
	}

	public void disconnect() {
		selectionKey.cancel();
		try {
			selectionKey.channel().close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Gets the <code>Selector</code>.
	 * 
	 * @return The selector
	 */
	public Selector getSelector() {
		return selector;
	}

	/**
	 * Gets the <code>SelectionKey</code>.
	 * 
	 * @return The selection key
	 */
	public SelectionKey getSelectionKey() {
		return selectionKey;
	}

	public ByteBuffer getInBuffer() {
		return inBuffer;
	}

	public ByteBuffer getOutBuffer() {
		return outBuffer;
	}

	public Encoder getEncoder() {
		return encoder;
	}

	public Decoder getDecoder() {
		return decoder;
	}

	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public Queue<Message> getOutQueue() {
		return outQ;
	}

}
