package GuiView;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class StatusMgmtWidget extends ServerDisplayer {
	protected Button StartStopButton;
	protected Label status;
	
	StatusMgmtWidget(Composite arg0, int arg1,SelectionListener startStopListener) {
		super(arg0, arg1);
		setLayout(new GridLayout(2,true));
		StartStopButton=new Button(this, SWT.PUSH);
		StartStopButton.setText("                                  ");
		StartStopButton.setLayoutData(new GridData(SWT.RIGHT, SWT.None, false, false, 1, 1));
		StartStopButton.addSelectionListener(startStopListener);
		
		status = new Label(this, SWT.BORDER);
		status.setText("                                  ");
		status.setLayoutData(new GridData(SWT.LEFT, SWT.None, false, false, 1, 1));

		
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent arg0) {
				System.out.println("paint control " + serverStatus);
				if (serverStatus == true)
				{
				StartStopButton.setText("  Stop Server  ");
				
				status.setBackground(new Color(getParent().getDisplay(),0,204,0));
				status.setText(" Server online ");
				System.out.println("paintin");
			}else
			{
				StartStopButton.setText("  Start Server  ");
				
				status.setBackground(new Color(getDisplay(),255,0,0));
				status.setText(" Server offline ");
				System.out.println("paintin");
			}
				StartStopButton.setLayoutData(new GridData(SWT.RIGHT, SWT.None, false, false, 1, 1));
				status.setLayoutData(new GridData(SWT.LEFT, SWT.None, false, false, 1, 1));
			}
		});
		}

	public void setStartStopListener(SelectionListener startStopListener) {
		StartStopButton.addSelectionListener(startStopListener);
		
	}
}
