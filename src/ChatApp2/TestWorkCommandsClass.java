package ChatApp2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TestWorkCommandsClass {

	public static void main(String[] args) {
		Command command = new Command();
		NickCommand nickCommand = new NickCommand();
		MessageCommand messageCommand = new MessageCommand();

		Connection connection = new Connection();
		int port = 4444;
		boolean b = true;
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Не могу создать такой порт " + port);
			b = false;
		}

		if (b) {
			Socket clientConnect = null;
			try {
				System.out.print("Жду подключения клиента");
				clientConnect = serverSocket.accept();
				System.out.println("Клиент подключен");
				System.out.println("Данные о клиенте: адресс " + clientConnect.getRemoteSocketAddress() + ", порт "
						+ clientConnect.getLocalPort() + " " + clientConnect.getLocalSocketAddress());
			} catch (IOException e) {
				System.out.println("Не могу подключится к клиенту");
				b = false;
			}
			try {
				connection.setSocket(clientConnect);
				System.out.println("jkhkgh");
				
			} catch (IOException e) {
				b = false;
			}

			while (b) {
				String str= "";
				try{
				str = connection.receive();
				} catch(IOException e){System.out.println("ljkljkl");}
				System.out.println(str);
				command.setCommand(str);
				if (command.isHaveFalseCommand() == false) {
					System.out.println("class Command");
					System.out.println(command.getCommand());
					System.out.println(command.isHaveFalseCommand);
				}

				nickCommand.setCommand(str);
				if (nickCommand.isHaveFalseCommand() == false) {
					System.out.println("class NickCommand");
					System.out.println(nickCommand.getNick());
					System.out.println(nickCommand.isBusy());
					System.out.println(nickCommand.isHaveFalseCommand());
				}

				messageCommand.setCommand(str);
				if (messageCommand.isHaveFalseCommand() == false) {
					System.out.println("class MessageCommand");
					try{
					str = connection.receive();
					} catch(IOException e){System.out.println("jkjhg");}
					messageCommand.setMessage(str);
					System.out.println(messageCommand.isHaveMessage());
					System.out.println(messageCommand.getMessage());
					System.out.println(messageCommand.isHaveMessage());
					System.out.println(messageCommand.isHaveFalseCommand());
				}
			}
		}
	}

}
