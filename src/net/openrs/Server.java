package net.openrs;

import java.io.File;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.logging.Logger;

import net.openrs.net.codec.CodecFactory;
import net.openrs.net.reactor.ReactorService;
import net.openrs.service.ServiceEngine;
import net.openrs.util.Configuration;
import net.openrs.util.ScriptInterpreter;

public class Server {

	private static final Logger log = Logger.getLogger("OpenRS");

	public static void main(String[] args) {
		log.info("Starting OpenRS emulator...");

		// TODO: Make the startup procedure more elegant.
		try {
			// Core stuff.
			Configuration.loadConfiguration();
			ServiceEngine engine = new ServiceEngine(Configuration.getTickrate());
			ServiceEngine.setInstance(engine);
			ScriptInterpreter.getInstance().loadScripts(new File(Configuration.getScriptDirectory()));

			// Network stuff.
			CodecFactory.setInstance(Configuration.getCodecFactory());
			Selector selector = Selector.open();
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			ssc.socket().bind(new InetSocketAddress(Configuration.getServerHost(), Configuration.getServerPort()));
			ssc.register(selector, SelectionKey.OP_ACCEPT);

			// Service stuff.
			ReactorService rs = new ReactorService(selector);
			engine.register(rs);
			
			// Get goin'
			engine.start();
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}

		log.info("Startup complete.");
	}

}