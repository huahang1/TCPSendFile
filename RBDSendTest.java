package main;

import java.io.*;
import java.net.*;

public class RBDSendTest {
		  
	    
	    class Sender extends Thread{  
	         
	        private InputStream in;  
	        
	        private OutputStream out;  
	        
	        private String filename;  
	  
	        public Sender(String filename, Socket socket){  
	            try {  
	                this.out = socket.getOutputStream();  
	                this.in = socket.getInputStream();  
	                this.filename = filename;  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	          
	        @Override  
	        public void run() {  
	            try {  
	                System.out.println("wait for command");  
	                int temp = 0;  
	                StringWriter sw = new StringWriter();  
	                while((temp = in.read()) != 0){  
	                    sw.write(temp);  
	                    //sw.flush();  
	                }  
	                
	                String cmds = sw.toString();  
	                
	                System.out.println("received cmd is : " + cmds);  
	                
	                if("get".equals(cmds)){  
	                     
	                    File file = new File(this.filename);  
	                    RandomAccessFile access = new RandomAccessFile(file,"r");  
	                     
	                    StringWriter sw1 = new StringWriter();  
	                    while((temp = in.read()) != 0){  
	                        sw1.write(temp);  
	                        sw1.flush();  
	                    }  
	                    System.out.println(sw1.toString());  
	                      
	                    int startIndex = 0;  
	                    if(!sw1.toString().isEmpty()){  
	                        startIndex = Integer.parseInt(sw1.toString());  
	                    }  
	                    long length = file.length();  
	                    byte[] filelength = String.valueOf(length).getBytes();  
	                    out.write(filelength);  
	                    out.write(0);  
	                    out.flush();  
	                    
	                    System.out.println("file length : " + length);  
	                    
	                    byte[] buffer = new byte[1024*10];  
	                      
	                    int tatol = (int) length;  
	                    System.out.println("startIndex : " + startIndex);  
	                    access.skipBytes(startIndex);  
	                    while (true) {  
	                        
	                        if(tatol == 0){  
	                            break;  
	                        }  
	                         
	                        int len = tatol - startIndex;  
	                         
	                        if(len > buffer.length){  
	                            
	                            len = buffer.length;  
	                        }  
	                         
	                        int rlength = access.read(buffer,0,len);  
	                         
	                        tatol -= rlength;  
	                         
	                        if(rlength > 0){  
	                             
	                            out.write(buffer,0,rlength);  
	                            out.flush();  
	                        } else {  
	                            break;  
	                        }  
	                         
	                    }  
	                    
	                    
	                    out.close();  
	                    in.close();  
	                    access.close();  
	                }  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	            super.run();  
	        }  
	    }  
	      
	    public void run(String filename, Socket socket){  
	        
	        new Sender(filename,socket).start();  
	    }  
	      
	    public static void main(String[] args) throws Exception {  
	         
	        ServerSocket server = new ServerSocket(6666);  
	       
	        String filename = "/Users/hanghua/Desktop/Java学习/movie.mp4";  
	        for(;;){  
	        	
	            Socket socket = server.accept();
	            
	            new RBDSendTest().run(filename, socket);  
	        }  
	    }  
	  
	}  