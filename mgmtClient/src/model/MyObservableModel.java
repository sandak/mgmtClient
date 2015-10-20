package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
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
	
	/** The updates channel. */
	ServerSocket updatesChannel;
	
	/** The update thread. */
	Thread updateThread;
	
	/** The update stop flag. */
	volatile boolean updateStop;
	
	/** The thread pool. */
	ExecutorService threadPool;
	
	/** The server update handler. */
	ClientHandler updateHandler;
	
	/** The commands to the server channel socket. */
	Socket outChannelSocket;
	
	/** The out writer to the server in the commands channel. */
	PrintWriter outToServer;
	
	/** The reader from server in the commands channel. */
	BufferedReader inFromServer;

	/**
	 * Ctor. Tries to get old maps from cached maps , if failed initializing new
	 * maps.
	 *
	 * @param updateHandler the update handler
	 */
	public MyObservableModel(ClientHandler updateHandler) { // Ctor
		super();
		this.updateHandler = updateHandler;
		updateHandler.setModel(this);

	}

/* (non-Javadoc)
 * @see model.Model#start()
 */
@Override
	public boolean start()
	{
	try{
	
	outChannelSocket = new Socket(properties.getServerIP(), properties.getMgmtPort());
	if (properties.isDebug())
		System.out.println("connected to server!");
	outToServer = new PrintWriter(outChannelSocket.getOutputStream());
	inFromServer = new BufferedReader(new InputStreamReader(outChannelSocket.getInputStream()));
	
	startUpdateChannel();
	
	register();	//register in the server in order to get updates.
	return true;
	}catch (ConnectException e)
	{return false;}catch (IOException e) //if the connection fails eturn false
	{return false;}

	}
	
	/**
	 * opens the update channel and waiting to update connections.
	 */
	public void startUpdateChannel() {
		updateStop = false;
		try {
			updatesChannel = new ServerSocket(properties.getUpdatePort());
			updatesChannel.setSoTimeout(10000);
		} catch (IOException e1) {
			if(properties.isDebug())
				e1.printStackTrace();
		}
		threadPool = Executors.newFixedThreadPool(1);

		updateThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!updateStop) {
					try {
						final Socket someUpdate = updatesChannel.accept();
						if (someUpdate != null) {
							threadPool.execute(new Runnable() {
								@Override
								public void run() {
									try {
										if (properties.isDebugMode())
											System.out.println("server connecting in the update channel");
										InputStream in = someUpdate.getInputStream();
										OutputStream out = someUpdate.getOutputStream();
										updateHandler.handleClient(in, out);
										in.close();
										out.close();
										someUpdate.close();
									} catch (IOException e) {
										if (properties.isDebug())
											e.printStackTrace();
									}
								}
							});
						}
					} catch (SocketTimeoutException e) {
						if (properties.isDebug())
						System.out.println("no new updates...");
					} catch (IOException e) {
						if (properties.isDebug())
							e.printStackTrace();
					}
				}
				if(properties.isDebug())
					System.out.println("done accepting new updates.");
			} // end of the mainServerThread task
		});

		updateThread.start();

	}

	/* (non-Javadoc)
	 * @see model.Model#exit()
	 */
	@Override
	public void exit() {
		try{
			if(outChannelSocket!=null)
			{
				unregister(); //ask the server to stop the updates

			outToServer.println("exit"); //exit sign in the protocol
				outToServer.flush();

			}
				
			if(inFromServer!=null)// closing the resources
				inFromServer.close();
			if(outToServer!=null)
				outToServer.close();

			if(outChannelSocket!=null)
				if(outChannelSocket.isConnected())
					outChannelSocket.close();
			}catch(IOException e)
			{}
		if(updatesChannel!=null) //closing the update channel
		try {
			updateStop = true;
			// do not execute jobs in queue, continue to execute running threads
			if (properties.isDebug())
				System.out.println("shutting down");
			threadPool.shutdown();
			// wait 10 seconds over and over again until all running jobs have
			// finished
			boolean allTasksCompleted = false;
			while (!(allTasksCompleted = threadPool.awaitTermination(10, TimeUnit.SECONDS)));

			
				if(properties.isDebug())
					System.out.println("all the tasks have finished");

			updateThread.join();
			if(properties.isDebug())
				System.out.println("thread is done");

			updatesChannel.close();
			if (properties.isDebug())
				System.out.println(" session is safely closed");
		} catch (InterruptedException e) {
			if(properties.isDebug())
				e.printStackTrace();
		} catch (IOException e) {
			if(properties.isDebug())
				e.printStackTrace();
		}
	}


	/**
	 * Unregister request protocol.
	 */
	private void unregister() {
		try {
			outToServer.println("unregister");
			outToServer.flush();
			inFromServer.readLine();
		} catch (IOException e) {
			// do nothing
		}

	}

	/**
	 * Register request protocol.
	 */
	private void register() {
		try {
			outToServer.println("register");
			outToServer.flush();
			inFromServer.readLine();

		} catch (IOException e) {
			if(properties.isDebug())
				e.printStackTrace();
		}
	
	}

	/* (non-Javadoc)
	 * @see model.Model#startStopServer()
	 */
	@Override
	public void startStopServer() {
		try {
				String parse;
			outToServer.println("get status");
			outToServer.flush();

			parse = inFromServer.readLine();
			if (parse.contains("online")) //check the service status and requesting to start or stop it 
				outToServer.println("stop server");
			else
				outToServer.println("start server");
			outToServer.flush();
			parse = inFromServer.readLine();
				
		} catch (IOException e) {
			if(properties.isDebug())
				e.printStackTrace();
		}

	}


	/* (non-Javadoc)
	 * @see model.Model#getStatus()
	 */
	@Override
	public void getStatus() {
		try {
			String parse;

			outToServer.println("get status");
			outToServer.flush();
			parse = inFromServer.readLine();
			setChanged();
			if (parse.contains("online")) {
				notifyObservers("updateStatus online");
			} else
				notifyObservers("updateStatus offline");

			if(properties.isDebug())
				System.out.println("status updated request done");
			
		} catch (IOException e) {
if (properties.isDebug())	
	e.printStackTrace();}
	}

	/* (non-Javadoc)
	 * @see model.Model#kick(java.lang.String)
	 */
	@Override
	public void kick(String param) {
		try {
			
			outToServer.println("kick request");
			outToServer.flush();
			inFromServer.readLine();// ok
			outToServer.println("sending");
			outToServer.flush();
			inFromServer.readLine();// ready
			outToServer.println(param);
			outToServer.flush();
			inFromServer.readLine();// done
	

		} catch (IOException e) {
			if(properties.isDebug())
				e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see model.Model#updateLog(java.lang.String)
	 */
	@Override
	public void updateLog(String parse) {
		setChanged();
		notifyObservers("updateLog "+parse);
	}

	/* (non-Javadoc)
	 * @see model.Model#updateStatus(java.lang.String)
	 */
	@Override
	public void updateStatus(String stat) {
		setChanged();
		notifyObservers("updateStatus "+stat);
		
	}

	/* (non-Javadoc)
	 * @see model.Model#getData()
	 */
	@Override
	public void getData() {
		try {
			
			outToServer.println("get data");
			outToServer.flush();
			inFromServer.readLine();//ok


			

		} catch (IOException e) {
			if(properties.isDebug())
				e.printStackTrace();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see model.Model#shutDownProtocol()
	 */
	@Override
	public void shutDownProtocol() {
		try {
		outToServer.println("shutdown");
		outToServer.flush();
		inFromServer.readLine();//ok
		} catch (IOException e) {
			if(properties.isDebug())
				e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see model.Model#shutdownUpdate()
	 */
	@Override
	public void shutdownUpdate() {
		setChanged();
		notifyObservers("message server is shuting down!" ); 
		setChanged();
		notifyObservers("exit exit"); //starting exit sequence
		
	}

}
