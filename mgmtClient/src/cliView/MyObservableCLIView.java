package cliView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import presenter.Properties;

/**
 * This class represents the command line user interface in the MVP.
 *
 * @author Guy Golan && Amit Sandak.
 */
public class MyObservableCLIView extends ObservableCommonCLIView {

	boolean exit; // used as a flag

	/**
	 * Instantiates a new my observable cli view.
	 *
	 * @param in
	 *            the BufferedReader
	 * @param out
	 *            the PrintWriter
	 */
	public MyObservableCLIView(BufferedReader in, PrintWriter out) {
		super(in, out);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#start()
	 */
	@Override
	public void start() {
		exit = false;

		new Thread(new Runnable() { // the user interface runs in an independent
									// thread.

			@Override
			public void run() {

				try {
					String buffer;

					while (!exit) {
						out.print("Enter Command >> ");
						out.flush();
						buffer = in.readLine();
						setChanged();
						notifyObservers(buffer);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#display(java.lang.String[])
	 */
	@Override
	public void display(String[] strings) {
		if (strings.length > 0) {
			for (String string : strings)
				out.println(string);

			out.flush();
		} else {
			display("Empty Directory.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#exit()
	 */
	@Override
	public void exit() {
		exit = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#exitRequest()
	 */
	@Override
	public void exitRequest() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#display(java.lang.String)
	 */
	@Override
	public void display(String string) {
		out.println(string);
		out.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#displayError(java.lang.String)
	 */
	@Override
	public void displayError(String string) {
		out.println("Error: " + string);
		out.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#setProperties(presenter.Properties)
	 */
	@Override
	public void setProperties(Properties prop) {
		if (!prop.getUi().equals("Command line")) {
			setChanged();
			notifyObservers("switchUi switch");
		}

	}

	@Override
	public void setStatus(boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setClientList(ArrayList<String[]> clientsList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLog(String param) {
		System.out.println("log: " + param);
		
	}

}
