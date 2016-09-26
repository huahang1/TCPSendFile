package main;

import java.io.*;
import java.net.*;

class Receive implements Runnable{
	
	private Socket socket;
	private String savedFilepath;
	public Receive(Socket socket, String savedFilePath){
		this.socket = socket;
		this.savedFilepath = savedFilePath;
		
	}
	
	
	
	@Override
	public void run(){
		try{
			
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			
			BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
			s.op("Please type in \"start\" to start the receive file task from other client :");
			String command = read.readLine();
			
			byte[] cmd = command.getBytes();
			out.write(cmd);
			out.write(0); //create a separate marker
						
			int startIndex = 0;
			
			//choose a file path to create a new file (to write data from the received data)
			File file = new File(savedFilepath);
			
			//if the file has already received the data, don't receive data from the very beginning, start from the last position
			if(file.exists()){
				
				startIndex = (int) file.length();
			}
			
			s.op("ClientA startIndex : " + startIndex);
			
			//create a RandomAccessFile file to read data from Client
			RandomAccessFile access = new RandomAccessFile(file,"rw");
			
			//send the startIndex to client, so another client can send data from startIndex instead of the very beginning
			out.write(String.valueOf(startIndex).getBytes());
			out.write(0);;
			out.flush();
			
			//get the total length of file from Client
			int temp = 0;
			
			StringWriter sw = new StringWriter();
			
			while((temp = in.read()) != 0){
				
				sw.write(temp);
				
				sw.flush();
			}
			
			int length = Integer.parseInt(sw.toString()); 
			
			s.op("Total file length is : " + length);
			
			//create a buffer array to receive file content
			byte[] buffer = new byte[1024 * 10];
			
			//read the rest of file , here we can regard startIndex as a broken point
			int rest = length - startIndex;
			
			//if file has already read some part of the the file, skip this part and read from the broken point(startIndex)
			access.skipBytes(startIndex);
			
			while(true){
				
				//if rest equals 0, that means we have already finished read data
				
				if(rest == 0){
					
					break;
				}
				
				int len = rest;
				
				//if the read length is bigger than buffer length, modify read length to buffer length
				if(len > buffer.length){
					
					len = buffer.length;
				}
				//read file and return the real read length
				int rlength = in.read(buffer, 0, len);
				
				rest -= rlength;
				
				if(rlength > 0){
					
					//write the data into file on clientA, otherwise it means has already finished reading all the file
					
					access.write(buffer, 0, rlength);
					
				}else{
					
					break;
					
				}
				s.op("The receiving file from ClientB task has already finished :" + ((float) (length - rest) / length ) * 100 + "%");
			}
			
			s.op("Congratulations! The receive file from ClientB task has already finished ! ");
			
			//close data stream
			access.close();
			out.close();
			in.close();
			
			
		}catch(Exception e ){
			e.printStackTrace();
		}
	}
	
}