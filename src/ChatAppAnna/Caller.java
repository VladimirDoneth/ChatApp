import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
/**
 * Created by anna on 25.10.15.
 */
public class Caller{
    private Socket socket;
    private String localNick;
    private String remoteNick;
    private String remoteAdress;
    private static final int remotePort =28411;
    //lastError;?????

public  Caller(){
    socket =null;
}

    public void call()throws  Exception{
        socket = new Socket(remoteAdress,remotePort);
    }

    public void setLocalNick(String localNick){
        this.localNick = localNick;
    }
    public void setRemoteNick(String remoteNick){
        this.remoteNick=remoteNick;
    }
    public  void setRemoteAdress(String remoteAdress){
        this.remoteAdress = remoteAdress;
    }
    public Socket getSocket(){
        return socket;
    }
}
    /*
    Connection connection = new Connection();
     public void Connect(String ipAddress,int serverPort){
         try {
             Socket socket = new Socket(ipAddress, serverPort);

         }catch (Exception e){
              connection.sendNickBusy(ipAddress, serverPort);
         }
     }

}
