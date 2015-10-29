package ChatApp;

import java.io.IOException;
import java.net.*;

public class CallListener {
	private ServerSocket server = null;

	public CallListener(ServerSocket server) {
		this.server = server;
	}

	public void setServerSocket(ServerSocket server) {
		this.server = server;
	}

	public Socket getSocket() {
		Socket socket = null;
		try {		
			socket = server.accept();
		} catch (IOException e) {
			System.out.println("Не могу подключится к клиенту");
		}
		
		return socket;
	}
}
