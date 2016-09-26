package main;

import java.io.*;
import java.net.*;

public class RBDReceiveTest { 
		  
	    public void Get(String filepath) throws Exception {  
	    	
	        Socket socket = new Socket();
	        
	        socket.connect(new InetSocketAddress("192.168.1.48", 6666));  
	         
	        OutputStream out = socket.getOutputStream();  
	        InputStream in = socket.getInputStream();  
	        
	        s.op("type in your commmand: ");
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        String message = br.readLine();
	        byte[] cmd = message.getBytes();  
	        out.write(cmd);  
	        out.write(0); 
	        
	        int startIndex = 0;  
	         
	        File file = new File(filepath);  
	        if(file.exists()){  
	            startIndex = (int) file.length();  
	        }  
	        System.out.println("Client startIndex : " + startIndex);  
	         
	        RandomAccessFile access = new RandomAccessFile(file,"rw");  
	         
	        out.write(String.valueOf(startIndex).getBytes());  
	        out.write(0);  
	        out.flush();  
	        
	        int temp = 0;  
	        StringWriter sw = new StringWriter();  
	        while((temp = in.read()) != 0){  
	            sw.write(temp);  
	            sw.flush();  
	        }  
	        int length = Integer.parseInt(sw.toString());  
	        System.out.println("Client fileLength : " + length);  
	        
	        byte[] buffer = new byte[1024*10];  
	        
	        int tatol = length - startIndex;  
	        //  
	        access.skipBytes(startIndex);  
	        while (true) {  
	             
	            if (tatol == 0) {  
	                break;  
	            }  
	            
	            int len = tatol;  
	             
	            if (len > buffer.length) {  
	                
	                len = buffer.length;  
	            }  
	            
	            int rlength = in.read(buffer, 0, len);  
	            
	            tatol -= rlength;  
	             
	            if (rlength > 0) {  
	                 
	                access.write(buffer, 0, rlength);  
	            } else {  
	                break;  
	            }  
	            System.out.println("finish : " + ((float)(length -tatol) / length) *100 + " %");  
	        }  
	        System.out.println("finished!");  
	       
	        access.close();  
	        out.close();  
	        in.close();  
	    }  
	  
	    public static void main(String[] args) {  
	        RBDReceiveTest client = new RBDReceiveTest();  
	        try {  
	            client.Get("/Users/hanghua/Desktop/Received from Test4.mp4");  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	}  



