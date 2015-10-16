package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;


import model.Model;
import view.View;

/**
 * The unit which observes the independent activity of the View and Model
 * @author Guy Golan && Amit Sandak.
 *
 */
public class Presenter implements Observer {
	private Model model;	//the model.
	private View view;		//the view.
	private Properties properties;		//system properties.
	
	private HashMap<String, Command> commandMap;	//the presenter available commands in a map.
	
	
	public Presenter(Model model, View view) {
		super();
		this.setModel(model);
		this.setView(view);
		
		properties = new Properties();	//creating default properties.
		properties.setDefaults();
		model.setProperties(properties);	//informing the model of the system properties.
		
		this.commandMap = new HashMap<String , Command>();		//inserting all the Commands into the map
		commandMap.put("display", new Display(this));
		commandMap.put("exit", new Exit(this));
		commandMap.put("completedTask", new CompletedTask(this));
		commandMap.put("propertiesUpdate", new PropertiesUpdate(this));
		commandMap.put("switchUi", new SwitchUi(this));
		commandMap.put("changeServerStatus", new ChangeServerStatus(this));
		commandMap.put("updateStatus", new UpdateStatus(this));
		commandMap.put("getStatus", new GetStatus(this));
		commandMap.put("clientsUpdated", new ClientsUpdated(this));
		commandMap.put("kickRequest", new KickRequest(this));
		commandMap.put("updateLog", new UpdateLog(this));
		commandMap.put("getData", new GetData(this));
		
	}

	@Override
	public void update(Observable comp, Object id) {
		String identifier = ((String)id); //TODO check type
		Command c = commandMap.get(identifier.split(" ")[0]);
		System.out.println(identifier);
		if(c != null)
		{
			if(identifier.split(" ").length > 1)
			{
				c.doCommand(identifier.substring(identifier.indexOf(' ')+1));			//executing the command.
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
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setDebugMode(boolean b) {
		properties.setDebug(b);
		
	}
	//--------------------------------------------------------------------------------
	/**
	 * informing the view and model of the system properties.
	 * @param prop - properties.
	 */
	public void setProperties(Properties prop) {
		this.properties = prop;
		if (model != null)
			this.model.setProperties(prop);
		if (view!=null)
			this.view.setProperties(prop);
		
	}

	public void closeView() {
		view.exit();
		
	}
					
}
	


