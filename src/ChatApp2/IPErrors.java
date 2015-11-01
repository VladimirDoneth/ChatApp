import java.util.ArrayList;

/**
 * Created by anna on 08.11.15.
 */
public class IPErrors {
  private   ArrayList<String> ipErrors;
  public IPErrors(){
      ipErrors = new ArrayList<String>();
  }

    public boolean testOnIPErrors(String IP){
        for (int i=0;i<ipErrors.size();i++){
            if(IP.equals(ipErrors.get(i))) return false;
        }
        return  true;
    }

    public void addErrorIP(String IP){
        ipErrors.add(IP);
    }

    public  void deleteIPErrors(String  IP){
        for (int i=0;i<ipErrors.size();i++){
            if(IP.equals(ipErrors.get(i))) ipErrors.remove(i);
        }
    }
}
