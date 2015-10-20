package model;


import java.util.ArrayList;

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
	
	/**
	 * request the server to Start or stop the service.
	 */
	void startStopServer();

	/**
	 * the protocol to request from  server the service status .
	 *
	 * @return the service status
	 */
	void getStatus();

	/**
	 * Update the connected clients list in the view.
	 *
	 * @param list the new connected clients list
	 */
	void updateClientsList(ArrayList<String[]> list);

	/**
	 * request from the server the connected clients list.
	 *
	 * @return the connected clients list
	 */
	ArrayList<String[]> getClientsList();

	/**
	 * request the server to Kick a client.
	 *
	 * @param param the client to kick
	 */
	void kick(String param);

	/**
	 * Update the log in the view.
	 *
	 * @param parse the parse
	 */
	void updateLog(String parse);

	/**
	 * Update status in the view.
	 *
	 * @param stat the new status
	 */
	void updateStatus(String stat);

	/**
	 * request from the server to send an update with his current data .
	 */
	void getData();

	/**
	 * Start the model and opens the connections.
	 *
	 * @return true, if successful
	 */
	boolean start();

	/**
	 * server shutdown request protocol .
	 */
	void shutDownProtocol();

	/**
	 * update all the components that the server is shutdown and start a exit sequence.
	 */
	void shutdownUpdate();
}