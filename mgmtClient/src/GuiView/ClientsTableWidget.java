package GuiView;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * The Class ClientsTableWidget
 * is a GUI widget that represent the clients connected to a server.
 * 
 * @author Guy Golan && Amit Sandak.
 */
public class ClientsTableWidget extends ServerDisplayer {

/** The clients table. */
Table clientsTable;

/** The kick client button. */
Button kickClient;

/** The table items. */
ArrayList<TableItem> items;

/** The kick button selection listener. */
SelectionListener kickListener;

/** The clients that chosen to be kicked. */
String [] kicks;

	/**
	 * Instantiates a new clients table widget.
	 *
	 * @param parent the parent composite.
	 * @param param the SWT style parameters.
	 */
	ClientsTableWidget(Composite parent, int param) {
		super(parent, param);
		items = new ArrayList<TableItem>();
		setLayout(new GridLayout(2, false));
		clientsTable=new Table(this, SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL);
		clientsTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		clientsTable.setSize(500, 500);
		clientsTable.setItemCount(10);
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
		kickClient.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				TableItem [] items = clientsTable.getSelection();
				 kicks=new String[items.length];
				int i=0;
				for (TableItem item : items) {
					kicks[i++] = item.getText(0);
				}
				
				kickListener.widgetSelected(arg0);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				//nothing
				
			}
		});
		
	    addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent arg0) {
			
				
				
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see GuiView.ServerDisplayer#setClientList(java.util.ArrayList)
	 */
	@Override
	public void setClientList(ArrayList<String[]> list)
		{
			this.clientsList=list;
			Display.getDefault().syncExec(new Runnable() {
				
				@Override
				public void run() {
					if(items!=null){
						for (TableItem item : items) {
							item.dispose();
						}
						clientsTable.setItemCount(0);
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
	
	/**
	 * Gets the connected clients table.
	 *
	 * @return the connected clients table
	 */
	public Table getClientsTable() {
		return clientsTable;
	}
	
	/**
	 * Sets the connected clients table.
	 *
	 * @param clientsTable the new connected clients table
	 */
	public void setClientsTable(Table clientsTable) {
		this.clientsTable = clientsTable;
	}
	
	/**
	 * Gets the kick button selection listener.
	 *
	 * @return the kick button selection listener
	 */
	public SelectionListener getKickListener() {
		return kickListener;
	}
	
	/**
	 * Sets the kick button selection listener.
	 *
	 * @param kickListener the new kick button selection listener
	 */
	public void setKickListener(SelectionListener kickListener) {
		this.kickListener = kickListener;
	}
	
	/**
	 * Gets the list of clients to be kicked.
	 *
	 * @return the list of clients to be kicked
	 */
	public String[] getKicks() {
		return kicks;
	}
	
	/**
	 * Sets the list of clients to be kicked.
	 *
	 * @param kicks the new list of clients to be kicked
	 */
	public void setKicks(String[] kicks) {
		this.kicks = kicks;
	}
}
