package presenter;

import java.lang.invoke.CallSite;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;


import model.Model;
import view.View;

/**
 * The unit which observes the independent activity of the View and Model.
 *
 * @author Guy Golan && Amit Sandak.
 */
public class Presenter implements Observer {
	
	/** The model. */
	protected Model model;	
	
	/** The view. */
	protected View view;		
	
	/** The connection and program properties. */
	protected Properties properties;		
	
	/** The commands map . */
	protected HashMap<String, Command> commandMap;	
	
	/** exit process flag. */
	protected boolean closeProcess; //
	
	
	/**
	 * Instantiates a new presenter.
	 *
	 * @param model the model
	 * @param view the view
	 */
	public Presenter(Model model, View view) {
		super();
		this.setModel(model);
		this.setView(view);
		
		properties = new Properties();	//creating default properties.
		properties.setDefaults();
		model.setProperties(properties);	//informing the model of the system properties.
		
		this.commandMap = new HashMap<String , Command>();		//inserting all the Commands into the map
		commandMap.put("exit", new Exit(this));
		commandMap.put("message", new Message(this));
		commandMap.put("propertiesUpdate", new PropertiesUpdate(this));
		commandMap.put("switchUi", new SwitchUi(this));
		commandMap.put("changeServerStatus", new ChangeServerStatus(this));
		commandMap.put("updateStatus", new UpdateStatus(this));
		commandMap.put("getStatus", new GetStatus(this));
		commandMap.put("clientsUpdated", new ClientsUpdated(this));
		commandMap.put("kickRequest", new KickRequest(this));
		commandMap.put("updateLog", new UpdateLog(this));
		commandMap.put("getData", new GetData(this));
		commandMap.put("shutdownRequest", new ShutdownRequest(this));
		
	}

/**
 * Start.
 */
public void start()
{
	if(model.start())//checks if the model succeeded to establish a connection before starting the view.
		view.start();
	else{
		view.displayError("error, server unaviable!"); //if the connection failed close the program.
		model.exit();
	}
}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable comp, Object id) {
		String identifier = ((String)id); 
		Command c = commandMap.get(identifier.split(" ")[0]);
		System.out.println(identifier);
		if(c != null)
		{
			if(identifier.split(" ").length > 1)
			{
				c.doCommand(identifier.substring(identifier.indexOf(' ')+1));	//executing the command.
			}
			else if (!identifier.equals("exit"))
			{
				getView().displayError("Missing parameters.");
			}
			else
			{
				c.doCommand("");
			}
		}
		else
		{
			getView().displayError(identifier + " is not a valid command.");	
		}
		
	}
					//-------------GETTERS & SETTERS-----------------
/**
 * Gets the model.
 *
 * @return the model
 */
						
	public Model getModel() {
		return model;
	}

	/**
	 * Sets the model.
	 *
	 * @param model the new model
	 */
	public void setModel(Model model) {
		this.model = model;
	}

	/**
	 * Gets the view.
	 *
	 * @return the view
	 */
	public View getView() {
		return view;
	}

	/**
	 * Sets the view.
	 *
	 * @param view the new view
	 */
	public void setView(View view) {
		this.view = view;
	}

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * Sets the debug mode.
	 *
	 * @param b the new debug mode
	 */
	public void setDebugMode(boolean b) {
		properties.setDebug(b);
		
	}

	/**
	 * sets new properties and informing the view and model of the system properties.
	 * @param prop - properties.
	 */
	public void setProperties(Properties prop) {
		this.properties = prop;
		if (model != null)
			this.model.setProperties(prop);
		if (view!=null)
			this.view.setProperties(prop);
		
	}

	/**
	 * Close view.
	 */
	public void closeView() {
		view.exit();
		
	}
	
	/**
	 * Sets the close process flag.
	 *
	 * @param b the new close process flag
	 */
	public void setCloseProcess(boolean b) {
		closeProcess=b;
		
	}
	
	/**
	 * Checks the close process flag status.
	 *
	 * @return true, if is close process
	 */
	public boolean isCloseProcess() {
		return closeProcess;
	}
					
}
	


