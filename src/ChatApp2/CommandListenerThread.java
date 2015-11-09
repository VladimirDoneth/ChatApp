package ChatApp2;
import java.io.IOException;
import java.util.Observable;

public class CommandListenerThread extends Observable implements Runnable{

	private boolean isSleep, isExit = true;
	private Command lastCommand;
	private Connection connection;
	private Thread thread;
	
	public CommandListenerThread(String name, Connection connection){
		this.connection = connection;
		thread = new Thread(this, name);
	}
	
	public void CommandListenerThreadStart(){
		thread.start();
	}
	
	public void run() {
		while(isExit){
			synchronized (this){
				if (isSleep){
					try{
						this.wait();
					} catch(InterruptedException e){}
				} else {
					try{ 
					lastCommand = connection.receive();
					setChanged();
					notifyObservers(lastCommand);
					} catch(IOException e){
						
					}
				}
			}
		}
	}

	public Command getLastCommand(){
		return lastCommand;
	}
	
	//этот метод возможно будут нужен
	public void waitMethod(){
		isSleep = true;
	}
	
	//этот метод возможно будет нужен
	public void stop(){
		isExit = false;
	}
	
	//этот метод возможно будет нужен
	public void notifyMethod(){
		isSleep = false;
		synchronized(this){
			this.notify();
		}
		
	}
}
