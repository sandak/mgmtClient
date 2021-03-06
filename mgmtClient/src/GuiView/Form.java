package GuiView;

import java.lang.reflect.Field;
import java.util.HashMap;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

/**
 * A generic Form. this class getting an object and creating a
 *
 * @author Guy Golan && Amit Sandak.
 */
public class Form extends BasicWindow{

	Object created;
	//Shell shell;
	@SuppressWarnings("rawtypes")
	Class format;
	/**
	 * Instantiates a new form.
	 *
	 * @param parent the parent shell
	 * @param classObject the class object
	 * @param title the title of the shell
	 * @param width the width of the shell
	 * @param height the height of the shell
	 */
	@SuppressWarnings("rawtypes")
	public Form( Class format, String title) {
		super(title,500,500);
		shell.setLayout(new GridLayout(2,false));
		this.format = format;
		
	}
	
	@Override
	public void initWidgets(){
		Label titel = new Label(shell,SWT.CENTER);
		titel.setText(format.getName());
		titel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		
		Field[] fields = format.getDeclaredFields();
		HashMap<String, Widget> hm = new HashMap<String, Widget>();
		for (Field field : fields) {
			switch(field.getType().getName())
			{
			case "boolean":
				new Label(shell,SWT.TOP).setText(field.getName());
				hm.put(field.getName(), new Button(shell, SWT.CHECK));
				break;
				default:
				new Label(shell,SWT.TOP).setText(field.getName());
		hm.put(field.getName(), new Text(shell, SWT.BORDER));	
			}
		}
		
		
		
		Button saveButton=new Button(shell, SWT.PUSH);
		saveButton.setText("  Save  ");
		saveButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		saveButton.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						try {
							created=format.newInstance();
							for (Field field : fields) {
								
								try{
								 boolean tmp = ((Button)hm.get(field.getName())).getSelection();
									field.setAccessible(true);
									field.set(created,tmp);
								}catch(ClassCastException e) //catch if the cast to button failed then cast to Text
								{
								String tmp =((Text)hm.get(field.getName())).getText();
								String type = field.getType().getSimpleName();
								field.setAccessible(true);
								switch (type)
								{
								
								case "int":
									
									field.set(created,Integer.parseInt(tmp));
									break;
								case "String":
								case "char[]":
									field.set(created,tmp);
									break;
								} 
								}
					
								
							}
							shell.dispose();
						}
						catch (IllegalArgumentException | IllegalAccessException e) {
								// nothing
								e.printStackTrace();
						}
						catch (InstantiationException | SecurityException e) {
							//nothing
							e.printStackTrace();
						}	
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						//nothing
						
					}
				});	
		shell.pack();
	}

	
	/**
	 * Gets the new object that created by the form.
	 *
	 * @return the object
	 */
	public Object getObject() {
		return created;
		
	}


}
