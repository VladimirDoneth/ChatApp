package ChatApp2;
import java.util.*;

public class Contacts {
   private ArrayList<String> nickNameArray;
   private ArrayList<String> IPArray;
   
   public Contacts(){
	   nickNameArray = new ArrayList<String>();
	   IPArray = new ArrayList<String>();
   }
   
   public boolean isHaveItNick(String nick){
	   boolean isHaveItNick = false;
	   for (int i = 0; i<nickNameArray.size(); i++){
		   if (nick.equals(nickNameArray.get(i))){
			   isHaveItNick = true;
			   break;
		   }
	   }
	   return isHaveItNick;
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
