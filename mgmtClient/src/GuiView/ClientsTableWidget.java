package GuiView;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class ClientsTableWidget extends ServerDisplayer {
Table clientsList;
Button kickClient;

	ClientsTableWidget(Composite arg0, int arg1) {
		super(arg0, arg1);
		setLayout(new GridLayout(2, false));
		clientsList=new Table(this, SWT.BORDER|SWT.CHECK|SWT.V_SCROLL|SWT.H_SCROLL);
		clientsList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		clientsList.setHeaderVisible(true);
		clientsList.setBackground(new Color(getDisplay(), 255,255,255));
		
		String[] titles = { "IP Address" };

	    for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
	      TableColumn column = new TableColumn(clientsList, SWT.NULL);
	      column.setText(titles[loopIndex]);
	    }
		kickClient = new Button(this, SWT.PUSH);
		kickClient.setText("Kick Client");
		kickClient.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));

	    addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent arg0) {
				/////////////////////////
				
			}
		});
	}

}
