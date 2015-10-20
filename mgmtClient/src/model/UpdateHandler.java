package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * The Class UpdateHandler handles the updates from the server.
 * 
 * @author Guy Golan & Amit Sandak
 */
public class UpdateHandler implements ClientHandler {
	
	/** The model. */
	Model model;

	/* (non-Javadoc)
	 * @see model.ClientHandler#handleClient(java.io.InputStream, java.io.OutputStream)
	 */
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(inFromClient));
			PrintWriter out = new PrintWriter(outToClient);
			String line;
			while (!(line = in.readLine()).endsWith("exit")) {

				if (line.contains("clients push"))
					clientsUpdate(in, out);
				if (line.contains("status push"))
					statusUpdate(in, out);
				if (line.contains("log push"))
					logUpdate(in, out);
				if (line.contains("shutdown push"))
					shutdownUpdate(in, out);
			}
		} catch (IOException e) {
			if(model.getProperties().isDebug())
				e.printStackTrace();
		}
	}

	/**
	 * Shutdown update protocol.
	 *
	 * @param in the in stream
	 * @param out the out stream
	 */
	private void shutdownUpdate(BufferedReader in, PrintWriter out) {
		out.println("ok");
		model.shutdownUpdate();
		
	}

	/**
	 * Log update protocol.
	 *
	 * @param in the in stream
	 * @param out the out stream
	 */
	private void logUpdate(BufferedReader in, PrintWriter out) {
		try {
			out.println("ready");
			out.flush();
			String parse = in.readLine();
			out.println("done");
			out.flush();
			model.updateLog(parse);
		} catch (IOException e) {
			if(model.getProperties().isDebug())
			e.printStackTrace();
		}

	}

	/**
	 * Status update protocol.
	 *
	 * @param in the in stream
	 * @param out the out stream
	 */
	private void statusUpdate(BufferedReader in, PrintWriter out) {
		try {
			out.println("ready");
			out.flush();
			String parse = in.readLine();
			model.updateStatus(parse);
			out.println("done");
			out.flush();
		} catch (IOException e) {
			if(model.getProperties().isDebug())
			e.printStackTrace();
		}

	}

	/**
	 * Clients update protocol.
	 *
	 * @param in the in stream
	 * @param out the out stream 
	 */
	private void clientsUpdate(BufferedReader in, PrintWriter out) {
		try {
			out.println("ready");
			out.flush();
			ArrayList<String[]> list = new ArrayList<String[]>();
			String line;
			String[] tmp;
			int i;
			while (!(line = in.readLine()).equals("list end")) {
				i = 0;
				tmp = new String[Integer.parseInt(line)];
				line = in.readLine();
				while (!(line.equals("client end"))) {
					tmp[i++] = (line);
					line = in.readLine();
				}
				list.add(tmp);
			}
			out.println("done");
			out.flush();
			model.updateClientsList(list);
		} catch (IOException e) {
			if(model.getProperties().isDebug())
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see model.ClientHandler#setModel(model.Model)
	 */
	@Override
	public void setModel(Model model) {
		this.model = model;

	}

}
