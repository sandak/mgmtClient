package model;

import java.util.ArrayList;
import java.util.Observable;
import presenter.Properties;

// TODO: Auto-generated Javadoc
/**
 * represents what every Observable Model should implement or have.
 * @author Guy Golan && Amit Sandak.
 *
 */
public abstract class ObservableCommonModel extends Observable implements Model{
	
	/** The properties. */
	protected Properties properties;	//system properties.
	
	/** The clients list. */
	protected ArrayList<String[]> clientsList;
	
	/**
	 * Ctor.
	 */
	public ObservableCommonModel() {				//Ctor
	
		properties = new Properties();
		properties.setDefaults();
		
	}

	
	/* (non-Javadoc)
	 * @see model.Model#getProperties()
	 */
	public Properties getProperties() {
		return properties;
	}


	/* (non-Javadoc)
	 * @see model.Model#setProperties(presenter.Properties)
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
		
	}
	
	/* (non-Javadoc)
	 * @see model.Model#updateClientsList(java.util.ArrayList)
	 */
	@Override
	public void updateClientsList(ArrayList<String[]> list)
	{
		this.clientsList = list;
		setChanged();
		notifyObservers("clientsUpdated clients");
	}
	
	/* (non-Javadoc)
	 * @see model.Model#getClientsList()
	 */
	@Override
	public ArrayList<String[]> getClientsList() {
		return clientsList;
	}
}


