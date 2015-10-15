package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * Defines what MyObservableModel does.
 * 
 * @author Guy Golan & Amit Sandak.
 *
 */
public class MyObservableModel extends ObservableCommonModel {

	/**
	 * Ctor. Tries to get old maps from cached maps , if failed initializing new
	 * maps.
	 */
	public MyObservableModel() { // Ctor
		super();

	}

	@Override
	public void exit() { // TODO Auto-generated method stub

	}

	@Override
	public void startStopServer() {
		try {
			Socket theServer = new Socket(properties.getServerIP(), properties.getMgmtPort());
			if (properties.isDebug())
				System.out.println("connected to server!");

			PrintWriter outToServer = new PrintWriter(theServer.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
			String parse;
			outToServer.println("get status");
			outToServer.flush();

			parse = in.readLine();
			if (parse.contains("online"))
				outToServer.println("stop server");
			else
				outToServer.println("start server");
			outToServer.flush();
			parse = in.readLine();
			outToServer.println("get status");
			outToServer.flush();
			parse = in.readLine();
			setChanged();
			if (parse.contains("online"))
				notifyObservers("getStatus status");
			else
				notifyObservers("getStatus status");

			outToServer.println("exit");
			outToServer.flush();

			in.close();
			outToServer.close();

			theServer.close();

		} catch (IOException e) {
			// do nothing
		}

	}

	@Override
	public void terminateClient() {
		// TODO Auto-generated method stub

	}
	
@Override
	public void getStatus() {
		try {
			Socket theServer = new Socket(properties.getServerIP(), properties.getMgmtPort());
			if (properties.isDebug())
				System.out.println("connected to server!");

			PrintWriter outToServer = new PrintWriter(theServer.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
			String parse;

			outToServer.println("get status");
			outToServer.flush();
			parse = inFromServer.readLine();
			System.out.println(parse);
			setChanged();
			if (parse.contains("online"))
			{
				notifyObservers("updateStatus online");
			System.out.println("online");
			}
			else
				notifyObservers("updateStatus offline");

			outToServer.println("exit");
			outToServer.flush();

			inFromServer.close();
			outToServer.close();

			theServer.close();

		} catch (IOException e) {
			// do nothing
		}
	}

}
