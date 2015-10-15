package GuiView;

import java.util.ArrayList;

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
import org.eclipse.swt.widgets.TableItem;

public class ClientsTableWidget extends ServerDisplayer {
Table clientsTable;
Button kickClient;
ArrayList<TableItem> items;

	ClientsTableWidget(Composite arg0, int arg1) {
		super(arg0, arg1);
		items = new ArrayList<TableItem>();
		setLayout(new GridLayout(2, false));
		clientsTable=new Table(this, SWT.BORDER|SWT.CHECK|SWT.V_SCROLL|SWT.H_SCROLL);
		clientsTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		clientsTable.setSize(500, 500);
		clientsTable.setHeaderVisible(true);
		clientsTable.setBackground(new Color(getDisplay(), 255,255,255));
		
		String[] titles = { "ID", "Ip address" ,"Host name" };

	    for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
	      TableColumn column = new TableColumn(clientsTable, SWT.NULL);
	      column.setWidth(80);
	      column.setText(titles[loopIndex]);
	    }
		kickClient = new Button(this, SWT.PUSH);
		kickClient.setText("Kick Client");
		kickClient.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));

	    addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent arg0) {
				if(items!=null)
					for (TableItem item : items) {
						item.dispose();
					}
				if(clientsList!= null)
				{
				for (String[]  strings: clientsList) {
				TableItem t = new TableItem(clientsTable,  SWT.NONE);
				t.setText(strings);
				t.setBackground(getBackground());
				items.add(t);
				}
				}
				
				
			}
		});
	}

}
