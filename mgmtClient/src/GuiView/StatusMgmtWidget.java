package GuiView;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * The Class StatusMgmtWidget shows the server services status and controls them.
 * 
 * @author Guy Golan && Amit Sandak.
 */
public class StatusMgmtWidget extends ServerDisplayer {
	
	/** The Start stop button. */
	protected Button StartStopButton;
	
	/** The status label. */
	protected Label status;
	
	/**
	 * Instantiates a new status management widget.
	 *
	 * @param parent the parent composite
	 * @param param the SWT style parameters
	 * @param startStopListener the start/ stop listener
	 */
	StatusMgmtWidget(Composite parent, int param,SelectionListener startStopListener) {
		super(parent, param);
		setLayout(new GridLayout(2,true));
		StartStopButton=new Button(this, SWT.PUSH);
		StartStopButton.setText("                                  "); //initialize
		StartStopButton.setLayoutData(new GridData(SWT.RIGHT, SWT.None, false, false, 1, 1));
		StartStopButton.addSelectionListener(startStopListener);
		
		status = new Label(this, SWT.BORDER);
		status.setText("                                  "); //initialize
		status.setLayoutData(new GridData(SWT.LEFT, SWT.None, false, false, 1, 1));

		
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent arg0) {
				System.out.println("paint control " + serverStatus);
				if (serverStatus == true)  //checks the service status and update the matched GUI 
				{
				StartStopButton.setText("  Stop Service  ");
				
				status.setBackground(new Color(getParent().getDisplay(),0,204,0));
				status.setText(" Service online ");
				System.out.println("paintin");
			}else
			{
				StartStopButton.setText("  Start Service  ");
				
				status.setBackground(new Color(getDisplay(),255,0,0));
				status.setText(" Service offline ");
				System.out.println("paintin");
			}
				StartStopButton.setLayoutData(new GridData(SWT.RIGHT, SWT.None, false, false, 1, 1));
				status.setLayoutData(new GridData(SWT.LEFT, SWT.None, false, false, 1, 1));
			}
		});
		}

	/**
	 * Sets the start / stop listener.
	 *
	 * @param startStopListener the new start / stop listener
	 */
	public void setStartStopListener(SelectionListener startStopListener) {
		StartStopButton.addSelectionListener(startStopListener);
		
	}
}
