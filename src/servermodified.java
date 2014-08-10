import java.net.*;
import java.io.*;
//import java.util.Collections;
import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.ArrayList;



public class servermodified extends Thread
{ 
	 protected Socket clientSocket;
	 static ArrayList<UserState> usrList = new ArrayList<UserState>();
	 static ArrayList<UserState> lastList=new ArrayList<UserState>();
	 static ArrayList<UserState> blackList=new ArrayList<UserState>();
	 static ArrayList<Socket> UsrList=new ArrayList<Socket>();
	 static SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
     private static final int LoginuserName = 0;
	 private static final int Loginpassword = 1;
	 private static final int Authenticateuser = 2;
	 private static final int Authsuccess = 3;
	 private static final int BroadCast = 4;
	 private int states = LoginuserName;
	 private int interval=0;
	 private String username = null;
	 private String userpassword = null;
	 private String UserCommand=null;
	 private String MsgBroadcast=null;

	 public static void main(String[] args) throws IOException 
	   { 
	    ServerSocket serverSocket = null; 

	    try { 
	         serverSocket = new ServerSocket(4119); 
	         System.out.println ("Server started");
	         try { 
	              while (true)
	                 {
	                  //System.out.println ("Waiting for Connection");
	                  new servermodified (serverSocket.accept()); 
	                 // PrintWriter _out=new PrintWriter(clientSocket.getOutputStream(), true);
	                  
	                 
	                 }
	             } 
	         catch (IOException e) 
	             { 
	              System.err.println("Failure,exit!"); 
	              System.exit(1); 
	             } 
	        } 
	    catch (IOException e) 
	        { 
	         System.err.println("Could not listen on port: 4119."); 
	         System.exit(1); 
	        } 
	    finally
	        {
	         try {
	              serverSocket.close(); 
	             }
	         catch (IOException e)
	             { 
	              System.err.println("Could not close port: 4119."); 
	              System.exit(1); 
	             } 
	        }
	   }

	 private servermodified (Socket clientSoc)
	   {
	    clientSocket = clientSoc;
	    start();
	    
	   }
     private PrintWriter out = null;
     private BufferedReader in = null;
     
   
     
	public void run()
	   {
		 System.out.println("Client connected to Server socket: " + clientSocket.toString());
		 try {
			 out = new PrintWriter(clientSocket.getOutputStream(), true);
		     in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             UserState CurrentState = null;
            // int flag=0;
		     //System.out.println("Client connected to Server socket: " + clientSocket.toString());
		     InetAddress ipAddress=clientSocket.getInetAddress();
			 int ClientPort=clientSocket.getPort();
			 //String clientPort=Integer.toString(ClientPort);
			 String ipaddress=ipAddress.toString();
             String inputLine;
		     String outputLine;
		     outputLine="Please Enter your user name: ";
             out.println(outputLine);
             
             states=Loginpassword;

		     while ((inputLine=in.readLine())!= null) {
		    	 if((inputLine!=null)&&(inputLine.equalsIgnoreCase("login"))){
		    		 states=Loginpassword;
		    	 }
		    	 if((inputLine!=null)&&(inputLine.equalsIgnoreCase("exit"))){
		    		 outputLine="exit";
		    	 }
		    	 if(states == LoginuserName) {
		    	     for(int k=0;k<blackList.size();k++){
		    	    	 if(ipaddress.equals(blackList.get(k).getusrIP())){
		    	    		 try {
		    	    			    
									sleep(6000);
									interval=0;
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		    	    		 states=LoginuserName;
		    	    	 }
		    	    	 else
		    	    		 break;
		    	     }
		    		 
					 outputLine = "Please Enter your user name: ";
				     states = Loginpassword;
				 } 
				 else if(states == Loginpassword) {
					 username = inputLine;
				     outputLine = "Please Enter your password: ";
				     states = Authenticateuser;
				 } 
				 else if(states == Authenticateuser) { 
					 userpassword = inputLine;
					 if((username.equals("Columbia") && userpassword.equals("116bway"))||(username.equals("SEAS") && userpassword.equals("summerisover"))
						 ||(username.equals("csee4119") && userpassword.equals("lotsofexams"))||(username.equals("foobar") && userpassword.equals("passpass"))
						 ||(username.equals("window") && userpassword.equals("withglass"))||(username.equals("Google") && userpassword.equals("hasglasses"))
						 ||(username.equals("facebook") && userpassword.equals("wastingtime"))||(username.equals("wikipedia") && userpassword.equals("donation"))
						 ||(username.equals("network") && userpassword.equals("seemsez"))) {
						 String date=sdf.format(new java.util.Date());
					     outputLine = "Welcome to Simple Server!, Press Enter to Continue. ";
					     		//+ "Client IP Address:"+clientSocket.getInetAddress()+", Client Port:"+clientSocket.getPort()+", Login Time:"+date;
				     
				         states = Authsuccess;
				         CurrentState=new UserState(username,ClientPort,ipaddress,date);
				         for(int m=0;m<usrList.size();m++){
				        	 if(usrList.get(m).getUsrName().equals(username))
				        		 usrList.remove(m);
				         }
				         usrList.add(CurrentState);
				         
				         UsrList.add(clientSocket);
				         for(int y=0;y<blackList.size();y++){
				        	 if(blackList.get(y).getUsrName().equals(username)){
				        		 blackList.remove(y);
				        		 }
				    	     else
				    		     break;
				        	 }
				     
	
				    } 
					 else {
						 outputLine = "Invalid Credentials!!! Please try again. Enter you user name";
				         states = Loginpassword;
				         interval=interval+1;
				         if(interval>=3){
				        	 outputLine="Wrong Username or Password for 3 times, try login after 60 seconds!";
				    	     //for block IP
				    	     String FailDate=sdf.format(new java.util.Date());
				    	     CurrentState=new UserState(username,ClientPort,ipaddress,FailDate);
				    	     blackList.add(CurrentState);
				    	     states=LoginuserName;
				    	 }
				         }
					 }
		    	 
				 else if(states==Authsuccess){
					// outputLine="Please Enter Command(whoelse, wholasthr or broadcast): ";
					 UserCommand=inputLine;
					 if(UserCommand.equals("whoelse")){
						 
						 for(int i=0;i<usrList.size();i++){
							 if(i==0){
								 outputLine=usrList.get(0).getUsrName();

							 }
							 if(i>=1){
								 outputLine=outputLine+" , "+usrList.get(i).getUsrName();
							 }
							
						 }
						 states=Authsuccess;
					 }
					 
					 else if(UserCommand.equals("wholasthr")){
						 
						 String CurrentTime=sdf.format(new java.util.Date());
						 Date currenttime=sdf.parse(CurrentTime);
						 outputLine=null;
						 //output all the online client
						 for(int i=0;i<usrList.size();i++){
							 if(outputLine==null){
								 outputLine=usrList.get(i).getUsrName();
							 }
							 else{
								 outputLine=outputLine+" , "+usrList.get(i).getUsrName();
							 }
						 }
						 for(int j=0;j<lastList.size();j++){
							 Date logouttime=sdf.parse(lastList.get(j).getUsrTime());
							 long diff=currenttime.getTime()-logouttime.getTime();
							 if(diff<=3600000){
								 
								 if(outputLine==null){
									 outputLine=lastList.get(j).getUsrName();
								 }
								 else{
									 outputLine=outputLine+" , "+lastList.get(j).getUsrName();
								 }
								 
							 }
						 }
							 
							 
						 
						 states=Authsuccess; 
					 }
					 else if(UserCommand.equals("broadcast")){
						 //sends a message to all connected clients
						// flag=0;
						 outputLine="Please Enter the message to broadcast: ";
						 states=BroadCast;
						 
						 
					//	 broadcast(MsgBroadcast);
					 }
					 else if(UserCommand.equals("exit")){
						 outputLine="exit";
					 }
					//else
						 //outputLine="Invalid Request!!!";
					 
				 }
				 else if(states==BroadCast){
					 outputLine="Please Enter the message to broadcast: ";
					 MsgBroadcast=inputLine;
					 for(int i=0;i<UsrList.size();i++){
						 PrintWriter p = new PrintWriter(UsrList.get(i).getOutputStream(), true);
						 p.println(MsgBroadcast); 						 
					 }
					 //flag=1;
					 states=Authsuccess;
						 
				 }
		    	 
				 else {
					 outputLine = "Invalid Request!!!";
					 states=Authsuccess;
				 }
		    	 if((outputLine!=null)){
		    		
		    		out.println(outputLine);
		    		out.flush();
		    		if((states==Authsuccess)&&(outputLine!="exit")){
		    			out.println("Please Enter Command(whoelse, wholasthr, broadcast or exit):");
		    		}
		    		
		    		if(outputLine.equals("exit")){
		    			
		    			states=LoginuserName;
		    			String datelogout=sdf.format(new java.util.Date());
		    			UserState LogoutState=new UserState(username,ClientPort,ipaddress,datelogout);
		    			lastList.add(LogoutState);
		    		    usrList.remove(CurrentState);
		    			
		    		
		    			System.out.println("Server is closing the socket for client:");
		                break;
		    				
		    			}
		    			
		    		}
		    	 }
		    	  
		     
	    }catch (IOException e) {
			 e.printStackTrace();
		 } catch (ParseException e) {
			e.printStackTrace();
		} finally { //In case anything goes wrong we need to close our I/O streams and sockets.
		 try {
			 out.close();
		     in.close();
		     clientSocket.close();
		 } catch(Exception e) {
			 System.out.println("Couldn't close I/O streams");
			 }
		 }
	   }
}


