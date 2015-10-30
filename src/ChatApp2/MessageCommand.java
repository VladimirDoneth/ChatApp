package ChatApp2;

public class MessageCommand extends Command{
	public static final String message = "Message";
	
	private String sendedMessage;
	private boolean isHaveMessage;
	
	public MessageCommand (){
		super.nowCommand = message;
	}
	
	public void setCommand(String command){
		if (message.equals(command)) super.isHaveFalseCommand = false;
		else super.isHaveFalseCommand = true;
	}
	
	public void setMessage(String sendedMessage){
		 this.sendedMessage = sendedMessage;
		 this.isHaveMessage = true;
	}
	
	public boolean isHaveMessage(){
		return isHaveMessage;
	}
	
	public String getMessage(){
		isHaveMessage = false;
		return sendedMessage;
	}
	
}
