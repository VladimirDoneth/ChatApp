package ChatApp;

import java.io.*;
import java.net.*;

public class Caller {
	private Socket socket;
	private String localNick;
	private String remoteNick;
	private String remoteAdress;
	private static final int remotePort = 28411;

	public Caller() {
		socket = null;
	}

	public void Call() throws UnknownHostException, IOException {
		socket = new Socket(remoteAdress, remotePort);
	}

	public void setLocalNick(String localNick) {
		this.localNick = localNick;
	}

	public void setRemoteNick(String remoteNick) {
		this.remoteNick = remoteNick;
	}

	public void setRemoteAdress(String remoteAdress) {
		this.remoteAdress = remoteAdress;
	}
	
	public Socket getSocket(){
		return socket;
	}
}
