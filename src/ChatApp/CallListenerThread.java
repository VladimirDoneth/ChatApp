package ChatApp;

import java.net.*;

public class CallListenerThread implements Runnable{
	private Thread t = null;
	private boolean WorkFlag = true , sleepFlag = false;
	private CallListener callListener = null;
	
	
    public CallListenerThread(String name, int port){
    	t = new Thread(this, name);
		t.start();
		try {
			ServerSocket server = new ServerSocket(port);
			callListener = new CallListener(server);
		} catch (Exception e) {
			System.out.println("Не могу создать такой порт " + port);
		}
    }
	
	public void run() {
		while(WorkFlag){
			synchronized (this) {
				if (sleepFlag){
					try{ t.wait(); } catch(Exception e){}
				} else {
					 
				}
			}
		}
	}
	
	

}
