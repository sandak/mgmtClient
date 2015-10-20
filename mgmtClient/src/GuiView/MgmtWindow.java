package GuiView;

import java.util.ArrayList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import presenter.Properties;

/**
 * This the main window in the GUI view.
 * 
 * @author Guy Golan && Amit Sandak.
 */
public class MgmtWindow extends BasicWindow {
	
	/** The service status. */
	boolean serviceStatus;
	
	/** The Start\ stop button. */
	Button StartStopButton;
	
	/** The shutdown button. */
	Button shutDown;
	
	/** The status label. */
	Label status;
	
	/** The status widget. */
	StatusMgmtWidget statusWidget;
	
	/** The connected clients widget. */
	ClientsTableWidget clientsWidget;
	
	/** The server log widget. */
	ServerLogWidget serverLog;
	
	/** the selection listener that sets the behavior of - exit request - in the MVP. */
	protected DisposeListener exitListener;

	/** the selection listener that sets the behavior of - update properties request - in the MVP. */
	protected SelectionListener propertiesUpdateListener;

	/** the selection listener that sets the behavior of - start / stop request - in the MVP. */
	protected SelectionListener startStopListener;

	/** The widgets list. */
	protected ArrayList<ServerDisplayer> widgetsList;

	/** The server properties. */
	protected Properties properties;
	
	/** The connected clients list. */
	protected ArrayList<String[]> clientsList;
	
	/** The kick button selection listener. */
	protected SelectionListener kickListener;
	
	/** The shutdown button selection listener. */
	protected SelectionListener shutdownListener;

	/**
	 * Instantiates a new maze window.
	 *
	 * @param title
	 *            the window title
	 * @param width
	 *            the window width
	 * @param height
	 *            the window height
	 * @param properties
	 *            the game properties
	 */
	public MgmtWindow(String title, int width, int height, Properties properties) {
		super(title, width, height);
		this.properties = properties;
		widgetsList = new ArrayList<ServerDisplayer>();
		shell.setImage(new Image(display, "resources/mgmticon.png"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see GuiView.BasicWindow#initWidgets()
	 */
	@Override
	void initWidgets() {
		shell.addDisposeListener(exitListener); // for X button and 'Exit'
												// button
		shell.setLayout(new GridLayout(2, true));
		Image image = new Image(display, "resources/mgmtbackground.jpg");
		shell.setBackgroundImage(image);
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
	
		shutDown = new Button(shell, SWT.PUSH);
		shutDown.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false, 2, 1));
		

			shutDown.setText("        Shutdown server         ");
			shutDown.addSelectionListener(shutdownListener);
		statusWidget = new StatusMgmtWidget(shell, SWT.NULL, startStopListener);
		statusWidget.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false, 2, 1));
		widgetsList.add(statusWidget);
		clientsWidget = new ClientsTableWidget(shell, SWT.NULL);
		clientsWidget.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 1, 1));
		widgetsList.add(clientsWidget);
		clientsWidget.setKickListener(kickListener);

		serverLog = new ServerLogWidget(shell, SWT.NULL);
		serverLog.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 1, 1));
		widgetsList.add(serverLog);

		shell.pack();
	}

	/**
	 * the Exit request behavior.
	 */
	protected void exitRequest() {
		shell.dispose();

	}

	/**
	 * Widgets refresh all the relevant data and redraw.
	 */
	public void widgetsRefresh() {
		for (ServerDisplayer widget : widgetsList) {
			widget.setServerStatus(this.serviceStatus);
			widget.setClientList(this.clientsList);
		}
	}

	/**
	 * Display error message.
	 *
	 * @param string the message text
	 */
	public void displayError(String string) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				MessageBox errorBox = new MessageBox(shell, SWT.ICON_ERROR);
				errorBox.setMessage(string);
				errorBox.setText("Error");
				errorBox.open();
			}
		});
	}

	/**
	 * Display a infometion message.
	 *
	 * @param string the text to display
	 */
	public void display(String string) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION);
				messageBox.setMessage(string);
				messageBox.setText("information message");
				messageBox.open();

			}
		});
	}

	/**
	 * Sets the selection listener that define the behavior of - start / stop
	 * request - in the MVP.
	 *
	 * @param startStopListener the new start stop listener
	 */
	public void setStartStopListener(SelectionListener startStopListener) {
		this.startStopListener = startStopListener;
		if (statusWidget != null)
			statusWidget.setStartStopListener(startStopListener);
	}

	/**
	 * Sets the selection listener that sets the behavior of - exit request - in
	 * the MVP.
	 *
	 * @param exitListener
	 *            the new exit listener
	 */
	public void setExitListener(DisposeListener exitListener) {
		this.exitListener = exitListener;
		if (shell != null)
			shell.addDisposeListener(exitListener);
	}

	/**
	 * Sets the selection listener that sets the behavior of - update properties
	 * request - in the MVP.
	 *
	 * @param selectionListener
	 *            the new properties update listener
	 */
	public void setPropertiesUpdateListener(SelectionListener selectionListener) {
		this.propertiesUpdateListener = selectionListener;

	}


	/**
	 * Sets the service status.
	 *
	 * @param b the new status
	 */
	public void setStatus(boolean b) {
		this.serviceStatus = b;
		widgetsRefresh();
	}

	/**
	 * Sets the connected clients list.
	 *
	 * @param clientsList the new connected clients list
	 */
	public void setClientsList(ArrayList<String[]> clientsList) {
		this.clientsList = clientsList;
		widgetsRefresh();

	}

	/**
	 * Sets the kick button selection listener.
	 *
	 * @param selectionListener the new kick button selection listener
	 */
	public void setKickListener(SelectionListener selectionListener) {
		this.kickListener = selectionListener;
		if (clientsWidget != null)
			clientsWidget.setKickListener(selectionListener);

	}

	/**
	 * Gets the list of clients to be kicked.
	 *
	 * @return the kick list
	 */
	public String[] getKickList() {
		return clientsWidget.getKicks();
	}

	/**
	 * Update log.
	 *
	 * @param param the log text
	 */
	public void updateLog(String param) {
		if(serverLog!=null)
		serverLog.appendText(param);

	}

	/**
	 * Sets the shutdown button selection listener.
	 *
	 * @param selectionListener the new shutdown listener
	 */
	public void setShutdownListener(SelectionListener selectionListener) {
		this.shutdownListener = selectionListener;
		if (shutDown!=null)
			shutDown.addSelectionListener(shutdownListener);
		
	}
}