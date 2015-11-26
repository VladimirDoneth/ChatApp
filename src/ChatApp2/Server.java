import sun.text.normalizer.UTF16;

import java.net.*;
import java.io.*;
import java.util.*;
public class Server {
    public static void main(String[] ar) {
        int port = 28411;
        try {
            ServerSocket ss = new ServerSocket(port);
            Socket socket = ss.accept();
            System.out.println(socket.getLocalPort() + " " + socket.getInetAddress());
            PrintWriter PWOut = new PrintWriter(socket.getOutputStream(), true);
            Scanner sss = new Scanner(System.in);
                PWOut.flush();
            while(true){
                System.out.println("you message");
                String str = sss.nextLine();
                PWOut.println(MessageCommand.message);
                PWOut.println(str);
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
