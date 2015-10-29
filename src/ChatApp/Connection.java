package ChatApp;

import java.io.*;
import java.net.*;

public class Connection {
	private Socket socket = null;
	private BufferedReader buffReadIn = null;
	private PrintWriter PWOut = null;

	
	public Connection() {
	}

	public void setSocket(Socket socket) throws Exception {
		this.socket = socket;
		PWOut = new PrintWriter(socket.getOutputStream(), true);
	}

	public void sendNickHello(String nick) {
		sendMessage("ChatApp 2015 user " + nick);
	}

	public void sendNickBusy(String nick) {
		sendMessage("ChatApp 2015 user " + nick + " busy");
		PWOut.close();
	}

	public void accept() {
		sendMessage("Accepted");
	}

	public void reject() {
		sendMessage("Rejected");
		PWOut.close();
	}

	public void disconnect() {
		sendMessage("Disconnect");
		PWOut.close();
	}

	public void sendMessage(String message) {
		PWOut.println(message);
		PWOut.flush();
	}

	public Command receive() {
		return new Command();
	}
}
