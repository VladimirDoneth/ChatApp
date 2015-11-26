package ChatApp2;
import java.io.IOException;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.net.*;

public class CommandListenerThread extends Observable implements Runnable {
    //Connection connection = new Connection();
    private Command lastCommand;
    private Connection connection;
    private boolean isExit = true;
    private boolean isSleep = true;
    private Thread thread;
    public final static  String itIsCommandLisnenerThread="lastCommand";

    public Socket getSocket(){
        return connection.getSocket();
    }

    public CommandListenerThread(String name, Connection connection){
        this.connection = connection;
        thread = new Thread(this,name);
    }

    /*public void setConnection(Connection connection){
        this.connection = connection;
    }
*/
    public Command getLastCommand(){
        return lastCommand;
    }

    public void CommandListenerThreadStart(){
        thread.start();
    }

    public void stop(){
        isExit = false;
    }

    @Override
    public void run() {
     while (isExit){
         synchronized (this) {
                 try {
                     lastCommand = connection.receive();
                     setChanged();
                     notifyObservers(itIsCommandLisnenerThread);
                 }catch (IOException e){

                 }
             }
         }
    }
}

