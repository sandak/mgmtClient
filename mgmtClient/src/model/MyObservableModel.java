package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * Defines what MyObservableModel does.
 * 
 * @author Guy Golan & Amit Sandak.
 *
 */
public class MyObservableModel extends ObservableCommonModel {
	ServerSocket updatesChannel;
	Thread updateThread;
	volatile boolean updateStop;
	ExecutorService threadPool;
	ClientHandler updateHandler;

	/**
	 * Ctor. Tries to get old maps from cached maps , if failed initializing new
	 * maps.
	 */
	public MyObservableModel(ClientHandler updateHandler) { // Ctor
		super();
		this.updateHandler = updateHandler;
		updateHandler.setModel(this);
		startUpdateChannel();
		register();

	}

	public void startUpdateChannel() {
		updateStop = false;
		try {
			updatesChannel = new ServerSocket(properties.getUpdatePort());
			updatesChannel.setSoTimeout(properties.getTimeOut());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		threadPool = Executors.newFixedThreadPool(1);

		updateThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!updateStop) {
					try {
						final Socket someClient = updatesChannel.accept();
						if (someClient != null) {
							threadPool.execute(new Runnable() {
								@Override
								public void run() {
									try {
										if (properties.isDebugMode())
											System.out.println("server connecting in the update channel");
										InputStream in = someClient.getInputStream();
										OutputStream out = someClient.getOutputStream();
										updateHandler.handleClient(in, out);
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							});
						}
					} catch (SocketTimeoutException e) {
						System.out.println("no updates...");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println("done accepting new updates.");
			} // end of the mainServerThread task
		});

		updateThread.start();

	}

	@Override
	public void exit() {
		unregister();

		try {
			updateStop = true;
			// do not execute jobs in queue, continue to execute running threads
			System.out.println("shutting down");
			threadPool.shutdown();
			// wait 10 seconds over and over again until all running jobs have
			// finished
			boolean allTasksCompleted = false;

			while (!(allTasksCompleted = threadPool.awaitTermination(10, TimeUnit.SECONDS)))

				System.out.println("all the tasks have finished");

			updateThread.join();
			System.out.println("thread is done");

			updatesChannel.close();
			System.out.println(" session is safely closed");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void unregister() {
		try {
			Socket theServer = new Socket(properties.getServerIP(), properties.getMgmtPort());
			if (properties.isDebug())
				System.out.println("connected to server!");

			PrintWriter outToServer = new PrintWriter(theServer.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
			outToServer.println("unregister");
			outToServer.flush();
			in.readLine();
			outToServer.println("exit");
			outToServer.flush();

			in.close();
			outToServer.close();

			theServer.close();

		} catch (IOException e) {
			// do nothing
		}

	}

	private void register() {
		try {
			Socket theServer = new Socket(properties.getServerIP(), properties.getMgmtPort());
			if (properties.isDebug())
				System.out.println("connected to server!");

			PrintWriter outToServer = new PrintWriter(theServer.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
			outToServer.println("register");
			outToServer.flush();
			in.readLine();
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
			if (parse.contains("online")) {
				notifyObservers("updateStatus online");
				System.out.println("online");
			} else
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

	@Override
	public void kick(String param) {
		try {
			Socket theServer = new Socket(properties.getServerIP(), properties.getMgmtPort());
			if (properties.isDebug())
				System.out.println("connected to server!");

			PrintWriter outToServer = new PrintWriter(theServer.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(theServer.getInputStream()));

			outToServer.println("kick request");
			outToServer.flush();
			inFromServer.readLine();// ok
			outToServer.println("sending");
			outToServer.flush();
			inFromServer.readLine();// ready
			outToServer.println(param);
			outToServer.flush();
			inFromServer.readLine();// done
			outToServer.println("exit");
			outToServer.flush();

			inFromServer.close();
			outToServer.close();

			theServer.close();

		} catch (IOException e) {
			// do nothing
		}

	}

	@Override
	public void updateLog(String parse) {
		setChanged();
		notifyObservers("updateLog "+parse);
	}

	@Override
	public void updateStatus(String stat) {
		setChanged();
		notifyObservers("updateStatus "+stat);
		
	}

	@Override
	public void getData() {
		try {
			Socket theServer = new Socket(properties.getServerIP(), properties.getMgmtPort());
			if (properties.isDebug())
				System.out.println("connected to server!");

			PrintWriter outToServer = new PrintWriter(theServer.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(theServer.getInputStream()));

			outToServer.println("get data");
			outToServer.flush();
			inFromServer.readLine();//ok

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
