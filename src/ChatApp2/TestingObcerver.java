package ChatApp2;

import java.util.Observer;
import java.util.Observable;
import java.io.IOException;
import java.net.Socket;

public class TestingObcerver implements Observer {
	static CallListenerThread callListenerThread;
	static Socket socket;
	static Connection con = new Connection();

	public void update(Observable obj, Object arg) {
		socket = callListenerThread.getSocket();
		try {
			con.setSocket(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Command com = con.receive();
			System.out.println(com.getCommand());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		callListenerThread = new CallListenerThread(4444);
		TestingObcerver to = new TestingObcerver();
		callListenerThread.addObserver(to);
		callListenerThread.CallListenerStart("lala");
			
	}

}
