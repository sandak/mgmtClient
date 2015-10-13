package model;

import java.util.Observable;
import presenter.Properties;

/**
 * represents what every Observable Model should implement or have.
 * @author Guy Golan && Amit Sandak.
 *
 */
public abstract class ObservableCommonModel extends Observable implements Model{
	
	protected Properties properties;						//system properties.
	
	
	/**
	 * Ctor
	 */
	public ObservableCommonModel() {				//Ctor
	
		properties = new Properties();
		properties.setDefaults();
		
	}

	
	public Properties getProperties() {
		return properties;
	}


	public void setProperties(Properties properties) {
		this.properties = properties;
		
	}

}


