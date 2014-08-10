import java.io.*;
import java.net.*;

public class Typein extends Thread {
	Socket clientsocket;
	public Typein(Socket socket){
		clientsocket=socket;
	}
	
	PrintWriter out=null;
	BufferedReader stdIn=null;
	
	public void run(){
		while(true){
			try{
				out = new PrintWriter(clientsocket.getOutputStream(), true);
		        stdIn = new BufferedReader(new InputStreamReader(System.in));
		        String fromUser;
		        while ((fromUser = stdIn.readLine()) != null) {
		        	System.out.println("Client - " + fromUser);
			        out.println(fromUser);
			        }
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
		
}
