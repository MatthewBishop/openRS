package net.openrs.util;

import java.io.FileReader;
import java.util.Properties;

import net.openrs.net.codec.CodecFactory;

public class Configuration {

	public static final String PROPERTIES = "properties.ini";

	private static String scriptDirectory;

	private static int tickrate;

	private static String serverHost;

	private static int serverPort;

	private static int bufferSize;

	private static CodecFactory codecFactory;

	private static boolean isaacEnabled;

	private static boolean rsaEnabled;

	private static String rsaKeyFile;
	
	private static String loginServerHost;
	
	private static int loginServerPort;

	private Configuration() {
	}

	public static void loadConfiguration() throws Exception {
		Properties props = new Properties();
		props.load(new FileReader(PROPERTIES));
		setScriptDirectory(props.getProperty("script_directory"));
		setTickrate(Integer.parseInt(props.getProperty("tickrate")));
		setServerHost(props.getProperty("server_host"));
		setServerPort(Integer.parseInt(props.getProperty("server_port")));
		setBufferSize(Integer.parseInt(props.getProperty("buffer_size")));
		setCodecFactory((CodecFactory) Class.forName(props.getProperty("codec_factory")).newInstance());
		setIsaacEnabled(Boolean.parseBoolean(props.getProperty("isaac_enabled")));
		setRsaEnabled(Boolean.parseBoolean(props.getProperty("rsa_enabled")));
		setRsaKeyFile(props.getProperty("rsa_key_file"));
		setLoginServerHost(props.getProperty("login_server_host"));
		setLoginServerPort(Integer.parseInt(props.getProperty("login_server_port")));
	}

	public static void setScriptDirectory(String scriptDirectory) {
		Configuration.scriptDirectory = scriptDirectory;
	}

	public static String getScriptDirectory() {
		return scriptDirectory;
	}

	public static void setServerHost(String serverHost) {
		Configuration.serverHost = serverHost;
	}

	public static String getServerHost() {
		return serverHost;
	}

	public static void setServerPort(int serverPort) {
		Configuration.serverPort = serverPort;
	}

	public static int getServerPort() {
		return serverPort;
	}

	public static void setTickrate(int tickrate) {
		Configuration.tickrate = tickrate;
	}

	public static int getTickrate() {
		return tickrate;
	}

	public static void setCodecFactory(CodecFactory codecFactory) {
		Configuration.codecFactory = codecFactory;
	}

	public static CodecFactory getCodecFactory() {
		return codecFactory;
	}

	public static void setBufferSize(int bufferSize) {
		Configuration.bufferSize = bufferSize;
	}

	public static int getBufferSize() {
		return bufferSize;
	}

	public static void setIsaacEnabled(boolean isaacEnabled) {
		Configuration.isaacEnabled = isaacEnabled;
	}

	public static boolean isIsaacEnabled() {
		return isaacEnabled;
	}

	public static void setRsaEnabled(boolean rsaEnabled) {
		Configuration.rsaEnabled = rsaEnabled;
	}

	public static boolean isRsaEnabled() {
		return rsaEnabled;
	}

	public static void setRsaKeyFile(String rsaKeyFile) {
		Configuration.rsaKeyFile = rsaKeyFile;
	}

	public static String getRsaKeyFile() {
		return rsaKeyFile;
	}

	public static void setLoginServerHost(String loginServerHost) {
		Configuration.loginServerHost = loginServerHost;
	}

	public static String getLoginServerHost() {
		return loginServerHost;
	}

	public static void setLoginServerPort(int loginServerPort) {
		Configuration.loginServerPort = loginServerPort;
	}

	public static int getLoginServerPort() {
		return loginServerPort;
	}

}
