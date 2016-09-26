package main;

import java.io.*;

import java.net.*;

class Send implements Runnable{
	
	//get the inputStream
			private InputStream in;
			
			//get the outputStream 
			private OutputStream out;

			private Socket socket;
			
			private String filePath;
			
			public Send(String filePath, Socket socket){
				try{
					this.out = socket.getOutputStream();
					this.in = socket.getInputStream();
					this.filePath = filePath;
					this.socket = socket;
				}catch (IOException e ){
					e.printStackTrace();
				}
			}
	
	@Override
	public void run(){
		try{
			
			s.op("wait for \"start\" command");
			
			int temp = 0;
		
			StringWriter sw = new StringWriter();
			
			//get the command from 
			while((temp = in.read()) != 0){
				
				
				sw.write(temp);
			
			}
			
			String cmd = sw.toString();
			
			s.op("cmd from ClientA is : " + cmd);
		
			
			//if received command is "start", get ready to send file
			if("start".equals(cmd)){
								
				File file = new File(this.filePath);
				
				RandomAccessFile access = new RandomAccessFile(file,"r");
			
				//sw1 is the startIndex received from Client
				StringWriter sw1 = new StringWriter();
			
				
				while((temp = in.read()) != 0){
				
					sw1.write(temp);
				
					sw1.flush();
				}
			
				s.op("the sw1's content is : " + sw1.toString());
			
				//get the broken point
				int startIndex = 0;
			
				if(! sw1.toString().isEmpty()){
				
					startIndex = Integer.parseInt(sw1.toString());
				
				}
			
				int length = (int) file.length();
			
				byte[] filelength = String.valueOf(length).getBytes();
			
				out.write(filelength);
			
				out.write(0);
			
				out.flush();
			
				s.op("file length from other client is : " + length);
			
				//create a buffer and set the size as 10 KB
				byte[] buffer = new byte[1024 * 10];
			
				//get the rest length (the length to be sent)
				int rest = length;
			
				s.op("startIndex is : " + startIndex);
			
				access.skipBytes(startIndex);
				
				//if the rest is 0, that means it has finished the task
				while(true){
				
					if(rest == 0){
					
						break;
					
					}
					
					int leng = rest - startIndex;
				
					//if the read length is bigger than buffer length, set it to buffer length
					if(leng > buffer.length){
					
						leng = buffer.length;
					
					}
				
					int rlength = access.read(buffer, 0, leng);
				
					rest -= rlength;
					// if the read length is bigger than 0, that means this is effective read, send it to client
						if(rlength > 0){
					
							out.write(buffer, 0, rlength);
					
							out.flush();
					
						}else{
					
							break;
						}
						
				}
			
				s.op("Congratulations! The send file task has already finished !");
			
				out.close();
			
				in.close();
			
				access.close();
			
			}
			
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
