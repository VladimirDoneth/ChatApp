import java.util.Observable;
import java.net.*;

public class CallListenerThread extends Observable implements Runnable {
    private Thread t = null;
    private boolean isWorkFlag = true , isSleepFlag = false;
    private CallListener callListener;
    private Socket socket;


    public CallListenerThread( int port){
        try {
            ServerSocket server = new ServerSocket(port);
            callListener = new CallListener(server);
        } catch (Exception e) {
            System.out.println("Не могу создать такой порт: " + port);
        }
    }

    public void CallListenerStart(String name){
        t = new Thread(this, name);
        t.start();
    }

    public void run() {
        while(isWorkFlag){
            synchronized (this) {
                // if (isSleepFlag){
                  /*  try{
                        t.wait();
                    } catch(InterruptedException e){}
              //  } else {*/
                socket = null;
                socket = callListener.getSocket();
                setChanged();
                notifyObservers();
            }
            }
        }


    public void stop(){
        synchronized (this) {
           // this.notify();
            isWorkFlag = false;
        }
    }

    private Socket getSocket(){
        Socket socket1 = socket;
        return socket1;
    }



}