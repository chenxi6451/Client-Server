
public class UserState {
	private String usrname=new String();
	private int usrPortNumber;
	private String ipAddress=new String();
	private String TimeState = new String();
	//private int State;
	
	public String getUsrName()
	{
		return usrname;
	}
	
	public void setUsrName(String name)
	{
		usrname = name;
	} 
	public int getUserPortNumber(){
		return usrPortNumber;
	}
	public void setUsrTime(String time)
	{
		
		TimeState = time;

	}
	//public void setUsrState(int state){
	//	State=state;
	//}
	public String getusrIP()
	{
		return ipAddress;
	}
	//UsrState
	public String getUsrTime()
	{
		return TimeState;
	}
	
	
	UserState(String username,int portNumber,String IpAddress,String timestate) {
		//setUsrPortNumber(portNumber);
		//setUsrName(name);
		//setUsrState(state);
		//gameSN = -1;
		this.usrname= username;
		this.usrPortNumber=portNumber;
		this.ipAddress=IpAddress;
		this.TimeState=timestate;
		//this.State=states;
	}
}

	


