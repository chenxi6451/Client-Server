
import java.io.*;
import java.net.*;

public class Echoclient {
	public static InetAddress host=null;
	private static Socket socket = null;
	
	
    public static void main(String[] args) throws IOException {
    	
    	
    		

    	try {
    		InetAddress host = null;
    		host = InetAddress.getLocalHost();
    		socket = new Socket(host.getHostName(), 4119);

    		
    		}catch (Exception e) {
    			System.out.println("Something falied: " + e.getMessage());
    		    e.printStackTrace();
    		    }
    	Listenfor clientListen = new Listenfor(socket);
		clientListen.start();
		Typein clientTypeIn = new Typein(socket);
		clientTypeIn.start();
   }
   
} 

 

