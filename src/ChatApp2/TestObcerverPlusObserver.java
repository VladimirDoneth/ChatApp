package ChatApp2;

import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class TestObcerverPlusObserver implements Observer {
	TestObserver2 to2 = new TestObserver2();
	@Override
	public void update(Observable o, Object arg) {
		Socket socket = to2.callListenerThread.getSocket();
		Connection con = new Connection();
		try {
			System.out.println("we are in tyt");
			con.setSocket(socket);
			System.out.println("we are in tyt 2");
		} catch (IOException e) {
			System.out.println("we are in tyt 3");
			e.printStackTrace();
		}
        to2.clt = new CommandListenerThread("lala", con);
        to2.clt.addObserver(to2);
        to2.clt.CommandListenerThreadStart();
        
	}
}