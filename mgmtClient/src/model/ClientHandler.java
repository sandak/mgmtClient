package model;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * The Interface ClientHandler.
 */
public interface ClientHandler {
	
	/**
	 * Handle client.
	 *
	 * @param inFromClient the in from client stream
	 * @param outToClient the out to client stream
	 */
	void handleClient(InputStream inFromClient, OutputStream outToClient);

	/**
	 * Sets the model.
	 *
	 * @param model the new model
	 */
	void setModel(Model model);




}
