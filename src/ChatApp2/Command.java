package ChatApp2;

public class Command {
	public static final String accepted = "Accepted";
	public static final String disconnect = "Disconnect";
    public static final String rejected = "Rejected";

	protected String nowCommand;
	protected boolean isHaveFalseCommand;

	public Command() {

	}

	public void setCommand(String command) {
		isHaveFalseCommand = true;
		switch (command) {
		case accepted:
			nowCommand = accepted;
			isHaveFalseCommand = false;
			break;
		case disconnect:
			nowCommand = disconnect;
			isHaveFalseCommand = false;
			break;
		case rejected:
			nowCommand = rejected;
			isHaveFalseCommand = false;
			break;
		}
	}
	
	public String getCommand(){
		isHaveFalseCommand = true;
		return nowCommand;
	}
	
	public boolean isHaveFalseCommand(){
		return isHaveFalseCommand;
	}
}
