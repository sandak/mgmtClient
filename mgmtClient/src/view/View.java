package view;

import presenter.Presenter;
import presenter.Properties;

/**
 * The Interface of View in the MVP.
 * 
 * @author Guy Golan && Amit Sandak.
 */
public interface View {

/**
 * Display an array of strings
 *
 * @param strings the strings array
 */
	public void display(String[] strings);
	
	/**
	 * Display a string.
	 *
	 * @param string the string to display
	 */
	public void display(String string);
	

	
	/**
	 * Exit from view.
	 */
	public void exit() ;
	
	/**
	 * Start the view.
	 */
	public void start();
	
	/**
	 * Exit request. the notify exit protocol in the MVP.
	 * this request will be processed in the presenter.
	 */
	void exitRequest();
	
	
	
	/**
	 * Display error.
	 *
	 * @param message the error message
	 */
	public void displayError(String message);
	
	
	/**
	 * Sets the properties.
	 *
	 * @param prop the new properties
	 */
	public void setProperties(Properties prop);

	public void setStatus(boolean b);
	
}
