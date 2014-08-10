import java.io.*;
import java.net.*;

public class Listenfor extends Thread{

	Socket clientsocket;
	
	public Listenfor(Socket socket){
		clientsocket=socket;
	}
	
	BufferedReader in=null;
	
	public void run(){
		while(true){
			
			try{
				
				in = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
				String fromServer;
				while ((fromServer = in.readLine()) != null) {
					System.out.println("Server - " + fromServer);
					if (fromServer.equals("exit"))
						break;
				
			}
		}catch (UnknownHostException e) {
			System.err.println("Cannot find the host: " + Echoclient.host.getHostName());
			System.exit(1);
		}
			catch(IOException e){
			e.printStackTrace();
		}
		}
	}

}

