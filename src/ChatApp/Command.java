package ChatApp;

import java.net.*;

public class Command {
	private Connection connection = new Connection();

	public Command() {
	}

	public void setSocket(Socket socket) throws Exception {
		connection.setSocket(socket);
	}

	public void sendMessage(String message) {
		connection.sendMessage("Message");
		connection.sendMessage(message);
	}

	public void disconnect() {
		connection.disconnect();
	}

}
