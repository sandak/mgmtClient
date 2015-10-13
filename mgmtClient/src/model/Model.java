package model;


import presenter.Properties;

/**
 * Defines what every Model can do.
 * @author Guy Golan & Amit Sandak
 *
 */
public interface Model {


	/**
	 * Safely closing all resources.
	 */
	void exit();

	/**
	 * setting the model properties (usually through a Presenter).
	 * @param properties - properties.
	 */
	void setProperties(Properties properties);
	
	/**
	 * Regular get for the properties.
	 * @return Properties properties.
	 */
	Properties getProperties();
	
	void startStopServer();
	void terminateClient();

	void getStatus();
}