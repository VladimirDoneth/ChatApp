package ChatApp2;
import java.util.*;

public class Contacts {
   private ArrayList<String> nickNameArray;
   private ArrayList<String> IPArray;
   
   public Contacts(){
	   nickNameArray = new ArrayList<String>();
	   IPArray = new ArrayList<String>();
   }
   
   public void addContact(String nick, String IP){
	   nickNameArray.add(nick);
	   IPArray.add(IP);
   }
   
   public String getNick(int index){
	  return nickNameArray.get(index);
   }
   
   public String getIP(int index){
	  return IPArray.get(index); 
   }
   
   public int getSize(){
	   return nickNameArray.size();
   }
}
