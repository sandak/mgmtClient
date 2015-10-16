package GuiView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import presenter.Properties;

/**
 * This class represents the GUI view in the MVP.
 *
 * @author Guy Golan && Amit Sandak.
 */
public class MyObservableGuiView extends ObservableCommonGuiView {

	/** The main window of the view. */
	protected MgmtWindow mainWindow;
	
	
	/**
	 * Instantiates a new observable GUI view.
	 *
	 * @param title the window title
	 * @param width the window width
	 * @param height the window height
	 */
	public MyObservableGuiView(String title, int width, int height) {
		super(new Properties());
		properties.setDefaults();
		mainWindow = new MgmtWindow(title, width, height , properties);
		

	
	////////////////////////  the selection listener that sets the behavior of - properties update request - in this specific MVP  ////////////
			mainWindow.setPropertiesUpdateListener(new SelectionListener() {
	
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					
					setChanged();
					notifyObservers("propertiesUpdate "+mainWindow.getSelectedXMLpropertiesFile());
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// nothing
		
		
				}
			});

		
////////////////////////  the selection listener that sets the behavior of - exit request - in this specific MVP  ////////////
		mainWindow.setExitListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				exitRequest();
				
			}
		});

	
////////////////////////the selection listener that sets the behavior of - start / stop request - in this specific MVP  ////////////

		mainWindow.setStartStopListener(new SelectionListener() {
	
	@Override
	public void widgetSelected(SelectionEvent arg0) {
		System.out.println("click");
		setChanged();
		notifyObservers("changeServerStatus .");
		
	}
	

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
});


	
////////////////////////the selection listener that sets the behavior of - kick client reqest - in this specific MVP  ////////////

mainWindow.setKickListener(new SelectionListener() {

@Override
public void widgetSelected(SelectionEvent arg0) {
setChanged();
String s = "kickRequest ";
String [] strings = mainWindow.getKickList();
for (String string : strings) {
	s=s+string+":";
}
System.out.println(s);
notifyObservers(s);

}

@Override
public void widgetDefaultSelected(SelectionEvent arg0) {
	
	
}
});
	}



	/* (non-Javadoc)
	 * @see view.View#display(java.lang.String[])
	 */
	@Override
	public void display(String[] strings) {
		// nothing

	}

	/* (non-Javadoc)
	 * @see view.View#display(java.lang.String)
	 */
	@Override
	public void display(String string) {
		mainWindow.display(string);

	}

	/* (non-Javadoc)
	 * @see view.View#exit()
	 */
	@Override
	public void exit() {
		mainWindow.exit();

	}

	/* (non-Javadoc)
	 * @see view.View#start()
	 */
	@Override
	public void start() {
		Timer t =new Timer();
		TimerTask tt = new TimerTask() {
			
			@Override
			public void run() {
				setChanged();
		notifyObservers("getData data");
		this.cancel();
			}
		};
		t.scheduleAtFixedRate(tt, 500 , 1000*60);
		
		mainWindow.run();

	}

	/* (non-Javadoc)
	 * @see view.View#exitRequest()
	 */
	@Override
	public void exitRequest() {
		setChanged();
		notifyObservers("exit");
		
	}

	
	/* (non-Javadoc)
	 * @see view.View#displayError(java.lang.String)
	 */
	@Override
	public void displayError(String string) {
		mainWindow.displayError(string);
		
	}

	

	

	/* (non-Javadoc)
	 * @see view.View#setProperties(presenter.Properties)
	 */
	@Override
	public void setProperties(presenter.Properties prop) {
		if(properties.getUi()!=null)
		if (!properties.getUi().equals(prop.getUi()))
		{
			setChanged();
			notifyObservers("switchUi switch");
		}
		else
			this.properties = prop;
		
		
	}


	@Override
	public void setStatus(boolean b) {
		mainWindow.setStatus(b);
		
	}


	@Override
	public void setClientList(ArrayList<String[]> clientsList) {
		mainWindow.setClientsList(clientsList);
		
	}



	@Override
	public void updateLog(String param) {
		mainWindow.updateLog(param);
		
	}

}
