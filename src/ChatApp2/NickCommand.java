package ChatApp2;

public class NickCommand extends Command {
	public static final String helloMessage = "ChatApp 2015 user ";
	public static final String busy = " busy";
	
	private boolean isBusy;
	private String nickName;

	public NickCommand() {
        super.nowCommand = helloMessage;
	}

	private String getNickFromGreating(String connect, boolean bool) {
		String tmpStr = "";
		if (bool)
			for (int i = helloMessage.length(); i < connect.length(); i++) {
				tmpStr += connect.charAt(i);
			}
		else
			for (int i = helloMessage.length(); i < (connect.length() - busy.length()); i++) {
				tmpStr += connect.charAt(i);
			}
		return tmpStr;
	}

	private int getCodeGreating(String connect) {
		if (connect.length() > helloMessage.length()) {
			String tmpStr = "";
			for (int i = 0; i < helloMessage.length(); i++) {
				tmpStr += connect.charAt(i);
			}
			if (tmpStr.equals(helloMessage)) {
				tmpStr = helloMessage + " busy";
				if (connect.length() > tmpStr.length()) {
					int i = connect.length() - 1;
					if ('y' == connect.charAt(i) && 's' == connect.charAt(i - 1) && 'u' == connect.charAt(i - 2)
							&& 'b' == connect.charAt(i - 3) && ' ' == connect.charAt(i - 4)) {
						nickName = getNickFromGreating(connect, false);
						return 6;
					}
				}
				nickName = getNickFromGreating(connect, true);
				return 7;
			}
		}
		return 5; // lose command
	}

	public void setCommand(String command) {
		int i = getCodeGreating(command);
		isBusy = false;
		switch (i) {
		case 5:
			super.isHaveFalseCommand = true;
			break;
		case 7:
			super.isHaveFalseCommand = false;
			isBusy = false;
			break;
		case 6:
			super.isHaveFalseCommand = false;
			isBusy = true;
			break;
		}
	}
	
	public String getNick(){
		return nickName;
	}
	
	public boolean isBusy(){
		return isBusy;
	}

}
