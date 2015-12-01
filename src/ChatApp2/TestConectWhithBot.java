package ChatApp2;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class TestConectWhithBot implements Observer{
	static CommandListenerThread clt;
	public static void main(String args[]) {
		String remoteAdress = "https://files.litvinov.in.ua";
		Caller caller = new Caller();
		caller.setRemoteAdress(remoteAdress);
		try {
			caller.Call();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection connection = new Connection();
		try {
			connection.setSocket(caller.getSocket());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clt = new CommandListenerThread("lala", connection);
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		Command command = clt.getLastCommand();
		if (!command.equals(null)){
			System.out.println(command.getCommand());
		}
		
	}
}
