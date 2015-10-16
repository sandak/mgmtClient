package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UpdateHandler implements ClientHandler {
	Model model;

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
				if (line.contains("serverShutdown"))
					serverShutdown(in, out);
				if (line.contains("log push"))
					logUpdate(in, out);
			}
			in.close();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void logUpdate(BufferedReader in, PrintWriter out) {
		try {
			out.println("ready");
			out.flush();
			String parse = in.readLine();
			out.println("done");
			out.flush();
			model.updateLog(parse);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void serverShutdown(BufferedReader in, PrintWriter out) {
		// TODO Auto-generated method stub

	}

	private void statusUpdate(BufferedReader in, PrintWriter out) {
		try {
			out.println("ready");
			out.flush();
			String parse = in.readLine();
			model.updateStatus(parse);
			out.println("done");
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setModel(Model model) {
		this.model = model;

	}

}
