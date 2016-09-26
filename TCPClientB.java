package main;

import java.io.*;
import java.net.*;
	
public class TCPClientB {
	
	public static void main(String[] args) throws Exception{
		
		
		s.op("please type in \"Send\" or \"Receive\": ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String command = br.readLine();
		
		
		if(command.equalsIgnoreCase("Send")){
			
			
			s.op("Please wait for other client's receive confirmation");
			
			ServerSocket server = new ServerSocket(7777);
			
			Socket socket = server.accept();
			
			SendRequest.sendFileInfo(socket);
			
			String fileName = GenerateInformation.receiveFileName(socket);
			
			s.op("In main, fileName received from client is " + fileName);
			
			String filePath = GenerateInformation.getFilePath(fileName);
			
			s.op("the filePath you need is: " + filePath);
			
			new Thread(new Send(filePath,socket)).start();
			
			
			}else{
				
				if(command.equalsIgnoreCase("Receive")){
				
					Socket socket2 = new Socket("192.168.2.2",7778);
					
					GenerateInformation.receiveFileInfo(socket2);
					
					s.op("type in filename you want to save as with format(.mp4 or .txt or .pdf): ");
					
					BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
					
					String savedFilePath = FileType.chooseFileName(br1.readLine());
					
					SendRequest.sendCommand(socket2);
					
					new Thread(new Receive(socket2,savedFilePath)).start();
				
				}else{
					s.op("you type in a wrong command, please type in \"Send\" or \"Receive\"");
				}
			}
		
		}
	
}
		


