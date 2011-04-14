package net.openrs.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An executor for <code>Service</code> instances. The behavior of this class is
 * cyclic - it performs one cycle every <code>tickrate</code> milliseconds,
 * taking into account the amount of time it spent performing the last cycle.
 * For each cycle, every registered <code>Service</code>'s
 * {@link Service#execute()} method is called once. If the
 * <code>execute()</code> method of any <code>Service</code> throws an
 * <code>Exception</code> for any reason, that <code>Service</code> is
 * automatically suspended and will not perform further execution.
 * 
 * @author Blake Beaupain
 */
public class ServiceEngine extends Thread {

	/** The singleton instance. */
	private static ServiceEngine instance;

	/** The tickrate, in milliseconds. */
	private int tickrate;

	/** The services . */
	private List<Service> services = new ArrayList<Service>();

	/**
	 * Creates a new <code>ServiceEngine</code>.
	 * 
	 * @param tickrate
	 *            The initial tickrate (in milliseconds).
	 */
	public ServiceEngine(int tickrate) {
		this.tickrate = tickrate;
	}

	@Override
	public void run() {
		Iterator<Service> it;
		while (true) {
			long timeBefore = System.currentTimeMillis();
			synchronized (this) {
				for (it = services.iterator(); it.hasNext();) {
					executeService(it);
				}
			}
			long timeSpent = System.currentTimeMillis() - timeBefore;
			handleSleep(timeSpent);
		}
	}

	/**
	 * Registers a <code>Service</code> for execution. If the engine is
	 * currently performing a cycle, this method will block until the cycle is
	 * complete.
	 * 
	 * @param service
	 *            The service to register
	 */
	public synchronized void register(Service service) {
		services.add(service);
	}

	/**
	 * Unregisters a <code>Service</code> from execution. If the engine is
	 * currently performing a cycle, this method will block until the cycle is
	 * complete.
	 * 
	 * @param service
	 */
	public synchronized void unregister(Service service) {
		services.remove(service);
	}

	/**
	 * Executes the <code>next()</code> service.
	 * 
	 * @param it
	 *            The iterator
	 */
	private void executeService(Iterator<Service> it) {
		try {
			it.next().execute();
		} catch (Exception ex) {
			it.remove();
		}
	}

	/**
	 * Sleeps accurately, taking into account the amount of time that was spent
	 * performing the last cycle. Sleeps for <code>tickrate - timeSpent</code>
	 * milliseconds.
	 * 
	 * @param timeSpent
	 *            The time spent, in milliseconds.
	 */
	private void handleSleep(long timeSpent) {
		long sleepAmount = tickrate - timeSpent;
		if (sleepAmount < 0) {
			return;
		}
		try {
			Thread.sleep(sleepAmount);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Sets the engine tickrate.
	 * 
	 * @param tickrate
	 *            The tickrate, in milliseconds
	 */
	public void setTickrate(int tickrate) {
		this.tickrate = tickrate;
	}

	/**
	 * Gets the engine tickrate.
	 * 
	 * @return The tickrate, in milliseconds
	 */
	public int getTickrate() {
		return tickrate;
	}

	/**
	 * Sets the singleton instance.
	 * 
	 * @param instance
	 *            The instance
	 * @throws IllegalStateException
	 *             If the instance has already been set
	 */
	public static void setInstance(ServiceEngine instance) {
		if (getInstance() != null) {
			throw new IllegalStateException("Singleton already initialized!");
		}
		ServiceEngine.instance = instance;
	}

	/**
	 * Gets the singleton instance.
	 * 
	 * @return The singleton instance
	 */
	public static ServiceEngine getInstance() {
		return instance;
	}

}
