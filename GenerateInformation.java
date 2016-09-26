package main;

import java.io.*;
import java.net.*;
import java.util.*;

public class GenerateInformation {
	
	public static void receiveFileInfo(Socket socket) throws Exception{
	
	Reader reader = new InputStreamReader(socket.getInputStream());
	
	char chars[] = new char[64];
	
	int len = 0;
	
	StringBuilder sb = new StringBuilder();
	
	String temp;
	
	int index;
	
	while((len = reader.read(chars)) != -1){
		
		temp = new String(chars, 0 , len);
		
		if((index = temp.indexOf("end")) != -1){
			sb.append(temp.substring(0, index));
			break;
		}
		sb.append(temp);	
	}
	
	s.op("another client has these files: " + sb);
}
	
	public static String getFilePath(String fileName) {
		// TODO Auto-generated method stub
		
		if(fileName.equals("movie")){
			return "/Users/hanghua/Desktop/Java学习/movie.mp4";
		}else{
			if(fileName.equals("rfc2616")){
				return "/Users/hanghua/Desktop/Java学习/rfc2616.txt";
			}else{
				if(fileName.equals("java_tutorial")){
					return "/Users/hanghua/Desktop/Java学习/java_tutorial.pdf";
				}else{
					return "the filePath doesn't exists";
				}
			}
		}
	}


	public static String receiveFileName(Socket socket) throws Exception {
		// TODO Auto-generated method stub
		
		Reader reader = new InputStreamReader(socket.getInputStream());
		
		int len = 0;
		
		char chars[] = new char[64];
		
		StringBuilder sb = new StringBuilder();
		
		String temp;
		
		int index;
		
		while((len = reader.read(chars)) != -1){
			temp = new String(chars,0 , len);
			if((index = temp.indexOf("end")) != -1){
				sb.append(temp.substring(0, index));
				break;
			}
			sb.append(temp);
		}
		
		String fileName = sb.toString();
		
		s.op("received fileName is :" + fileName);
		
		return fileName;
	
	}


	
}