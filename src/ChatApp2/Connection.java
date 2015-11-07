package ChatApp2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Connection {
	private Socket socket;
	private BufferedReader buffReadIn;
	private PrintWriter PWOut;
	private BufferedReader buffReaderIn;

	public Connection() {
	}

	public void setSocket(Socket socket) throws IOException {
		this.socket = socket;
		PWOut = new PrintWriter(socket.getOutputStream(), true);
		buffReaderIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	}

	public void sendNickHello(String nick) {
		sendMessage(NickCommand.helloMessage + nick);
	}

	public void sendNickBusy(String nick) {
		sendMessage(NickCommand.helloMessage + nick + NickCommand.busy);
		PWOut.close();
	}

	public void accept() {
		sendMessage(Command.accepted);
	}

	public void reject() {
		sendMessage(Command.rejected);
		PWOut.close();
	}

	public void disconnect() {
		sendMessage(Command.disconnect);
		PWOut.close();
	}

	public void sendMessage(String message) {
		PWOut.println(message);
		PWOut.flush();
	}

	public Command receive() throws IOException{
		String commandMessage = "";
			commandMessage = buffReaderIn.readLine();
		Command command = new Command();
		command.setCommand(commandMessage);
		if (command.isHaveFalseCommand() == false) return command;
		
		MessageCommand messageCommand = new MessageCommand();
		messageCommand.setCommand(commandMessage);
		if (messageCommand.isHaveFalseCommand() == false) return messageCommand;
		
		NickCommand nickCommand = new NickCommand();
		nickCommand.setCommand(commandMessage);
		if (nickCommand.isHaveFalseCommand() == false) return nickCommand;
		return null;
	}
}
