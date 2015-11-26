package ChatApp2;


import javax.swing.*;
import java.awt.event.*;
import java.text.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;


public class Connection{
    private Socket socket;
    private BufferedReader buffReadIn;
    private PrintWriter PWOut;
    private BufferedReader buffReaderIn;
    //static MessageCommand messageCommand;
    static String tmpStrTimep;

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
    }

    public Socket getSocket(){
        return socket;
    }

    public Command receive() throws IOException{
        Command command = new Command();
        String commandMessage = "";

        commandMessage = buffReaderIn.readLine();
        command.setCommand(commandMessage);
        if (!command.isHaveFalseCommand()) return command;

        System.out.println("1");
        MessageCommand messageCommand = new MessageCommand();
        messageCommand.setCommand(commandMessage);
        if (!messageCommand.isHaveFalseCommand()){
             tmpStrTimep="";
             
            final Timer timer = new Timer(false);
            final TimerTask task = new TimerTask() {
                int flag = 1;
                @Override
                public void run() {
                    if (flag == 1) {
                        try {
                            flag=0;
                            tmpStrTimep=buffReaderIn.readLine();
                           // cancel();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        tmpStrTimep +="битое";
                        cancel();
                    }
                }
            };
            timer.schedule(task, 0, 2000);
            while (!task.cancel()) {
            }
            tmpStrTimep = buffReaderIn.readLine();
            messageCommand.setMessage(tmpStrTimep);
            return messageCommand;
        }

        System.out.println("2");
        NickCommand nickCommand = new NickCommand();
        nickCommand.setCommand(commandMessage);
        if (!nickCommand.isHaveFalseCommand()) return nickCommand;

        System.out.println("3");
        return null;
    }

   /* private class MessageReceive implements ActionListener{
        private MessageCommand messageCommand;
        private BufferedReader bufferedReader;


        public MessageReceive(MessageCommand messageCommand,BufferedReader bufferedReader){
            this.messageCommand = messageCommand;
            this.bufferedReader = bufferedReader;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                //Thread.sleep(2000);
                messageCommand.setMessage(buffReaderIn.readLine());
            } catch (IOException e) {
                e.printStackTrace();
           // } catch (InterruptedException e) {
            //    e.printStackTrace();
           // }
        }
    }*/


    public String readMessage(){
        String message= null;
        try {
            message = buffReaderIn.readLine();
        } catch (IOException e) {
            message = null;
        }
        return message;
    }

}

/*
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
*/
