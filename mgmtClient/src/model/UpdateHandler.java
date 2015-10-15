package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;



public class UpdateHandler implements ClientHandler{
Model model;

	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
		try{
			BufferedReader in=new BufferedReader(new InputStreamReader(inFromClient));
			PrintWriter out=new PrintWriter(outToClient);
			String line;
			while(!(line=in.readLine()).endsWith("exit")){
				
					if (line.contains("clients push") )
						clientsUpdate(in,out);
					if (line.contains("status push") )
						statusUpdate(in,out);
					if (line.contains("serverShutdown") )
						serverShutdown(in,out);
				}	
			in.close();
			out.close();			
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void serverShutdown(BufferedReader in, PrintWriter out) {
		// TODO Auto-generated method stub
		
	}

	private void statusUpdate(BufferedReader in, PrintWriter out) {
		// TODO Auto-generated method stub
		
	}

	private void clientsUpdate(BufferedReader in, PrintWriter out) {
		try {
			out.println("ready");
		out.flush();
		ArrayList<String[]> list = new ArrayList<String[]> ();
		String line;
		String[] tmp;
		int i;
		while (!(line=in.readLine()).equals("list end"))
		{
			i= 0;
			tmp = new String [Integer.parseInt(line)];
			while (!(line.equals("client end")))
					tmp[i++]=(line=in.readLine());
			list.add(tmp);	
		} 
		out.println("done");
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
