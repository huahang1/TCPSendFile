package main;

import java.io.*;
import java.net.*;

public class SendRequest {
	
	public static void sendCommand(Socket socket) throws Exception{
		// TODO Auto-generated method stub
		Writer writer = new OutputStreamWriter(socket.getOutputStream());
		
		s.op("please type in the file name you want to receive (without format!) : ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String command = br.readLine();
		
		writer.write(command);
		writer.write("end");
		writer.flush();
			
	}

	
	public static void sendFileInfo(Socket socket) throws Exception{
		
		String fileInfo = "\"movie.mp4\", \"rfc2616.txt\" , \"java_tutorial.pdf\"";
	
		Writer writer = new OutputStreamWriter(socket.getOutputStream());
	
		writer.write(fileInfo);
		
		writer.write("end");
	
		writer.flush();
	
	}


	

}

