package ChatApp2;

import java.util.Observer;
import java.util.Observable;
import java.io.IOException;
import java.net.Socket;

public class TestObserver2 implements Observer {
	static CommandListenerThread clt;
	static CallListenerThread callListenerThread;

	public static void main(String[] args) {
		callListenerThread = new CallListenerThread(4444);
	    TestObcerverPlusObserver topo = new TestObcerverPlusObserver();
	    callListenerThread.addObserver(topo);
	    callListenerThread.CallListenerStart("lala");
	}

	public void update(Observable o, Object arg) {
		Command com = clt.getLastCommand();
		if (com == null) System.out.println("null");
		else System.out.println(com.getCommand());
	}
	
	


}


