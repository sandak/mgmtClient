package GuiView;



import java.util.ArrayList;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;


/**
 * The abstract Class ServerDisplayer represent a general widget that interact with the server data.
 * 
 * @author Guy Golan && Amit Sandak.
 */
public abstract class ServerDisplayer extends Canvas {
		
		/** The server status. */
		protected boolean serverStatus;
		
		/** The connected clients list. */
		protected ArrayList<String[]> clientsList;
		
	/**
	 * Instantiates a new server displayer.
	 *
	 * @param parent the parent composite
	 * @param param the SWT style parameters
	 */
	ServerDisplayer(Composite parent,int param) {
		super(parent, param);
		serverStatus =false; //default value
	}


	/**
	 * Checks the game service status.
	 *
	 * @return true if the game service is on
	 */
	public boolean isServerStatus() {
		return serverStatus;
	}

	/**
	 * Sets the service status.
	 *
	 * @param serverStatus the new service status
	 */
	public void setServerStatus(boolean serverStatus) {
		this.serverStatus = serverStatus;
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {								//redraws the Widget.
		    	redraw();
		    }
		});
	}


	/**
	 * Sets the connected client list.
	 *
	 * @param clientsList the new connected client list
	 */
	public void setClientList(ArrayList<String[]> clientsList) {
		this.clientsList = clientsList;
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {								//redraws the Widget.
		    	redraw();
		    }
		});
		
	}

}
