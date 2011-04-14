package net.openrs.service;

/**
 * Defines an executable service. Runs within a {@link ServiceEngine} and is
 * executed upon every cycle of that engine.
 * 
 * @author Blake Beaupain
 */
public interface Service {

	/**
	 * Executes the service.
	 * 
	 * @throws Exception
	 *             If an Exception occurs
	 */
	public void execute() throws Exception;

}
