package main;

import java.io.*;
import java.net.*;


public class TCPClientA {

	public static void main(String[] args) throws Exception{
		
		//choose function as send or receive
		s.op("please type in \"Send\" or \"Receive\": ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String command = br.readLine();
		
		if(command.equalsIgnoreCase("Send")){
			
			//if send, please wait for another client to open this socket
			s.op("Please wait for other client's receive confirmation");
			
			ServerSocket server = new ServerSocket(7778);
			
			Socket socket1 = server.accept();
			
			//tell the client what files do this client have
			SendRequest.sendFileInfo(socket1);
			
			//get the fileName another client wants to download
			String fileName = GenerateInformation.receiveFileName(socket1);
			
			s.op("In main, fileName received from client is " + fileName);
			
			//get the filePath of the file to be downLoaded
			String filePath = GenerateInformation.getFilePath(fileName);
			
			s.op("the filePath you need is: " + filePath);
			
			//start the send thread to send
			new Thread(new Send(filePath,socket1)).start();
			
		}else{
			
			if(command.equalsIgnoreCase("Receive")){
				
				//connect to another client
				Socket socket = new Socket("192.168.2.2",7777);
				
				//get the file information from another client
				GenerateInformation.receiveFileInfo(socket);
				
				s.op("choose what kind of file do you want to receive(.mp4 or .txt or .pdf): ");
				
				//type in the file name and format you want to use, the default path is on the desktop
				BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
				
				String savedFilePath = FileType.chooseFileName(br1.readLine());
				
				//sent the "start" command to start receiving file
				SendRequest.sendCommand(socket);
				
				new Thread(new Receive(socket,savedFilePath)).start();
				
			}else{
				s.op("you type in a wrong command, please type in \"Send\" or \"Receive\"");
			}
		}
			
	}
	
}


class s{
	public static void op(Object obj){
		System.out.println(obj);
	}
}
