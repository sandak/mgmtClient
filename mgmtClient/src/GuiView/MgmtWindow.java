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
import org.eclipse.swt.widgets.Widget;


import presenter.Properties;

/**
 * This the main window in the GUI view.
 * 
 *  @author Guy Golan && Amit Sandak.
 */
public class MgmtWindow extends BasicWindow{
	boolean serverStatus;
	Button StartStopButton;
	Label status;
	StatusMgmtWidget statusWidget;
	/** The selected XML properties file. */
	protected String selectedXMLpropertiesFile;
	
	/** the selection listener that sets the behavior of - exit request - in the MVP */
	protected DisposeListener exitListener;
	
	
	/** the selection listener that sets the behavior of - update properties request - in the MVP */
	protected SelectionListener propertiesUpdateListener;

	/** the selection listener that sets the behavior of - start / stop request - in the MVP */
	protected SelectionListener startStopListener;
	
	/** The widgets list. */
	protected ArrayList<ServerDisplayer> widgetsList;
	
	/** The server properties. */
	protected Properties properties;
	
	/**
	 * Instantiates a new maze window.
	 *
	 * @param title the window title
	 * @param width the window width
	 * @param height the window height
	 * @param properties the game properties
	 */
	public MgmtWindow( String title, int width, int height , Properties properties) {
		super(title, width, height);
		this.properties= properties;
		selectedXMLpropertiesFile = null;
		widgetsList = new ArrayList<ServerDisplayer>();
		shell.setImage(new Image(display, "resources/mgmticon.png"));
	}



	/* (non-Javadoc)
	 * @see GuiView.BasicWindow#initWidgets()
	 */
	@Override
	void initWidgets() {
		shell.addDisposeListener(exitListener);			//for X button and 'Exit' button
		shell.setLayout(new GridLayout(1,false));	
		Image image= new Image(display,"resources/mgmtbackground.jpg");
		shell.setBackgroundImage(image);
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		//shell.setCursor(new Cursor(shell.getDisplay(), new ImageData("resources/Cursor_Greylight.png").scaledTo(27, 25), 16, 0));
	System.out.println("1");
		statusWidget = new StatusMgmtWidget(shell, SWT.NULL, startStopListener);
		statusWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		widgetsList.add(statusWidget);
	  
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
	public void widgetsRefresh()
	{
		for (ServerDisplayer widget : widgetsList) {
			widget.setServerStatus(this.serverStatus);
				
			
		}
	}
	
	
	
	/**
	 * Display error.
	 *
	 * @param string the message
	 */
	public void displayError(String string) {
		Display.getDefault().syncExec(new Runnable() {
			
			@Override
			public void run() {
				MessageBox errorBox =  new MessageBox(shell, SWT.ICON_ERROR); 
				errorBox.setMessage(string);
				errorBox.setText("Error");
				errorBox.open();				
			}
		});
	}
	
	/**
	 * Display a string.
	 *
	 * @param string the string to display
	 */
	public void display(String string) {
		Display.getDefault().syncExec(new Runnable() {
			
			@Override
		    public void run() {
		    	MessageBox messageBox =  new MessageBox(shell, SWT.ICON_INFORMATION); 
		    	messageBox.setMessage(string);
		    	messageBox.setText("information message");
		    	messageBox.open();		
		    	
		    }
		});
	}
	

	
	/**
	 * Sets the selection listener that sets the behavior of - start / stop request - in the MVP.
	 *
	 * @param selectionListener the new solve listener
	 */
	public void setStartStopListener(SelectionListener startStopListener) {
		this.startStopListener = startStopListener;
		if (statusWidget!=null)
		statusWidget.setStartStopListener(startStopListener);
	}
	
	/**
	 * Sets the selection listener that sets the behavior of - exit request - in the MVP.
	 *
	 * @param exitListener the new exit listener
	 */
	public void setExitListener(DisposeListener exitListener) {
		this.exitListener = exitListener;
	}


	/**
	 * Sets the selection listener that sets the behavior of - update properties request - in the MVP.
	 *
	 * @param selectionListener the new properties update listener
	 */
	public void setPropertiesUpdateListener(SelectionListener selectionListener) {
		this.propertiesUpdateListener = selectionListener;
		
	}



	/**
	 * Gets the selected XML properties file.
	 *
	 * @return the selected XML properties file
	 */
	public String getSelectedXMLpropertiesFile() {
		return selectedXMLpropertiesFile;
		
	}



	public void setStatus(boolean b) {
		this.serverStatus = b;
		widgetsRefresh();
		
	}


}