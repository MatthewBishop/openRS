package net.openrs.util;

import java.io.File;
import java.io.FileReader;

import org.python.util.PythonInterpreter;

public class ScriptInterpreter {

	private static final ScriptInterpreter instance = new ScriptInterpreter();

	private PythonInterpreter py = new PythonInterpreter();

	public void reset() {
		py.cleanup();
	}

	public void loadScripts(File dir) throws Exception {
		for (File child : dir.listFiles()) {

			// Recurse if the child is a directory.
			if (child.isDirectory()) {
				loadScripts(child);
				continue;
			}

			// Parse the Python script.
			if (child.getName().endsWith(".py")) {
				py.eval(py.compile(new FileReader(child)));
			}
		}
	}

	public static ScriptInterpreter getInstance() {
		return instance;
	}

}
