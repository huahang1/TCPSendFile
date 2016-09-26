package main;

public class FileType {
	
public static String chooseFileName(String string) {
		
	String d = "/Users/hanghua/Desktop/";

	String format = string.substring(string.length()-4, string.length());
	
	String name = string.substring(0,string.length() - 4);
	
	String receivedFilePath = d + name + format;
	
	return receivedFilePath;
		
	}
}
