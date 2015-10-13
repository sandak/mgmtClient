package presenter;

import java.io.Serializable;

/**
 * Properties for the game system.
 * 
 * @author Guy Golan && Amit Sandak
 *
 */

public class Properties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected boolean debugMode; // on or off.
	protected String ui; // which ui to present.
	protected String serverIP;
	protected int mgmtPort;

	public Properties() {
		super();
	}

	/**
	 * Setting the defaults values for an empty properties.
	 */
	public void setDefaults() {
		this.debugMode = false;
		this.ui = "Graphic user interface";
		this.serverIP = "localhost";
		this.mgmtPort = 4040;
	}
									//--------------REGULAR SETTERS AND GETTERS------------------
	public boolean isDebug() {
		return debugMode;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}


	public void setUi(String ui) {
		this.ui = ui;
	}

	public void setDebug(boolean debug) {
		this.debugMode = debug;
	}


	public String getUi() {
		return ui;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public int getMgmtPort() {
		return mgmtPort;
	}

	public void setMgmtPort(int mgmtPort) {
		this.mgmtPort = mgmtPort;
	}

}
